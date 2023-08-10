package org.zhuangjie.tree;


import java.util.Comparator;

/**
 * 二叉搜索树
 * @param <E>
 */
public class BSTree<E> extends BinaryTree<E> {
    public BSTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }
    public BSTree() {
        // 允许不传比较器，但类必须是可比较的（实现了 Comparator）
    }
    // 添加元素
    public void add(E element) {
        add(new Node<>(element, null));
    }
    protected void add(Node<E> newNode) {
        E newElement = newNode.element;
        // 【1】门外保安检查是你是否有票，如果没有票轰出去
        elementNotNullCheck(newElement);
        // 【2】看是不是第一个来的，如果是直接上坐
        if (isEmpty()) {
            this.root = newNode;
            this.size = 1;
            return;
        }
        // 【2】我要坐在这里
        Node<E> parent = null;
        Node<E> node = this.root;
        double cmp = 0;
        // 【3】看位置是否为空
        while (node != null) {
            // 【3.1】位置不为空，那我要顺着找找空位置了
            parent = node;
            cmp = compare(newElement,node.element);
            if (cmp == 0) {
                // 【3.2】有人帮我占位置，直接上座
                // 这里直接替换，而不是两者相等就返回
                node.element = newElement;
                return;
            }
            // 【3.2】寻找
            if (cmp > 0) {
                // 传入的元素比node元素小
                node = node.right;
            }else {
                // 传入的元素比node元素大
                node = node.left;
            }
        }
        // 【4】找到空位置了，直接上坐
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;

    }

    // 是否包含某元素
    public boolean contains(E element) {
        return node(element) != null;
    }
    /**
     * 删除元素
     * @return
     */
    public void remove(E e) {
        remove(node(e));
    }

    protected Node<E> node(E e) {
        Node<E> node = root;
        while (node != null) {
            double compare = compare(e, node.element);
            if (compare == 0) return node;
            if (compare > 0) {
                node = node.right;
            }else {
                node = node.left;
            }
        }
        return null;
    }
    public void remove(Node<E> node) {
        if (node == null) return;
        if (node.isFull()) {
            // 度为2, 使用node的前驱或后继替换删除的元素
            Node<E> postNode = getPostNode(node);
            node.element = postNode.element;
            node = postNode; // 转为删除度为1或0的节点，让后面的代码逻辑删除
        }
        size--;
        // 删除度为1或度为0
        if (node.isLeaf()) {
            // 叶子节点 & 节点是根节点，那直接删除 root = null
            if (node.parent == null) {
                root = null;
                return;
            }
            if (node.parent.left == node){
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
        }else {
            // 度为1的节点-让要删除的节点的父节点指定要删节节点的唯一子节点
            Node<E> childNode = node.left != null?node.left:node.right;
            if (node.parent == null) {
                root = childNode;
                childNode.parent = null;
            }else {
                childNode.parent = node.parent;
                if (node.parent.left == node) {
                    node.parent.left = childNode;
                }else {
                    node.parent.right = childNode;
                }
            }
        }
    }







}