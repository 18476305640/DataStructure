package com.zjazn.dsaa.tree;

public class AVL<U> extends BinarySearchTree<U>{

    protected static class AVLNode<U> extends Node<U>{
        int height;
        public AVLNode(U unit, Node<U> parent) {
            super(unit, parent);
        }
    }
    //因为使用AVLNode替代Node，所以需要重写
    public void add(U u) {
        checkFromNull(u);
        //插入第一个节点
        if(size == 0) {
            root = new AVLNode<U>(u,null);
            size++;
            //新添加节点的是叶子节点即高度为1
            ((AVLNode)root).height = 1;
            Balance((AVLNode<U>) root);
            return;
        }
        //查看插在树的位置
        AVLNode<U> parent = (AVLNode)this.root;
        AVLNode<U> node = (AVLNode)this.root;
        int direction = 0;
        while (parent != null) {
            direction = compare(parent.unit,u); //保存比较值
            node = parent;
            if(direction > 0) { //parent的大
                parent = (AVLNode<U>) parent.left;
            }else if(direction < 0) { //parent小
                parent = (AVLNode<U>) parent.right;
            }else { //与父元素相等
                return; //当相等时，当前add无动作
            }
        }
        AVLNode<U> uNode = new AVLNode<>(u, parent);
        if(direction > 0) {
            node.left = uNode;
            uNode.parent = node;

        }else if(direction < 0) {
            node.right = uNode;
            uNode.parent = node;
        }
        size++;
        //新添加节点的是叶子节点即高度为1
        uNode.height = 1;
        Balance(uNode);

    }
    //恢复平衡
    protected void Balance(AVLNode<U> node) {
        AVLNode<U> g_node = (AVLNode<U>) node.parent;
        while (g_node != null) {
            if (!isBalance(g_node)) {
                //不平衡，恢复平衡
                System.out.println("失衡了,g是："+g_node.unit);
                //开始判断类型
                AVLNode<U> p_node = tall_child_node(g_node);
                AVLNode<U> n_node = tall_child_node(p_node);
                if(tall_child_node(g_node) == g_node.left) {
                    //LL或LR
                    System.out.println("LL或LR");
                    if (n_node == p_node.left) {
                        //LL
                        System.out.println("LL");
                        LL_Balance(g_node,p_node);
                    }else {
                        //LR
                        System.out.println("LR");
                        LR_Balance(g_node,p_node,n_node);
                    }
                }else {
                    //RR或RL
                    System.out.println("RR或RL");
                    if (n_node == p_node.right) {
                        //RR
                        System.out.println("RR");
                        //调用指定类型恢复平衡的方法
                        RR_Balance(g_node,p_node);

                    }else {
                        //RL
                        System.out.println("RL");
                        RL_Balance(g_node,p_node,n_node);
                    }

                }
                return;

            }else {
                //平衡——更新高度
                AVLNode<U> height_max_node = tall_child_node(g_node);
                if (height_max_node == null) {
                    g_node.height = 1;
                }else {
                    g_node.height = height_max_node.height + 1;
                }


            }
            g_node = (AVLNode<U>)g_node.parent;
        }
    }
    //判断传入的树是否为平衡状态
    protected Boolean isBalance(AVLNode<U>  node) {
        int left_height = ((AVLNode<U>) node.left) == null ? 0: ((AVLNode<U>) node.left).height;
        int right_height = ((AVLNode<U>) node.right) == null ? 0: ((AVLNode<U>) node.right).height;
        //System.out.println(node.unit+"=>正在判断是否失衡,left="+left_height+"，right="+right_height);
        return Math.abs(left_height - right_height) < 2;

    }
    //返回高度高的子节点
    protected AVLNode<U> tall_child_node(AVLNode<U> node) {
        AVLNode<U> left_node = (AVLNode<U>) node.left;
        AVLNode<U> right_node = (AVLNode<U>) node.right;
        int left_height = left_node == null ? 0: left_node.height;
        int right_height = right_node == null ? 0: right_node.height;
        return (left_height > right_height)?left_node:right_node;
    }
    //本质是依靠子节点的高度更新自身的高度
    protected void update_node_height(AVLNode<U> node) {
        AVLNode<U> max_tall_child_node = tall_child_node(node);
        if (max_tall_child_node != null) {
            node.height = max_tall_child_node.height+1;
        }else {
            node.height = 1;
        }
    }

    /**
     * RR型恢复平衡
     * 程序核心：”向大树接“，就是p先接替g节点的位置，然后把g节点的东西向”大树“接上。
     * 特别要注意：当不平衡点是root节点时，此时不平衡节点的parent是root
     * @param g_node
     * @param p_node
     */
    protected void RR_Balance(AVLNode<U> g_node,AVLNode<U> p_node) {
        //获取g_node节点的父节点
        AVLNode<U> g_node_parent = (AVLNode<U>) g_node.parent;

        //分三种情况,将g_node挂上树上,注意需要维护g_node 的parent
        if (g_node_parent != null) {
            if (g_node_parent.left == g_node) {//g_node节点要挂在g_node_parent的左节点上
                g_node_parent.left = p_node;
                p_node.parent = g_node_parent;
            }else {//g_node节点要挂在g_node_parent的右节点上
                g_node_parent.right = p_node;
                p_node.parent = g_node_parent;
            }
        }else {//如果不平衡点是根节点时
            root = p_node;
            p_node.parent = null;
        }
        //卸下p_node的左节点,保存下来
        AVLNode<U> p_node_left_node =  (AVLNode<U>)p_node.left;
        //将g_node节点放在p_node上并维护好g_node的parent属性
        p_node.left = g_node;
        g_node.parent = p_node;
        if (p_node_left_node != null) {
            //将保留下来的p_node的左节点放在g_node节点上并维护好parent
            g_node.right = p_node_left_node;
            p_node_left_node.parent = g_node;
        }else {
            g_node.right = null;
        }

        //维护height： 更新g_node节点高度，再更新p_node节点的高度
        update_node_height(g_node);
        update_node_height(p_node);
    }

    /**
     * LL型采用的是右旋
     * 在RR型的基础上,将left变为right,right变为left,是相反的
     * @param g_node
     * @param p_node
     */
    protected void LL_Balance(AVLNode<U> g_node,AVLNode<U> p_node) {
        //获取g_node节点的父节点
        AVLNode<U> g_node_parent = (AVLNode<U>) g_node.parent;

        if (g_node_parent != null) {
            if (g_node_parent.left == g_node) {
                g_node_parent.left = p_node;
                p_node.parent = g_node_parent;
            }else {
                g_node_parent.right = p_node;
                p_node.parent = g_node_parent;
            }
        }else {
            //如何不平衡点是根节点时
            root = p_node;
            p_node.parent = null;
        }
        AVLNode<U> p_node_right_node =  (AVLNode<U>)p_node.right;
        p_node.right = g_node;
        g_node.parent = p_node;

        if (p_node_right_node != null) {
            g_node.left = p_node_right_node;
            p_node_right_node.parent = g_node;
        }else {
            g_node.left = null;
        }
        //维护height： 更新g_node节点高度，再更新p_node节点的高度
        update_node_height(g_node);
        update_node_height(p_node);


    }

    /**
     * RL型,对下面的p,n看作LL型,进行右旋,然后对上面的g,n看作RR进行左旋
     * @param g_node
     * @param p_node
     * @param n_node
     */
    protected void RL_Balance(AVLNode<U> g_node,AVLNode<U> p_node,AVLNode<U> n_node) {
        LL_Balance(p_node,n_node);
        RR_Balance(g_node,n_node);
    }

    /**
     * LR型,对下面的p,n看作RR型,进行左旋,然后对上面的g,n看作LL进行右旋
     * @param g_node
     * @param p_node
     * @param n_node
     */
    protected void LR_Balance(AVLNode<U> g_node,AVLNode<U> p_node,AVLNode<U> n_node) {
        RR_Balance(p_node,n_node);
        LL_Balance(g_node,n_node);
    }

}
