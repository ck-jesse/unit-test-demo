package com.touna.test.springboot;

import org.junit.Test;

import java.util.BitSet;

/**
 * Unit test for simple App.
 */
public class BitSetTest {

    @Test
    public void bitSetTest() {
        BitSet bm = new BitSet();
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(0);
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(1);
        System.out.println(bm.isEmpty() + "--" + bm.size());
        System.out.println(bm.get(65));
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(65);
        System.out.println(bm.isEmpty() + "--" + bm.size());
    }
}
