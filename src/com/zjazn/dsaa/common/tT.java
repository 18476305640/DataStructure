package com.zjazn.dsaa.common;

public class tT {
    public interface Task {
        void exec();
    }
    public static void check(String who,Task task) {
        System.out.println(who+": 测试开始了[");
        System.out.println("开始时间："+System.currentTimeMillis());
        long begin = System.currentTimeMillis();
        task.exec();
        long end = System.currentTimeMillis();
        System.out.println("结束时间："+System.currentTimeMillis());
        System.out.println("耗时："+ (end - begin)  +" ms！ ]");
    }
}
