package com.touna.test;

/**
 * @Author chenck
 * @Date 2018/12/13 16:34
 */
public class Student {

    public boolean canVote(int age) {
        if (age <= 0) throw new IllegalArgumentException("canVote 年龄应该大于0");
        if (age < 18) return false;
        else return true;
    }

    public boolean canVote1(int age) {
        if (age <= 0) throw new IllegalArgumentException("canVote1 年龄应该大于0");
        if (age < 18) return false;
        else return true;
    }
}
