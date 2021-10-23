package com.zjazn.dsaa.stack;

import java.util.ArrayList;
import java.util.List;

public class Stack<B> {
    private List<B> stack = new ArrayList<>();
    //向栈中添加元素
    public void push(B b) {
        stack.add(b);
    }
    //向栈中弹出元素
    public B pop() {
        return stack.remove(stack.size() - 1 );
    }
    //查看栈顶的元素
    public B top() {
        return stack.get(stack.size() - 1 );
    }
    //查看栈高度
    public int size() {
        return stack.size();
    }

}
