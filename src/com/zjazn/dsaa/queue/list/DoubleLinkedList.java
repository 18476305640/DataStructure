package com.zjazn.dsaa.queue.list;

import com.zjazn.dsaa.queue.list.comm.AbstractList;

public class DoubleLinkedList<U> extends AbstractList<U> {
    //size
    private Node<U> last;
    private Node<U> first;

    private static class Node<U> {
        U unit;
        Node<U> next;
        Node<U> prev;

        public Node(U unit, Node<U> prev,Node<U> next) {
            this.unit = unit;
            this.prev = prev;
            this.next = next;
        }
    }


    @Override
    public void add(int index, U unit) {
        if (index < 0 || index > size ) {
            throw new IndexOutOfBoundsException("size是"+size+"你输入的index是:"+index);
        }
        if(index == 0) {
            //在第一个位置添加的二种情况“第一个元素时” “已存在元素时在第一个位置插入元素”
            if(size == 0) {
                //在添加链表的第一个元素时
                Node<U> new_node = new Node<>(unit, null,null);
                first = new_node;
                last = new_node;
            }else {
                Node<U> next = getNode(index);
                Node<U> prev = next.prev;
                Node<U> new_node = new Node<>(unit, prev, next);
                first = new_node;
                next.prev =new_node;
            }
        }else {
            if(index == size) {
                //在最后添加一个元素
                Node<U> prev = getNode(size-1);
                Node<U> next = null;
                Node<U> new_node = new Node<>(unit, prev, next);
                last = new_node;
                prev.next = new_node;
            }else {
                Node<U> next = getNode(index);
                Node<U> prev = next.prev;
                Node<U> new_node = new Node<>(unit, prev, next);
                next.prev = new_node;
                prev.next = new_node;


            }
        }
        size++;

    }

    public Node<U> getNode(int index) {
        check_index(index);
        Node<U> target_node = null;
        if(index > (size >> 1)) {
            target_node = first;
            for (int i = 0; i < index; i++) {
                target_node = target_node.next;
            }
        }else {
            target_node = last;
            for (int i = size-1; i > index; i--) {
                target_node = target_node.prev;
            }
        }

        return target_node;
    }

    @Override
    public U get(int index) {
        check_index(index);
        U unit = getNode(index).unit;
        return unit;
    }

    @Override
    public int indexOf(U unit) {
        int index = -1;
        for (Node<U> node = first; node != null; node = node.next) {
            index++;
            if(node.unit == unit) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public U remove(int index) {
        check_index(index);
        Node<U> old_node = getNode(index);
        Node<U> next_node = old_node.next;
        Node<U> prev_node = old_node.prev;

        if(prev_node == null) {
            first = next_node;
        }else {
            prev_node.next = next_node;
        }
        if(next_node == null) {
            last = prev_node;
        }else {
            next_node.prev = prev_node;
        }

        size--;
        return old_node.unit;

    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size+"\t[");

        for (Node<U> my_unit = first; my_unit != null ; my_unit = my_unit.next) {
            Node<U> prev = my_unit.prev;
            Node<U> next = my_unit.next;
            if(my_unit.next == null) {
                string.append((prev==null?"null":prev.unit+"")+"("+my_unit.unit+")"+(next==null?"null":next.unit+""));
            }else {
                string.append((prev==null?"null":prev.unit+"")+"("+my_unit.unit+")"+(next==null?"null":next.unit+","));
            }
        }

        string.append("]");
        return string.toString();
    }







}
