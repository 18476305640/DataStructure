package org.zhuangjie.map;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 哈希表
 * @param <K>
 * @param <V>
 */
public class HashMap <K,V>{
    private Node<K, V>[] table;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 1 << 4; //数组默认长度，16
    // 扩容的负载因子
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap() {
        this.table = new Node[DEFAULT_CAPACITY];
    }

    protected static class Node<K,V> {
        final int hash;
        final K k;
        V v;
        Chain<K,V> chain;
        protected Node(K k,V v) {
            this.hash = k == null?0:k.hashCode();
            this.k = k;
            this.v = v;
        }

        @Override
        public String toString() {
            return "Node{" +
                    " k=" + k +
                    ", v=" + v +
                    '}';
        }
    }

    protected interface Chain<K,V> {
        // 添加节点Node，如果原来已经存在值，就返回原来的value值,没有返回空
        Node<K,V> put(Node<K,V> node);

        Node<K,V> remove(K k);

        Node<K,V> getReplaceNode();

        Node<K,V> get(K k);

        boolean findValue(V value);

        boolean traversal(Function<Node<K, V>,Boolean> consume);
    }
    protected class LinkedChain<K,V> implements Chain<K,V> {
        private LinkedList<Node<K,V>> linkedList = new LinkedList<>();
        @Override
        public Node<K,V> put(Node<K,V> node) {
            if (node == null) return null;
            int index = find(node.k);
            // 没有找到
            if (index == -1) {
                linkedList.push(node);
                return null;
            }
            // 找到了
            Node<K, V> oldNode = linkedList.get(index);
            linkedList.set(index, node);
            return oldNode;
        }

        /**
         * 寻找并返回索引-好操作
         * @param k
         * @return
         */
        private int find(K k) {
            int targetIndex = -1;
            for (int i = 0; i < linkedList.size(); i++) {
                Node<K,V> currentNode = linkedList.get(i);
                if (Objects.equals(currentNode.k,k)) {
                    targetIndex = i;
                }
            }
            return targetIndex;
        }

        @Override
        public Node<K, V> remove(K k) {
            int index = find(k);
            //-1索引表示 没有找到
            if (index == -1) return null;
            Node<K, V> removeNode = linkedList.get(index);
            linkedList.remove(index);
            return removeNode;
        }

        @Override
        public Node<K, V> getReplaceNode() {
            return linkedList.poll();
        }

        /**
         * 从链表中找，返回找到的节点
         * @param k
         * @return
         */
        @Override
        public Node<K, V> get(K k) {
            int index = find(k);
            //-1索引表示 没有找到
            if (index == -1) return null;
            return linkedList.get(index);
        }

        /**
         * 查找是否有指定value值,并返回指定索引,没有找到返回-1
         * @param value
         * @return
         */
        @Override
        public boolean findValue(V value) {
            if (value == null) return false;
            for (int i = 0; i < linkedList.size(); i++) {
                Node<K,V> node = linkedList.get(i);
                if (node != null && Objects.equals( node.v,value ) ) return true;
            }
            return false;
        }

        @Override
        public boolean traversal(Function<Node<K, V>,Boolean> consume) {
            for (Node<K, V> node : linkedList) {
                Boolean isBreak = consume.apply(node);
                if (isBreak) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return linkedList.toString();
        }
    }

    protected int hash(Object object) {
        if (object == null) return 0;
        return object.hashCode() & (table.length - 1);
    }

    public V put(K k,V v) {
        Node<K,V> node = putNode(new Node<>(k, v));
        return node == null?null:node.v;
    }
    // 接收要Put的Node
    public Node<K,V> putNode(Node<K,V> node) {
        if (node == null) return null;
        reset();
        Node<K, V> putResultNode = put(node);
        if (putResultNode == null) {
            // 为空表示新添加而不是覆盖
            size++;
            return null;
        }
        // 是覆盖
        return putResultNode;
    }

    /**
     * 检查并调整容器
     */
    private void reset() {
        // 判断大小是否满足扩容的条件
        if (size / Float.valueOf( table.length ) >= DEFAULT_LOAD_FACTOR) {
            // 满足扩容条件-扩容
            Node<K,V>[] oldTable = table;
            // 赋值
            table = new Node[table.length << 1];
            // 挪动代码得放到最后面
            moveNode(oldTable);
        }

    }

    /**
     * 将哈希表移动
     * @param oldTable
     */
    private void moveNode(Node<K, V>[] oldTable) {
        for (Node<K, V> nodePro : oldTable) {
            // 槽位节点移动
            if (nodePro == null) continue;
            Chain<K, V> chain = nodePro.chain;
            nodePro.chain = null;
            put(nodePro);
            if (chain != null) chain.traversal(node-> {
                put(node);
                return true;
            });
        }
    }

    /**
     * put，如果之前已经有了，返回已经被覆盖的节点
     * @param node
     * @return
     */
    protected Node<K,V> put(Node<K, V> node) {
        if (node == null) return null;
        node.chain = null;  // 重置节点的chain
        K k = node.k;
        K v = node.k;
        int index = hash(k);
        Node<K,V> nodePro = table[index];
        if (nodePro == null ) {
            // 槽位未被占用
            table[index] = node;
            return null;
        }
        // 槽位已被占用
        if ( Objects.equals( nodePro.k,node.k)) {
            // 槽位就是-替换
            node.chain = nodePro.chain;
            table[index] = node;
            return nodePro;
        }
        // 链中插入
        Chain<K, V> chain = nodePro.chain;
        if (chain == null) nodePro.chain = chain = new LinkedChain<>();
        // 甩手给chain处理
        return chain.put(node);
    }

    /**
     * 返回的V是返回删除的Node的value
     * @param k
     * @return
     */
    public V remove(K k) {
        Node<K, V> node = removeNode(k);
        return node == null?null:node.v;
    }
    public Node<K,V> removeNode(K k) {
        int index = hash(k);
        Node<K, V> nodePro = table[index];
        if (nodePro == null) return null;
        Chain<K, V> chain = null;
        // 看槽点的值是否等于k
        if (Objects.equals(nodePro.k,k)) {
            // 槽点就是要找的
            // 需要准备一个替换节点
            Node<K,V> replaceNodePro = null;
            if (nodePro.chain != null) {
                chain = nodePro.chain;
                replaceNodePro = chain.getReplaceNode();
            }
            table[index] = replaceNodePro;
            if (replaceNodePro != null) replaceNodePro.chain = chain;
            size--;
            return nodePro;
        }
        // 链为空，肯定没有找到
        if (nodePro.chain == null) return null;
        // 从不为空的链中找
        chain = nodePro.chain;
        Node<K,V> node = chain.remove(k);
        if (node == null) return null;
        // node不为空真的有删除了
        size--;
        return node;
    }
    public void clean() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }
    public V get(K k) {
        int index = hash(k);
        Node<K, V> nodePro = table[index];
        if ( nodePro == null ) return null;
        // 判断槽位的值是否相等(与槽位的值相同会直接返回)
        if ( Objects.equals( nodePro.k,k ) ) return nodePro.v;
        // 与槽位的值不相等，看chain是否为空，如果为空直接返回null
        Chain<K, V> chain = nodePro.chain;
        // 链为空，那肯定是找不到的，直接返回null
        if (chain == null) return null;
        // 链不为空，从链中找
        Node<K,V> chainFindNode = chain.get(k);
        return chainFindNode == null?null:chainFindNode.v;
    }
    public boolean containsValue(V value) {
        for (Node<K, V> nodePro : table) {
            if (nodePro == null ) continue;
            if (Objects.equals( nodePro.v,value )) return true; // 找到了
            // 没有找到
            Chain<K, V> chain = nodePro.chain;
            if(chain != null && chain.findValue(value)) return true;
        }
        return false;
    }
    public int size() {
        return size;
    }

    public void traversal(Function<Node<K, V>,Boolean> consume) {
        for (Node<K, V> node : table) {
            if (node == null ) continue;
            Boolean isBreak = consume.apply(node);
            if (isBreak) break;
            Chain<K, V> chain = node.chain;
            if (chain != null && ! chain.traversal(consume)) break;
        }
    }
    @Override
    public String toString() {
        String result = "";
        for (Node<K, V> nodePro : table) {
            if (nodePro == null) {
                result += "[NULL]\n";
                continue;
            }
            result += "[k="+nodePro.k+",v="+nodePro.v+"] ———— ";
            if (nodePro.chain != null) {
                result += nodePro.chain.toString();
            }
            result += "\n";
        }
        return result;
    }
}
