package org.zhuangjie.tree;

import java.util.Comparator;

/**
 * 平衡二叉搜索树
 * @author zhuangjie
 * @param <E>
 */
public class AVLTree<E> extends BBSTree<E> {
    public AVLTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }
    public AVLTree() {
        // 允许不传比较器，但类必须是可比较的（实现了 Comparator）
    }

    @Override
    public Object string(Object node) {
        AVLNode<E> nodeObj = (AVLNode<E>) node;
        return super.string (node)+"h("+nodeObj.height+")";
    }

    /**
     * AVL的Node节点
     * @param <E>
     */
    private static class AVLNode<E> extends Node<E> {
        // 高度，默认值为1，因为新节点肯定是叶子节点。
        int height = 1;
        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }
        // 获取节点的平衡因子：左子节点高度减去右子节点高度
        public int balanceFactor() {
            int leftHeight = left == null? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null? 0 : ((AVLNode<E>)right).height;
            return Math.abs(leftHeight - rightHeight);
        }

        // 更新节点高度
        public void updateHeight() {
            Node<E> tallerChildNode = tallerChild();
            int tallerChildHeight = tallerChildNode == null?0:((AVLNode<E>)tallerChildNode).height;
            height = 1 + tallerChildHeight;
        }
        // 判断是否平衡
        public boolean isBalanced() {
            return balanceFactor() <= 1;
        }
        // 获取最高的那个节点
        public Node<E> tallerChild() {
            int leftHeight = left == null? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null? 0 : ((AVLNode<E>)right).height;
            return leftHeight > rightHeight ? left:right;
        }
    }

    public void add(E element) {
        Node node = new AVLNode(element, null);
        super.add(node);
        // 判断节点是否平衡
        while ((node = node.parent) != null) {
            if (((AVLNode<E>)node).isBalanced()) {
                // 是平衡的，更新高度
                ((AVLNode<E>)node).updateHeight();
            }else {
                // 不是平衡的，恢复平衡
                balance(node);
                // 恢复最近不平衡的节点，整棵数就是平衡的了
                break;
            }
        }
    }

    private void balance(Node<E> gNode) {
        Node<E> pNode = ((AVLNode<E>) gNode).tallerChild();
        Node<E> nNode = ((AVLNode<E>) pNode).tallerChild();
        if (pNode.isLeftChild()) {
            // LL 或 LR
            if (nNode.isLeftChild()) {
                // LL（右旋转）
                rotateRight(gNode);
            }else {
                // LR(左旋转 右旋转)
                rotateLeft(pNode);
                rotateRight(gNode);
            }
        }else {
            // RR 或 RL
            if (nNode.isRightChild()) {
                // RR (左旋转)
                rotateLeft(gNode);
            }else {
                // RL (右旋转，左旋转)
                rotateRight(pNode);
                rotateLeft(gNode);
            }

        }

    }

    /**
     * 重写afterRotate添加更新高度逻辑
     * @param gNode
     * @param pNode
     * @param nSiblingNode
     */
    @Override
    protected void afterRotate(Node<E> gNode, Node<E> pNode, Node<E> nSiblingNode) {
        super.afterRotate(gNode, pNode, nSiblingNode);
        // 更新高度顺序不能变
        ((AVLTree.AVLNode<E>)gNode).updateHeight();
        ((AVLTree.AVLNode<E>)pNode).updateHeight();
    }

    public void remove(E e) {
        Node<E> node = super.node(e);
        super.remove(node);
        while ((node = node.parent) != null) {
            if (((AVLNode<E>)node).isBalanced()) {
                // 是平衡的，更新高度
                ((AVLNode<E>)node).updateHeight();
            }else {
                // 不是平衡的，恢复平衡
                balance(node);
            }
        }
    }

}
