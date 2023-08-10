package org.zhuangjie.trie;

public class Test {
    public static void main(String[] args) {
        Trie<Integer> trie = new Trie<>();
        System.out.println(trie.remove("哈哈"));
        trie.add("大猪",256);
        trie.add("大大猪",256);
        trie.add("小猪",512);
        System.out.println(trie.remove("大大猪"));
        System.out.println(trie.remove("小猪"));
        System.out.println(trie.remove("大猪"));
        System.out.println(trie.size());


    }
}
