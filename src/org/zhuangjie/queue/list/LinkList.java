package org.zhuangjie.queue.list;

import org.zhuangjie.queue.list.comm.AbstractList;

public class LinkList<U> extends AbstractList<U> {
    //size
    private Node<U> first;

    private static class Node<U> {
        U unit;
        Node<U> next;

        public Node(U unit, Node<U> next) {
            this.unit = unit;
            this.next = next;
        }
    }


    @Override
    public void add(int index, U unit) {
        if (index < 0 || index > size ) {
            throw new IndexOutOfBoundsException("size是"+size+"你输入的index是:"+index);
        }
        Node<U> new_node = new Node<>(unit, null);
        if(index == size) {
            //在最后插入
            if(index == 0) {
                first = new_node;
            }else {
                getNode(index - 1).next = new_node;
            }
        }else {
            Node<U> prev_node = getNode(index - 1); //写
            Node<U> replace_node = prev_node.next; //读
            new_node.next = replace_node; //将新节点的下个节点设置为替换位置的那个节点
            prev_node.next = new_node; //将上一个
        }
        size++;
        //在已存在元素的索引中插入


    }

    public Node<U> getNode(int index) {
        check_index(index);
        Node<U> target_node = first;
        for (int i = 0; i < index; i++) {
            target_node = target_node.next;
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
        Node<U> del_node = getNode(index);
        if(index == 0) {
            first = del_node.next;
        }else {
            getNode(index - 1).next =  del_node.next;
        }
        size--;
        return del_node.unit;

    }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size+"\t[");

        for (Node<U> my_unit = first; my_unit != null ; my_unit = my_unit.next) {
            if(my_unit.next == null) {
                string.append(my_unit.unit);
            }else {
                string.append(my_unit.unit+", ");
            }
        }

        string.append("]");
        return string.toString();
    }
    public Node<U> reverseList(Node<U> head) {
        if(head == null ) return null;
        Node<U> new_head = reverseList(head.next);
        if(new_head != null) {
            new_head.next = head;
        }else {
            this.first = head;
        }
        return head;
    }
    public void insteadList() {
        Node temp = first; //使不至于被销毁,反转后temp是最后一个
        Node<U> uNode = reverseList(first);
        temp.next =null; //反转后temp是最后一个,它的next应为空,否则遍历懂的
        temp = null;
        System.out.println("反转完成");
    }

    public void reversal() {
        Node<U> control_node = getNode(0);
        Node<U> wheel_node = control_node.next;
        Node<U> prev_node = null;
        for (int i = 0; i < size; i++) {
            if(i == 0) {
                control_node.next = null;
            }else {
                control_node.next = prev_node;
            }
            if(i != size-1) {
                //操作对象改变
                prev_node = control_node;
                control_node = wheel_node;
                wheel_node = wheel_node.next;
            }

        }
        first = control_node;
    }

    public boolean isCircle() {
        if(first == null || first.next == null) return false;
        Node<U> slow = first;
        Node<U> fast = first.next;
        int i = 1;
        while (fast != null && fast.next != null) {
            if(fast.equals(slow)) {
                System.out.println("开始结束："+(i++)+",slow="+slow.unit+",fast="+fast.unit);
                return true;
            }
            System.out.println("开始循环："+(i++)+",slow="+slow.unit+",fast="+fast.unit);
            slow = slow.next;
            fast = fast.next.next;
        }

        return false;
    }

    public void sendHasCircle() {
        getNode(size-1).next = first;
    }






}
