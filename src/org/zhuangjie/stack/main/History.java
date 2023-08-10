package org.zhuangjie.stack.main;

import java.util.Stack;

public class History<B> {
    private Stack<B> m = new Stack<>();
    private Stack<B> n= new Stack<>();

    //打开一个新的网站
    public void open(B b) {
        m.push(b);
        while(n.size() > 0) {
            n.pop();
        }
    }
    //点击前进
    public B back() {
        if(m.size() > 0) {
            n.push(m.pop());
            B peek = m.peek();
            if( peek != null) return peek;

        }
        System.out.println("正在访问主页！");
        return null;
    }
    //点击后退
    public B forward() {
        if(n.size() > 0 ) {
            m.push(n.pop());
            B peek = m.peek();
            if( peek != null) return peek;

        }
        System.out.println("正在访问主页！");
        return null;
    }
    public B peek() {
        return m.peek();
    }

}
