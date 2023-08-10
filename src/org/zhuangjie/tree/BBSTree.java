package org.zhuangjie.tree;

/**
 * 平衡功能的二叉树
 * @param <E>
 */
public abstract class BBSTree<E> extends BSTree<E>{

    protected void rotateLeft(Node<E> gNode) {
        /**
         *     口g              |       口p
         *        口p           |   口g     口n
         *           口n        |
         */
        Node<E> pNode = gNode.right;
        // Node<E> nNode = pNode.right;
        Node<E> pLeftChild = pNode.left;
        gNode.right = pLeftChild;
        pNode.left = gNode;

        afterRotate(gNode,pNode,pLeftChild);


    }

    protected void rotateRight(Node<E> gNode) {
        /**
         *         口g        |       口p
         *      口p           |   口n     口g
         *   口n              |
         */
        Node<E> pNode = gNode.left;
        // Node<E> nNode = pNode.left;
        Node<E> pRightChild = pNode.right;
        gNode.left = pRightChild;
        pNode.right = gNode;
        afterRotate(gNode,pNode,pRightChild);

    }

    protected void afterRotate(Node<E> gNode, Node<E> pNode, Node<E> nSiblingNode) {
        if (gNode.isLeftChild()) {
            gNode.parent.left = pNode;
        }else if (gNode.isRightChild()) {
            gNode.parent.right = pNode;
        }else {
            root = pNode;
            pNode.parent = null;
        }
        pNode.parent = gNode.parent;
        gNode.parent = pNode;
        if (nSiblingNode != null) {
            nSiblingNode.parent = gNode;
        }

    }
}
