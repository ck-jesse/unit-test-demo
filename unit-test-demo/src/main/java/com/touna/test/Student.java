package com.touna.test;

/**
 * @Author chenck
 * @Date 2018/12/13 16:34
 */
public class Student {

    public boolean canVote(int age) {
        if (age <= 0) throw new IllegalArgumentException("canVote age should be +ve");
        if (age < 18) return false;
        else return true;
    }

    public boolean canVote1(int age) {
        if (age <= 0) throw new IllegalArgumentException("canVote1 age should be +ve");
        if (age < 18) return false;
        else return true;
    }
}
