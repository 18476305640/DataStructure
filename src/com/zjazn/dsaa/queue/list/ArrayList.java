package com.zjazn.dsaa.queue.list;

import com.zjazn.dsaa.queue.list.comm.AbstractList;

public class ArrayList<U> extends AbstractList<U> {
    private static final int  DEFAULT_SIZE = 10;
    private U[] box;
    private int size = 0;
    public ArrayList(int size) {
        box = (U[]) new Object[size];
    }
    public ArrayList() {
        this(DEFAULT_SIZE);
    }
    //确保容器足够
    public  void check_capacity() {
        if(box.length - size <= 0) {
            //说明可以add与向指定位置插入
            int oldLength = box.length;
            int newLength = oldLength + (oldLength >> 1);
            U[] b = (U[])new Object[newLength];
            for(int i=0; i < oldLength; i++) {
                b[i] = box[i];
            }
            box = b;
            System.out.println("旧容量："+oldLength+",扩容为:"+newLength);
        }


    }

    //在指定index中插入指定的值
    public void add(int index,U unit) {
        if (index < 0 || index > size ) {
            throw new IndexOutOfBoundsException("size是"+size+"你输入的index是:"+index);
        }
        check_capacity();
        for (int i = size-1; i >= index; i--) {
            box[i+1] = box[i];
        }
        box[index] = unit;
        size++;
    }
    //根据index获取指定值
    public U get(int index) {
        check_index(index);
        return box[index];
    }
    //判断数组中是否包含某个元素
    public int indexOf(U unit) {
        for (int i = 0; i < box.length; i++ ) {
            if (box[i] != null && box[i].equals(unit)) {
                return i;
            }else if(box[i] == unit){
                return i;
            }
        }
        return -1;
    }




    //删除指定index对应的值，size减1
    public U remove(int index) {
        check_index(index);
        U old = box[index];
        for(int i = index+1; i < box.length; i++) {
            box[i-1] = box[i];
        }
        box[size--] = null;
        return old;
    }
    //清除所有元素
    public void clear() {
        for (int i = 0; i < box.length; i++) {
            box[i] = null;
        }
        size = 0;
    }



    //输出格式化
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size+"\t[");
        for (int i = 0; i < size; i++) {
            if(i == 0) {
                string.append(box[i]);
            }else {
                string.append(", "+box[i]);
            }
        }
        string.append("]");

        return string.toString();
    }

}
