package org.zhuangjie.heap.priority_queue;

import org.zhuangjie.heap.BinaryHeap;

import java.util.Comparator;

/**
 * 优先队列
 * @param <E>
 */
public class PriorityQueue<E> {
    private BinaryHeap<E> heap; // 二叉堆

    public PriorityQueue(Comparator<E> comparator) {
        heap = new BinaryHeap<>(comparator);
    }

    public PriorityQueue() {
        this(null);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void clear() {
        heap.clear();
    }

    public void enQueue(E element) {
        heap.add(element); //入队
    }

    public E deQueue() {
        return heap.remove(); //让优先级最高的元素出队
    }

    public E front() {
        return heap.get(); //获取堆顶元素
    }
}
