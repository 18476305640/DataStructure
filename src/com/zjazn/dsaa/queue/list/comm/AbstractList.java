package com.zjazn.dsaa.queue.list.comm;

//存放公共代码
public abstract class AbstractList<U> implements List<U> {
    protected int size = 0;

    //查看数据的大小
    public int size() {
        return size;
    }
    //查看数组是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //传入一个数组查看返回是否结构的对应数组
    public int[] contains(U...unit) {
        int[] indexs = new int[unit.length];
        for (int i = 0; i < unit.length; i++) {
            indexs[i] = indexOf(unit[i]);
        }
        return indexs;
    }
    //添加单元
    public void add(U unit) {
        add(size,unit);
    }


    //确保我输入参数index合法
    public void check_index(int index) {
        if (index < 0 || index > size-1 ) {
            throw new IndexOutOfBoundsException("size是"+size+"你输入的index是:"+index);
        }


    }


}
