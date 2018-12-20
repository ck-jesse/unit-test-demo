package com.touna.test.jmockit.usual;

import com.touna.test.Student;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Junit的异常测试
 *
 * @Author chenck
 * @Date 2018/12/13 16:33
 */
public class ExpectExceptionTest {

    // 方式一
    @Test(expected = IllegalArgumentException.class)
    public void test1() {
        Student student = new Student();
        student.canVote(0);
    }

    // 方式二
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test2() {
        thrown.expect(IllegalArgumentException.class);
//        thrown.expectMessage("canVote age should be +ve");
        thrown.expectMessage("canVote1 年龄应该大于0");

        Student student = new Student();
        student.canVote1(0);
        student.canVote(0);
    }
}
