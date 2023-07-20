package org.zhuangjie.tree;

import org.zhuangjie.tree.printer.BinaryTreeInfo;
import org.zhuangjie.tree.printer.BinaryTrees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树
 * @param <E>
 */
public abstract class BinaryTree<E> implements BinaryTreeInfo {
    // === 需要实现的方法（四个方法）
    @Override
    public Object root() {
        return this.root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return  ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> nodeObj = (Node<E>) node;
        // 前驱节点
        E preElement = null;
        E postElement = null;
        Node<E> preNode = getPreNode(nodeObj);
        Node<E> postNode = getPostNode(nodeObj);
        if (preNode != null) preElement = preNode.element;
        if (postNode != null) postElement = postNode.element;
        return  nodeObj.element+"_p("+(nodeObj.parent==null?null:nodeObj.parent.element)+")_pre("+preElement+")_post("+postElement+")";
    }

    protected static class Node<E> {
        // 存放元素
        E element;
        // 左节点
        Node<E> left;
        // 右节点
        Node<E> right;
        // 父节点
        Node<E> parent;
        // 构造函数
        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
        // 叶子节点：度为0
        public boolean isLeaf() {
            return left == null && right == null;
        }
        // 度为2
        public boolean isFull() {
            return left != null && right != null;
        }
        // 判断是否为左子节点
        public boolean isLeftChild() {
            return this.parent != null && parent.left == this;
        }
        // 判断是否为右子节点
        public boolean isRightChild() {
            return this.parent != null && parent.right == this;
        }

    }
    
    protected int size; // 元素的个数
    protected Node<E> root; // 根节点
    protected Comparator<E> comparator; // 比较器

    // 元素的数量
    public int size(){
        return size;
    }

    // 是否为空
    public boolean isEmpty() {
        if (this.size == 0 || root == null) {
            return true;
        }
        return false;
    }

    /**
     * 比较方法
     * @param e1
     * @param e2
     * @return
     */
    protected double compare(E e1,E e2) {
        if (comparator != null) {
            // 使用成员变量中自定义的比较器
            return comparator.compare(e1,e2);
        }
        // 这时候就E类型就必须是可以比较的了，必须报错。
        return ((Comparable<E>)e1).compareTo(e2);
    }
    // 清空所有元素
    public void clear() {
        this.root = null;
        this.size = 0;
    }
    protected void elementNotNullCheck(E element) {
        if (element == null) {
            throw  new IllegalArgumentException("元素不能为空");
        }
    }
    /**
     * 前序遍历
     * @param node
     * @param list
     */
    public void preorderTraversal(Node<E> node, List<E> list) {
        if (node == null) return;
        list.add(node.element);
        preorderTraversal(node.left,list);
        preorderTraversal(node.right,list);
    }

    /**
     *  中序遍历
     * @param node
     * @param list
     */
    public void inorderTraversal(Node<E> node, List<E> list) {
        if (node == null) return;
        inorderTraversal(node.left,list);
        list.add(node.element);
        inorderTraversal(node.right,list);
    }

    /**
     * 后序遍历
     * @param node
     * @param postorderList
     */
    private void postorderTraversal(Node<E> node, List<E> postorderList) {
        if (node == null) return;
        postorderTraversal(node.left,postorderList);
        postorderTraversal(node.right,postorderList);
        postorderList.add(node.element);
    }

    /**
     * 层次遍历
     * @param node
     * @param postorderList
     */
    private void levelOrderTraversal(Node<E> node, List<E> postorderList) {
        if (node == null) return;
        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        while (! queue.isEmpty()) {
            Node<E> currentNode = queue.poll();
            postorderList.add(currentNode.element);
            if (currentNode.left!= null) queue.offer(currentNode.left);
            if (currentNode.right!= null) queue.offer(currentNode.right);
        }
    }

    /**
     * 计算二叉树的高度-递归
     * @return
     */
    public int heightForRecursion(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(heightForRecursion(node.left),heightForRecursion(node.right));
    }
    /**
     * 计算二叉树的高度-使用层次遍历方式
     * @return
     */
    public int heightForLevelOrder(Node<E> node) {
        if (node == null) return 0;
        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        // 树的高度
        int height = 0;
        int currentLevelNodeUnPollCount = 1;
        while (! queue.isEmpty()) {
            Node<E> currentNode = queue.poll();
            currentLevelNodeUnPollCount--;
            if (currentNode.left != null) queue.offer(currentNode.left);
            if (currentNode.right != null) queue.offer(currentNode.right);
            if (currentLevelNodeUnPollCount == 0) {
                currentLevelNodeUnPollCount = queue.size();
                height++;
            }
        }
        return height;
    }
    /**
     * 前驱节点: 获取某个节点中序遍历结果的前一个节点
     * @return
     */
    public Node<E> getPreNode(Node<E> node) {
        if (node == null) return null;
        Node<E> preNode = node.left;
        if (preNode != null) {
            // 往下找：node.left.right.right.right...
            while (preNode.right != null) preNode = preNode.right;
            return preNode;
        }
        // 往上找：node.parent.parent.parent...
        while (node.parent != null && node.parent.left == node) node = node.parent;

        // node.parent != null
        // node.parent.right == node
        return node.parent;
    }
    /**
     * 后驱节点: 获取某个节点中序遍历结果的后一个节点
     * @return
     */
    public Node<E> getPostNode(Node<E> node) {
        if (node == null) return null;
        Node<E> preNode = node.right;
        if (preNode != null) {
            // 往下找：node.right.left.left.left...
            while (preNode.left != null) preNode = preNode.left;
            return preNode;
        }
        // 往上找：node.parent.parent.parent...
        while (node.parent != null && node.parent.right == node) node = node.parent;

        // node.parent != null
        // node.parent.left == node
        return node.parent;
    }
    /**
     * 判断是否为完全二叉树
     * @return
     */
    public boolean isComplete(Node<E> node) {
        if (node == null) return false;
        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        int currentNodeChildrenCount = 2;
        while (! queue.isEmpty()) {
            Node<E> currentNode = queue.poll();
            if (currentNodeChildrenCount == 0 ) {
                if(!currentNode.isLeaf()) return false;  // 看如果要求当前节点度是0时，如果当前处理的节点不是叶子节点时，返回false
            }else if (currentNode.isLeaf()){
                currentNodeChildrenCount = 0; // 如果当前要求的度不0时，如果当前处理的节点是叶子节点时，也将currentNodeChildrenCount = 0
            }
            if (currentNode.left != null) {
                queue.offer(currentNode.left);
            }else if (! currentNode.isLeaf()){
                // left == null && right != null
                return false;
            }
            if (currentNode.right != null) {
                queue.offer(currentNode.right);
            }else if(! currentNode.isLeaf()) {
                //  left != null && right == null
                currentNodeChildrenCount = 0;
            }
        }
        return true;
    }

    /**
     * 二叉树翻转
     * @return
     */
    public Node<E> invertTree(Node<E> node) {
        if (node == null) return null;
        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        while (! queue.isEmpty()) {
            Node<E> currentNode = queue.poll();
            // 对 currentNode 节点进行操作
            Node<E> tmp = currentNode.left;
            currentNode.left = currentNode.right;
            currentNode.right = tmp;

            if ( currentNode.left != null ) queue.offer(currentNode.left);
            if ( currentNode.right != null ) queue.offer(currentNode.right);

        }
        return node;
    }

    @Override
    public String toString() {
        String treeStr = BinaryTrees.printString(this);
        List<E> preorderList = new ArrayList<>();
        List<E> inorderList = new ArrayList<>();
        List<E> postorderList = new ArrayList<>();
        List<E> levelorderList = new ArrayList<>();
        preorderTraversal(root,preorderList);
        inorderTraversal(root,inorderList);
        postorderTraversal(root,postorderList);
        levelOrderTraversal(root,levelorderList);
        String result = treeStr+"\n前序遍历（"+preorderList+"）\n中序遍历（"+inorderList+"）\n后序遍历（"+postorderList+")\n层序遍历（"+levelorderList+"）";
        result += "\n【树的高度】："+heightForLevelOrder(root);
        result += "\n【是否为完全二叉树】："+isComplete(root);

        return result;
    }


}
