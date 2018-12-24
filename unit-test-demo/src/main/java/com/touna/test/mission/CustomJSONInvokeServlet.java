//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.touna.test.mission;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.mission.common.exception.InvokeNotFoundException;
import org.mission.common.exception.MessageException;
import org.mission.common.utils.CommonUtils;
import org.mission.common.utils.PropertiesUtils;
import org.mission.invoke.*;
import org.mission.invoke.annotation.LoginStateType;
import org.mission.invoke.servlet.AbstractJSONHttpServlet;
import org.mission.invoke.util.CookieUtils;
import org.mission.tsession.TSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 针对mission框架中的JSONInvokeServlet进行扩展
 * 改动点：只是注释掉 httpResponse.getStatus() 的判断逻辑，因为httpunit中的ServletUnitHttpResponse的getStatus()是私有方法，所以此处给注释掉
 *
 * @Author chenck
 * @Date 2018/12/20 16:17
 */
public class CustomJSONInvokeServlet extends AbstractJSONHttpServlet {
    private static final long serialVersionUID = -6252867909812875308L;
    private static final Logger logger = LoggerFactory.getLogger(CustomJSONInvokeServlet.class);
    private String invokerName = null;
    protected String serviceName = null;
    private static Map<String, String> mock_config = null;
    private ValueFilter filter = new ValueFilter() {
        public Object process(Object obj, String s, Object v) {
            return v == null ? "" : v;
        }
    };

    public CustomJSONInvokeServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
        String serviceName = config.getInitParameter("service");
        this.invokerName = config.getServletName();
        if (CommonUtils.isEmpty(serviceName)) {
            serviceName = this.invokerName.replace("Invoker", "Service");
        }

        this.serviceName = serviceName;
    }

    protected void handleJSONRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        String subtime = httpRequest.getParameter("subtime");
        if (subtime == null) {
            throw new IllegalArgumentException("please add subtime.");
        } else {
            String methodName = httpRequest.getParameter("method");
            if (methodName == null) {
                throw new IllegalArgumentException("please add methodName.");
            } else {
                HttpInvokeHolder invokeHolder = new HttpInvokeHolder(subtime, httpRequest, httpResponse);
                String output;
                if (mock_config != null && !mock_config.isEmpty()) {
                    String key = this.serviceName + "_" + methodName;
                    logger.info("mock key is " + key);
                    output = (String) mock_config.get(key);
                    if (output != null && output != "") {
                        httpResponse.getWriter().write(output);
                        httpResponse.flushBuffer();
                        return;
                    }
                }

                this.doInvoke(methodName, invokeHolder);
//                if (httpResponse.getStatus() == 200) {
                Map<String, Object> respMap = new HashMap(8, 1.0F);
                respMap.put("subtime", subtime);
                respMap.put("method", methodName);
                respMap.put("status", invokeHolder.getStatus());
                respMap.put("result", invokeHolder.getResult());
                respMap.put("desc", invokeHolder.getDesc());
                output = JSON.toJSONString(respMap, InvokeManager.isWriteNullValue() ? this.filter : null, new SerializerFeature[0]);
                httpResponse.getWriter().write(output);
                httpResponse.flushBuffer();
//                }

            }
        }
    }

    protected void doInvoke(String methodName, HttpInvokeHolder invokeHolder) {
        long start = System.currentTimeMillis();
        boolean invokeResult = false;
        boolean isMessage = false;
        MethodHolder methodHolder = null;
        Object[] invokeArgs = null;

        try {
            ServiceHolder serviceHolder = InvokeManager.foundServiceHolder(this.serviceName);
            if (serviceHolder == null) {
                throw new InvokeNotFoundException("unfound service=[" + this.serviceName + "] @ " + this.invokerName);
            }

            methodHolder = serviceHolder.getMethodHolder(methodName);
            if (methodHolder == null) {
                throw new InvokeNotFoundException("unfound method=[" + methodName + "] from service=[" + this.serviceName + "] @ " + this.invokerName);
            }

            this.checkState(methodHolder, invokeHolder);
            if (!invokeHolder.isOk()) {
                return;
            }

            invokeArgs = HttpInvokeHelper.extractInvokeParameters(methodHolder.getArgsNames(), methodHolder.getArgsMarkers(), invokeHolder.getHttpRequest());
            InvokeManager.registerInvokeHolder(invokeHolder);
            methodHolder.getMethod().invoke(serviceHolder.getService(), invokeArgs);
            invokeResult = true;
        } catch (Throwable var19) {
            Throwable cause = CommonUtils.foundRealThrowable(var19);
            String reson = CommonUtils.formatThrowable(cause);
            isMessage = cause instanceof MessageException;
            if (!isMessage) {
                Map<String, String[]> data = invokeHolder.getHttpRequest().getParameterMap();
                Map<String, String> parameters = new HashMap();
                if (data != null) {
                    Iterator var14 = data.keySet().iterator();

                    while (var14.hasNext()) {
                        String key = (String) var14.next();
                        parameters.put(key, Arrays.toString((Object[]) data.get(key)));
                    }
                }

                logger.error("invoke service={}, method={}, userid={}, args={}, parameters={} with:{}", new Object[]{this.serviceName, methodName, "", invokeArgs, parameters, reson});
            }

            if (cause instanceof MustLoginException) {
                redirectLogin(invokeHolder);
            }

            if (cause instanceof NotFoundException) {
                redirectNotFound(invokeHolder);
            } else {
                String desc = "系统繁忙, 请稍后重试.";
                if (invokeHolder.isDebug()) {
                    desc = CommonUtils.formatThrowableForHtml(cause);
                } else if (isMessage) {
                    desc = cause.getMessage();
                }

                invokeHolder.setDesc(desc);
            }
        } finally {
            InvokeManager.releaseInvoke();
        }

    }

    protected void checkState(MethodHolder mHolder, HttpInvokeHolder invokeHolder) {
        if (!invokeHolder.isDebug()) {
            boolean isLogin = TSessionManager.get(CookieUtils.getCookie("sid", invokeHolder.getHttpRequest())) != null;
            if (mHolder.getLoginState() == LoginStateType.MUST_LOGIN && !isLogin) {
                redirectLogin(invokeHolder);
            } else if (mHolder.getLoginState() == LoginStateType.FORBIN_LOGIN && isLogin) {
                forbiddenLogin(invokeHolder);
            }
        }

    }

    private static void forbiddenLogin(InvokeHolder invokeHolder) {
        invokeHolder.setResult("/account.html");
        invokeHolder.setDesc(302, (String) null);
    }

    private static void redirectNotFound(InvokeHolder invokeHolder) {
        invokeHolder.setResult("/404.html");
        invokeHolder.setDesc(404, (String) null);
    }

    private static void redirectLogin(InvokeHolder invokeHolder) {
        invokeHolder.setResult("/user-login.html");
        invokeHolder.setDesc(302, (String) null);
    }

    static {
        try {
            mock_config = PropertiesUtils.load("mock_config");
            if (mock_config != null && !mock_config.isEmpty()) {
                logger.info("Mock mode start");
            }
        } catch (Exception var1) {
            ;
        }

    }
}
