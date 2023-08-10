package org.zhuangjie.queue;

public class CircleQueue<C> {
    private final static int DEFAULT_CAPACITY = 15;
    private C[] circle = (C[]) new Object[DEFAULT_CAPACITY];
    private int front = 0;
    private int size = 0;


    //转为在数组中真实的索引
    private int CI(int index) {
        return (index + front) % circle.length;
    }
    //检查容器
    public void check_capacity() {
        if(circle.length <= size) {
            System.out.println("--开始扩容--");
            C[] _circle = (C[]) new Object[circle.length*2];
            for (int i = 0; i < circle.length; i++) {
                _circle[i] = circle[CI(i)];
            }
            circle = _circle;
            front = 0;

        }
    }
    //添加元素
    public void enQueue(C c) {
        check_capacity();
        circle[(front+size)%circle.length] = c;
        size++;
    }
    //弹出元素
    public C deQueue() {
        C pop_unit = circle[front];
        front = (front + 1) % circle.length;
        size--;
        return pop_unit;
    }
    public boolean isEmpty() {
        return size <= 0;
    }


    //循环队列格式化输出
    @Override
    public String toString() {
        StringBuilder result_str = new StringBuilder();
        result_str.append("size="+size+",capacity ="+circle.length+" [");
        int length = circle.length;
        int tail = CI(size - 1);
        for (int i = 0; i < length; i++) {
            if( (tail < front && i > tail && i < front) || ( tail > front && i < front) ) {
                result_str.append("null , ");
            }else {
                if (i != length-1) {
                    result_str.append(circle[i]+", ");
                }else {
                    result_str.append(circle[i]+"]");
                }
            }
        }
        return result_str.toString();
    }

}
