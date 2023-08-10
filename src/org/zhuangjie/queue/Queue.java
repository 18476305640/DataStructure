package org.zhuangjie.queue;

import org.zhuangjie.queue.list.DoubleLinkedList;

public class Queue<Q> {
    private DoubleLinkedList<Q> m = new DoubleLinkedList<Q>();

    public void enQueue(Q q) {
        m.add(q);
    }
    public Q deQueue() {
        if(m.isEmpty()) return null;
        return m.remove(0);
    }
    public boolean isEmpty() {
        return m.isEmpty();
    }
    public int size() {
        return m.size();
    }




}
