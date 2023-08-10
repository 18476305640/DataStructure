package org.zhuangjie.heap;

import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        Integer[] array = {1, 15, 7, 23, 9, 42, 18, 6, 31, 12, 28, 3, 17, 8, 39, 20, 11, 5, 33, 14};
        int topK = 5;
        Heap<Integer> heap = new BinaryHeap<>((e1,e2)->e2 - e1);
        for (Integer element : array) {
            if (heap.size() < topK) {
                heap.add(element);
            }else if (heap.get() < element){
                heap.replace(element);
            }
        }
        System.out.println(heap);

    }
}
