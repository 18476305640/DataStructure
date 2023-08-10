package org.zhuangjie.map;

import java.util.Iterator;
import java.util.function.Function;

/**
 * LinkedHashMap
 */
public class LinkedHashMap<K,V> extends HashMap<K,V>{
    LinkedNode<K,V> first;
    LinkedNode<K,V> last;

    private static final class LinkedNode<K,V> extends Node<K,V> {
        LinkedNode<K,V> prev;
        LinkedNode<K,V> next;
        protected LinkedNode(K k, V v) {
            super(k, v);
        }
    }
    @Override
    public V put(K k,V v) {
        Node<K,V> node = putNode(new LinkedNode<>(k, v));
        return node == null?null:node.v;
    }

    @Override
    public Node<K,V> putNode(Node<K, V> node) {
        if (node == null) return null;
        LinkedNode<K, V> oldNode = (LinkedNode<K, V>)super.putNode(node);
        LinkedNode<K, V> newNode = (LinkedNode<K, V>) node;
        if (oldNode == null) {
            // 添加
            if (first == null) {
                first = newNode;
                newNode.prev = null;
            }else {
                LinkedNode<K, V> lastNode = last;
                lastNode.next = newNode;
                newNode.prev = lastNode;
            }
            newNode.next = null;
            last = newNode;
        }else {
            // 替换
            LinkedNode<K, V> prevNode = oldNode.prev;
            LinkedNode<K, V> nextNode = oldNode.next;
            if (prevNode == null) {
                first = newNode;
                newNode.prev = null;
            }else {
                prevNode.next = prevNode;
                newNode.prev = prevNode;
            }
            if (nextNode == null) {
                // oldNode最后一个节点
                last = newNode;
                newNode.next = null;
            }else {
                nextNode.prev = newNode;
                newNode.next = nextNode;
            }
        }
        return oldNode;
    }

    @Override
    public Node<K, V> removeNode(K k) {
        Node<K, V> removeNode = super.removeNode(k);
        if (removeNode == null) return null;
        // 有删除-维护链表
        LinkedNode<K,V> removeLinkedNode = (LinkedNode<K,V>)removeNode;
        LinkedNode<K, V> prev = removeLinkedNode.prev;
        LinkedNode<K, V> next = removeLinkedNode.next;
        if (prev == null) {
            first = next;
        }else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        }else {
            next.prev = prev;
        }
        return removeNode;
    }

    @Override
    public void traversal(Function<Node<K, V>, Boolean> consume) {
        LinkedNode<K, V> currentNode = first;
        while (currentNode != null) {
            if (! consume.apply(currentNode)) break;
            currentNode = currentNode.next;
        }
    }

    @Override
    public void clean() {
        super.clean();
        first = null;
        last = null;
    }

}
