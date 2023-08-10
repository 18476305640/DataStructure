package org.zhuangjie.heap.priority_queue;

public class Test {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.enQueue(1);
        priorityQueue.enQueue(3);
        priorityQueue.enQueue(2);
        System.out.println(priorityQueue.deQueue());
    }
}
