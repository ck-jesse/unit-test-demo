package com.touna.test.springboot;

/**
 * @Author chenck
 * @Date 2019/1/28 10:24
 */
public class TwoBitSetTest {

    //用两个bit位来标记某个元素的个数
    int bNum = 2;
    //一个32位字节能标记多少个数
    int bSize = 32 / bNum;
    //数据范围(0到2^32内的数)
    int numSize = 160000;//此处是16万，便于测试
    //定义bitmap数组大小
    int arraySize = (int) Math.ceil((double) numSize / bSize);
    private int array[] = new int[arraySize];

    /**
     * 初始化BitMap
     */
    public void initBitMap() {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    /**
     * 往BitMap中设置对应的数的个数
     *
     * @param x   要添加的数
     * @param num 对应的个数
     */
    public void set(int x, int num) {
        //获得bitMap的下标
        int m = x >> 4;//或 int m = x /bSize;
        //获得对应的位置
        int n = x % bSize;

        //将x对应位置上的数值先清零，但是有要保证其他位置上的数不变
        array[m] &= ~((0x3 << (2 * n)));
        //重新对x的个数赋值
        array[m] |= ((num & 3) << (2 * n));
//        printInfo(array[m]);
    }

    /**
     * 获取x在BitMap中的数量
     *
     * @param x
     * @return
     */
    public int get(int x) {
        int m = x >> 4;
        int n = x % bSize;
        return (array[m] & (0x3 << (2 * n))) >> (2 * n);
    }

    /**
     * 往BitMap中添加数
     * 如果x的个数大于三，则不在添加(2个bit为最多只能表示到3:00 01 10 11)
     *
     * @param x
     */
    public void add(int x) {
        int num = get(x);
        //只处理num小于3的
        if (num < 3) {
            set(x, num + 1);
        }
    }

    public static void main(String[] args) {

        TwoBitSetTest test = new TwoBitSetTest();
        test.initBitMap();
        int sortArray[] = new int[]{1, 4, 1, 32, 2, 6, 4, 2, 69, 9, 4, 185, 2};
        for (int i = 0; i < sortArray.length; i++) {
            test.add(sortArray[i]);
        }
        System.out.println("对BitMap中的所有数据排序:");
        for (int i = 0; i < test.numSize; i++) {
            if (test.get(i) != 0) {
                System.out.println((i) + "出现的次数：" + test.get(i) + " ");
//                System.out.print(i + " ");
            }
        }

        System.out.println("\n只出现一次的数据:");
        for (int i = 0; i < test.numSize; i++) {
            if (test.get(i) == 1) {
                System.out.print(i + " ");
            }
        }

    }

    /**
     * 输出一个int的二进制数
     *
     * @param num
     */
    public static void printInfo(int num) {
        System.out.println(num + " 二进制为" + Integer.toBinaryString(num));
    }
}
