package com.zjazn.dsaa.tree;

import com.zjazn.dsaa.tree.printer.BinaryTreeInfo;
import com.zjazn.dsaa.tree.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class BinaryTree<U>  implements BinaryTreeInfo {
    protected int size = 0;
    protected Node<U> root;
    protected static class Node<U> {
        U unit;
        Node<U> parent;
        Node<U> left;
        Node<U> right;
        public Node(U unit, Node<U> parent) {
            this.unit = unit;
            this.parent = parent;
        }
        boolean isLeaf() {
            if(this.left != null || this.right != null) {
                return false;
            }
            return true;

        }
        boolean isPlump() {
            return this.left != null && this.right != null;
        }
    }



    protected static abstract class Visitor<U> {
        public boolean stop = false;
        public abstract boolean visit(U unit);
    }


    /**
     * 关于遍历：前中后只是访问的位置不同+遍历的左右的前后不同（要有单元性的思想），代码一样，层序遍历更简单，层序遍历是要记住的遍历。
     * 层序遍历=	5	2	10	1	3	7	11
     * 中序遍历=	1	2	3	5	7	10	11
     * 前序遍历=	5	2	1	3	10	7	11
     * 后序遍历=	2	1	3	10	7	11	5
     */
    public void checkFromNull(U u) {
        if(u == null) throw new IllegalArgumentException("插入的节点为空");
    }

    //前序遍历
    protected void preorderTraversal(Node<U> node, Visitor<U> visitor) {
        if(node == null || visitor.stop) return;
        visitor.stop = visitor.visit(node.unit);
        if(visitor.stop) return;
        preorderTraversal(node.left,visitor);
        if(visitor.stop) return;
        preorderTraversal(node.right,visitor);
    }
    //后序遍历
    protected void postderTraversal(Node<U> node, Visitor<U> visitor) {
        if(node == null || visitor.stop) return;
        visitor.stop = visitor.visit(node.unit);
        if(visitor.stop) return;
        postderTraversal(node.right,visitor);
        if(visitor.stop) return;
        postderTraversal(node.left,visitor);


    }
    //中序遍历  升降序
    protected void inorderTraversal(Node<U> node,Visitor<U> visitor) {
        if(node == null) return;
        inorderTraversal(node.left,visitor);
        if(visitor.stop) return;
        visitor.stop = visitor.visit(node.unit);
        System.out.print("_"+node.unit+"的前驱节点是："+(rearNode(node) != null?rearNode(node).unit:"null")+"_\t");
        inorderTraversal(node.right,visitor);
    }
    //层序遍历
    protected void LevelOrderTraversal(Node<U> node,Visitor<U> visitor) {
        Queue<Node<U>> us = new LinkedList<>();
        us.add(node);
        while (!us.isEmpty()) {
            Node<U> uNode = us.poll();
            if(visitor.visit(uNode.unit)) return;
            if (uNode.left != null) us.add(uNode.left);
            if (uNode.right != null) us.add(uNode.right);
        }
    }
    //使用递归计算二叉树高度
    public int height() {
        return get_node_height(root);
    }
    private int get_node_height(Node<U> node) {
        if(node == null) return 0;
        return 1 + Math.max(get_node_height(node.left),get_node_height(node.right));
    }
    //使得迭代计算二叉树高度
    public int tree_height() {
        //高度
        int height = 0;
        //当前层剩下的节点
        int o = 1;
        Queue<Node<U>> us = new LinkedList<>();
        us.add(root);
        while (!us.isEmpty()) {
            Node<U> poll_node = us.poll();
            if(poll_node.left != null) us.add(poll_node.left);
            if(poll_node.right != null) us.add(poll_node.right);
            if (--o == 0) {
                height++;
                o = us.size();
            }
        }

        return height;
    }
    //判断是否是完全二叉树
    public boolean isComplete() {
        boolean leaf = false;
        Queue<Node<U>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {

            Node<U> poll_node = queue.poll();
            if(leaf && !poll_node.isLeaf()) return false; //使用节点提供的方法判断这个节点是否是叶子节点
            if(poll_node.left != null) {
                queue.add(poll_node.left);
            }else if(poll_node.right != null){
                return false;
            }


            if(poll_node.right != null) {
                queue.add(poll_node.right);
            }else if(poll_node.left != null) {
                System.out.println("后面必须都是叶子节点");
                leaf = true;
            }
        }
        return true;
    }
    /**
     * 前驱节点
     * 思想：看左子节点是否存在，如果存在，那么是node.left.right.right...
     *      如果不存在，看父节点，如果自己是父节点的右节点，那么父节点就是前驱节点，否则是左子节点，需要向上”爬“，直到找到当前节点是父节点的右节点
     *
     * @param node
     * @return
     */

    public Node<U> precursorNode(Node<U> node) {
        if (node == null) return null;
        Node<U> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }
        p = node.parent;
        while (p != null ) {
            if (node == p.right) {
                return  p;
            }else {
                node = p;
                p = p.parent;
            }
        }
        return null;

    }

    /**
     * 后继节点
     * 看上面节点的思想，与前驱节点思想相反，代码left改为right，right改为left即可
     * @param node
     * @return
     */
    public Node<U> rearNode(Node<U> node) {
        if (node == null) return null;
        Node<U> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;


        }
        p = node.parent;
        while (p != null ) {
            if (node == p.left) {
                return  p;
            }else {
                node = p;
                p = p.parent;
            }
        }
        return null;

    }


    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<U>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<U>)node).right;
    }

    @Override
    public Object string(Object node) {
        Node<U> parent = ((Node<U>) node).parent;
        if ( parent ==null ) {
            return ((Node<U>)node).unit+"(NULL)";
        }else {
            return ((Node<U>)node).unit+"("+parent.unit+")";
        }
    }

    /**
     * 当输入树时，会输出树的结构字符串
     * @return
     */
    @Override
    public String toString() {
        return BinaryTrees.printString(this);
    }

}
