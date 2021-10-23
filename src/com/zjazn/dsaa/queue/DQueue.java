package com.zjazn.dsaa.queue;

import com.zjazn.dsaa.queue.list.DoubleLinkedList;

public class DQueue<D> {
    private DoubleLinkedList<D> m = new DoubleLinkedList();
    public void enQueueFront(D d) {
        m.add(0,d);
    }
    public void enQueueRear(D d) {
        m.add(d);
    }
    public D deQueueFront() {
        if(m.isEmpty()) return  null;
        return m.remove(0);
    }
    public D deQueueRear() {
        if(m.isEmpty()) return  null;
        return m.remove(m.size() - 1);
    }
    public boolean isEmpty() {
        return m.isEmpty();
    }
}
