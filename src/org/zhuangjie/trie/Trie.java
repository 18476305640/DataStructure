package org.zhuangjie.trie;

import java.util.HashMap;

/**
 * 单词查找树
 *
 * @author zhuangjie
 * @date 2023/08/10
 */
public class Trie<V> {
    private int size;
    private Node<V> root;
    private static class Node<V>{
        Node<V> parent;
        HashMap<Character, Node<V>> children;
        Character character; // 为删除做准备
        V value;
        boolean word; // 是否为单词的结尾(是否位一个完整的单词)
        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }
    public V add(String key, V value){
        keyCheck(key);
        // 如果根节点为空，确保根节点存在
        if (root == null) root = new Node<>(null);
        int len = key.length();
        // 如果不存在后，就连续创建
        boolean isNotExist = false;
        Node<V> node = root;
        for (int i = 0; i < len; i++) {
            Node<V> fNode = null;
            Character c = key.charAt(i);
            if (! isNotExist) {
                fNode = findForNodeChildren(node,c);
                isNotExist = fNode == null;
            }
            if ( isNotExist ) {
                // 不存在，那就直接创建
                if (node.children == null) node.children = new HashMap<>();
                Node<V> nNode = new Node<>(node);
                nNode.character = c;
                node.children.put(c,nNode );
                node = nNode;
            }else {
                node = fNode;
            }
        }
        node.word = true;
        V oldValue = node.value;
        node.value = value;
        size++;
        return oldValue;
    }

    private Node<V> findForNodeChildren(Node<V> node, char c) {
        if (node == null || node.children == null) return null;
        return node.children.get(c);
    }

    private void keyCheck(String key){
        if(key==null || key.length()==0){
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public void clear(){
        size = 0;
        root = null;
    }
    public boolean contains(String key){
        Node<V> node = node(key);
        return node != null && node.word;
    }
    public boolean startsWith(String prefix){
        return node(prefix) != null;
    }
    public V remove(String key){
        Node<V> node = node(key);
        // 没有，所以不用删除
        if (node == null || ! node.word) return null;
        V removeValue = node.value;
        size--;
        // 去掉有值标志即可
        node.word = false;
        node.value = null;
        // 如果是叶子节点,不需要处理残留
        if (node.children != null && ! node.children.isEmpty()) return removeValue;
        // node是叶子节点，需要向上删除处理残留，直到遇到是word的节点 或者 不是叶子节点
        while (node != null && ! node.word && ( node.children == null || node.children.isEmpty())) {
            Node<V> parent = node.parent;
            if (parent == null) {
                // 要清理的是根节点，该节点肯定已经没有节点了
                root = null;
                break;
            }
            // 要清理的是普通节点，通过父节点删除
            parent.children.remove(node.character);
            node = parent;
        }
        return removeValue;
    }

    public V get(String key){
        Node<V> node = node(key);
        if (node == null || ! node.word) return null;
        return node.value;
    }
    /**
     * 根据传入字符串，找到最后一个节点
     * 例如输入 dog
     * 找到 g
     */
    private Node<V> node(String key){
        if (root == null || key == null) return null;
        Node<V> currentNode = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            Node<V> fNode = findForNodeChildren(currentNode, key.charAt(i));
            if (fNode == null) return null;
            currentNode = fNode;
        }
        return currentNode;
    }
}
