package com.zjazn.dsaa.sort;


public class test {

    public static void main(String[] args) {

        int a[] = new int[200];
        for (int i=0; i < 200; i++) {
            int  fa = (int)(Math.random()*10000);
            a[i] = fa;
        }
        //对a数组进行开始排序
        new SimpleSort().start(a);
        //输出数组
        String out_str = "[";
        for (int i = 0; i < a.length; i++) {
            if (i == a.length-1) {
                out_str += a[i]+"]";
            }else {
                out_str += a[i]+", ";
            }
        }
        System.out.println(out_str);

        //检测是否排序成功
        for (int i = 0; i < a.length-1; i++) {
            for (int j = i+1; j<a.length; j++) {
                if (a[i] > a[j]) {
                    System.err.println(a[i]+","+a[i+1]+"出现了问题~");
                    return;
                }
            }


        }



    }

}


