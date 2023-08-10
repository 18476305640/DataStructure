package org.zhuangjie.queue;

import java.util.Stack;

public class QueueForStack<Q> {
    private Stack<Q> m = new Stack<>();
    private Stack<Q> n = new Stack<>();

    public void push(Q q) {
        m.push(q);
    }
    public Q pop() {
        if(n.isEmpty()) {
            while (!m.isEmpty()) {
                n.push(m.pop());
            }
        }
        return n.pop();
    }
    public int size() {
        return m.size()+n.size();
    }
    public boolean isEmpty() {
        return m.isEmpty() && n.isEmpty();
    }
}
