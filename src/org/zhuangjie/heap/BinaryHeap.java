package org.zhuangjie.heap;

import org.zhuangjie.tree.printer.BinaryTreeInfo;
import org.zhuangjie.tree.printer.BinaryTrees;

import java.util.Comparator;

/**
 * 二叉堆
 *
 * @author zhuangjie
 * @date 2023/08/08
 */
public class BinaryHeap<E> implements Heap<E>, BinaryTreeInfo {
    private E[] elements ;
    private  Comparator<E> comparator;
    private int DEFAULT_CAPACITY = 16;
    private int size = 0;
    public BinaryHeap( Comparator<E> comparator)  {
        this();
        this.comparator = comparator;
    }
    public BinaryHeap(E[] elements)  {
        if (elements == null || elements.length == 0) {
            // 如果传入的elements无效，那就进行普通的初始化
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
            return;
        }
        // elements不为空，初始化容量进行批量建堆
        this.elements = (E[]) new Object[Math.max(elements.length, DEFAULT_CAPACITY)];
        for (int i = 0; i < elements.length; i++) this.elements[i] = elements[i];
        this.size = elements.length;
        heapify();
    }
    public BinaryHeap(E[] elements, Comparator<E> comparator)  {
        this(elements);
        this.comparator = comparator;
    }

    public BinaryHeap()  {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * 批量建堆
     */
    private void heapify() {
        // 自上而下的上滤
        // for (int i = 0; i < size; i++) siftUp(i);
        // 自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0; i--) siftDown(i);

    }



    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) elements[i] = null;
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size+ 1);
        elements[size++] = element;
        siftUp(size - 1);

    }

    /**
     * 当插入一个新的元素时，该元素节点会尝试上滤
     *
     * @param index 指数
     */
    private void siftUp(int index) {
        // 看是否为第一个节点，第一个节点肯定没有父节点
        if (index == 0) return;
        E element = elements[index];
        // 父节点位于数组的索引
        int pIndex;
        // 父节点element
        E pElement;
        do {
            // 更新父节点索引与element
            pElement = elements[pIndex = (index-1) >> 1];
            // 如果父节点element大于或等于element则不再需要上滤了
            if (compare(element,pElement) <= 0) break;
            // 父element -> childElement
            elements[index] = pElement;
            // index变为pIndex继续向尝试上滤
            index = pIndex;
        }while (pIndex != 0); // 如果上面操作的父节点索引已经是0时，就不再需要上滤了
        // "浮"着的下降
        elements[index] = element;
    }

    private int compare(E e1, E e2) {
        if (comparator != null) return comparator.compare(e1, e2);
        // 没有传入比较器，看元素是否可比较,否则让它报错
        return ((Comparable<E>) e1).compareTo(e2);
    }


    /**
     * 保证数组容量
     *
     * @param capacity 容器大小
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        // 容量不足-确保新容器容量是旧的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements  = (E[]) new Object[newCapacity];
        // 数据移动
        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i];
        }
        // 替换
        elements = newElements;
    }

    /**
     * 元素not null检查
     *
     * @param element 元素
     */
    private void elementNotNullCheck(E element) {
        if (element == null) throw new IllegalArgumentException("element must not be null");
    }

    @Override
    public E get() {
        return isEmpty()?null:elements[0];
    }

    @Override
    public E remove() {
        if (isEmpty()) return null;
        E removeElement = elements[0];
        elements[0] = null;
        size--;
        if (isEmpty()) return removeElement;
        elements[0] = elements[size];
        siftDown(0);
        return removeElement;
    }


    /**
     * 下滤
     *
     * @param index 要下滤的索引
     */
    private void siftDown(int index) {
        // helf是最后一个非叶子节点的索引（ floor(size/2) ），能算出是因为堆有完全二叉树的性质。
        int half = (size-2) >> 1;
        E tmpElement = elements[index];
        E childElement;
        // 下滤
        while (index <= half) {
            int leftIndex = (index << 1) + 1;
            int childIndex = leftIndex;
            int rightIndex =  (index << 1) + 2;
            if (rightIndex <= size - 1 && compare(elements[leftIndex],elements[rightIndex]) < 0) {
                childIndex = rightIndex;
            }
            childElement = elements[childIndex];
            if (compare(tmpElement,childElement) >= 0) break;
            elements[index] = childElement;
            index = childIndex;
        }
        elements[index] = tmpElement;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E replaceElement = elements[0];
        elements[0] = element;
        if (isEmpty()) {
            size++;
        }else {
            siftDown(0);
        }
        return replaceElement;
    }

    @Override
    public Object root() {
        return isEmpty()?null:0;
    }

    @Override
    public Object left(Object node) {
        Integer index = (Integer) node;
        int leftIndex = (index << 1) + 1;
        return leftIndex < size?leftIndex:null;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer)node;
        int rightIndex = (index << 1) + 2;
        return rightIndex < size?rightIndex:null;
    }

    @Override
    public Object string(Object node) {
        Integer index = (Integer)node;
        return index >= size?null:elements[index];
    }
    @Override
    public String toString() {
        return BinaryTrees.printString(this);
    }
}
