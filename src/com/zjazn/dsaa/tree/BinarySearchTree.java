package com.zjazn.dsaa.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<U> extends BinaryTree<U>  {

    private Comparator<U> comparator;
    public  BinarySearchTree() {
        this.comparator = null;
    }
    public  BinarySearchTree(Comparator<U> comparator) {
        this.comparator = comparator;
    }

    public void add(U u) {
        checkFromNull(u);
        //插入第一个节点
        if(size == 0) {
            root = new Node<U>(u,null);
            size++;
            return;
        }
        //查看插在树的位置
        Node<U> parent = this.root;
        Node<U> node = this.root;
        int direction = 0;
        while (parent != null) {
            direction = compare(parent.unit,u); //保存比较值
            node = parent;
            if(direction > 0) { //parent的大
                parent = parent.left;
            }else if(direction < 0) { //parent小
                parent = parent.right;
            }else { //与父元素相等
                return; //当相等时，当前add无动作
            }
        }
        Node<U> uNode = new Node<>(u, parent);
        if(direction > 0) {
            node.left = uNode;
            uNode.parent = node;

        }else if(direction < 0) {
            node.right = uNode;
            uNode.parent = node;
        }
        size++;

    }

    //翻转二叉树
    public void switch_true() {
        switch_true_child(root);
    }
    private void switch_true_child(Node<U> node) {
        if(node.left == null && node.right == null) return;
        Node<U> temp = node.left;
        node.left = node.right;
        node.right = temp;
        if(node.left != null) switch_true_child(node.left);
        if(node.right != null) switch_true_child(node.right);
    }
    //使用迭代的方法翻转二叉树
    public void invertTree() {
        Queue<Node<U>> queue = new  LinkedList<>();
        queue.add(root);
        while (! queue.isEmpty()) {

            Node<U> poll_node = queue.poll();

            //在弹出时就将它的左右子节点翻转，其实很简单，就是在输出时或都涉及到每个元素的输出时就进行翻转即可
            //所以在前序，后序都可以进行二叉树的翻转或说翻转就是遍历
            Node<U> temp  = poll_node.left;
            poll_node.left = poll_node.right;
            poll_node.right = temp;

            if(poll_node.left != null) queue.add(poll_node.left);
            if(poll_node.right != null) queue.add(poll_node.right);
        }
    }


    //重构二叉树：利用遍历的结构可推出原二叉树
    //前序遍历 + 中序遍历
    //后序遍历 + 中序遍历
    //前序遍历 + 后序遍历  当是一个真二叉树时

    /**
     * 删除元素，如果是叶子节点，让父节点的指向为null，如果是度为1的节点，让父节点的指向被删除节点的子节点，
     *         如果是度为2的节点，那么需要找左节点的前驱或右节点的后继来替换被删除的节点，然后再删除这个前驱或后继节点（它的度为0或1）
     *         还需要看删除的是否是根节点，且需要维护父节点
     * @param
     */

    public void remove(U unit) {
        Node<U> node = node(unit);
        if (node == null) return;
        size--;
        if( node.isPlump() ) {
            //删除的是度为2的节点, next_node是后继节点
            Node<U> next_node = rearNode(node);
            U new_unit = next_node.unit;
            remove(new_unit);
            node.unit = new_unit;

        }else {
            if ( node.isLeaf() ) {
                //度为0的节点/叶子节点
                if(node.parent == null) {
                    root = null;
                }else if(node.parent.left == node){
                    node.parent.left = null;
                }else {
                    node.parent.right = null;
                }

            }else {
                //度为1的节点
                Node<U> _parent = node.parent;
                if(node.parent == null) {
                    //度为1的根节点
                    root = node.left!=null?node.left:node.right;
                    root.parent = null;

                }else if(node.parent.left == node){
                    node.parent.left = node.left!=null?node.left:node.right;
                    _parent.left.parent = _parent;
                }else {
                    node.parent.right = node.left!=null?node.left:node.right;
                    _parent.right.parent = _parent;
                }

            }


        }

    }
    protected Node<U> node(U unit) {
        if(unit == null) return null;
        Node<U> _node = root;
        while (_node != null) {
            int compare = compare(_node.unit, unit);
            if(compare == 0) return _node;
            if(compare > 0) {
                _node = _node.left;
            }else {
                _node = _node.right;
            }
        }
        return null;
    }
    //遍历树
    public void iteration(Visitor<U> visitor ) {
        //inorderTraversal 中序
        //preorderTraversal 前序
        //postderTraversal 后序
        postderTraversal(root,visitor);
    }
    //比较两个节点，返回0时，两个元素相同,大于0左边大
    public int compare(U u1, U u2) {
        if(comparator != null) {
            return comparator.compare(u1,u2);
        }
        return ((Comparable<U>)u1).compareTo(u2);
    }



}


