package org.zhuangjie.tree;

import java.util.Comparator;

/**
 * 红黑树
 */
public class RBTree<E> extends BBSTree<E> {
    public RBTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }
    public RBTree() {
        // 允许不传比较器，但类必须是可比较的（实现了 Comparator）
    }
    @Override
    public Object string(Object node) {
        RBNode<E> nodeObj = (RBNode<E>) node;
        return super.string(node) + "color(" + (nodeObj.color == RED ? "红" : "黑") + ")";
    }

    private static final boolean BLACK = true;
    private static final boolean RED = false;

    // 构造一个红黑树节点
    private static class RBNode<E> extends Node<E> {
        // 创建的节点默认是红色
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }
    }

    /**
     * 节点染色
     */
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return null;
        ((RBNode) node).color = color;
        return node;
    }

    /**
     * 将节点染成红色
     */
    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    /**
     * 将节点染成黑色
     */
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    /**
     * 查看节点的颜色
     */
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode) node).color;
    }

    /**
     * 是否为黑色节点
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 是否为红色节点
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    public void add(E element) {
        Node node = new RBNode(element, null);
        super.add(node);
        afterAdd(node);
    }

    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        // 添加的新节点或上溢到达了根节点
        if (parent == null) {
            // 添加的是根节点
            black(node);
            return;
        }
        // 当父节点是黑色
        if (isBlack(parent)) return;
        // 叔父节点
        Node<E> uncle = parent.sibling();
        Node<E> grand = parent.parent;
        if (isRed(uncle)) {
            // 当叔父节点是红色，当前就满天4节点了，需要上溢
            black(parent);
            black(uncle);
            red(grand);
            afterAdd(grand);
            return;
        }
        // 当叔父节点不是红色，那就需要通过旋转来恢复平衡
        if (parent.isLeftChild()) { //  L
            if (node.isLeftChild()) { // LL
                rotateRight(grand);
                black(parent);
                red(grand);
            } else { // LR
                rotateLeft(parent);
                rotateRight(grand);
                black(node);
                red(grand);
            }
        } else if (parent.isRightChild()) {  // R
            if (node.isRightChild()) { // RR
                rotateLeft(grand);
                black(parent);
                red(grand);
            } else { // RL
                rotateRight(parent);
                rotateLeft(grand);
                black(node);
                red(grand);
            }

        }


    }

    public void remove(E e) {
        /**
         * 下面变量说明：
         * node是要删除的节点
         * removeNode 是实际删除的节点
         * replaceNode 是替换被实际删除节点的节点
         */
        Node<E> node = super.node(e);
        if (node == null) return;
        Node<E> removeNode = node;
        Node<E> replaceNode = null;
        if (! node.isLeaf()) {
            if (node.isFull()) {
                // 度为2的节点
                removeNode = getPostNode(node);
            }
            replaceNode = removeNode.left != null ? removeNode.left : removeNode.right;
        }
        super.remove(node);
        afterRemove(removeNode,replaceNode);

    }

    protected void afterRemove(Node<E> node, Node<E> removeNode) {
        // 如果删除的节点是红色，不用做任何处理
        if (isRed(node)) return;

        // 删除的是黑色
        // 用于替换的节点是红色，那就需要将替换的节点染为黑色
        if (isRed(removeNode)) {
            black(removeNode);
            return;
        }

        Node<E> parent = node.parent;
        // 删除的是根节点
        if (parent == null) return;

        // 删除的是黑色叶子节点 &下溢
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            // 如果兄弟节点是红色（无法借东西），需要转为兄弟节点为黑色的情况再处理
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }


}
