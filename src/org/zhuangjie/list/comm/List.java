package org.zhuangjie.list.comm;
//存放公共接口
public interface List<U> {
    //添加单元
    public void add(U unit);
    //在指定index中插入指定的值
    public void add(int index,U unit);
    //根据index获取指定值
    public U get(int index);
    //判断数组中是否包含某个元素
    public int indexOf(U unit);

    //传入一个数组查看返回是否结构的对应数组
    public int[] contains(U...unit);
    //查看数组是否为空
    public boolean isEmpty();

    //删除指定index对应的值，size减1
    public U remove(int index);
    //清除所有元素
    public void clear();


    //输出格式化
    public String toString();
}
