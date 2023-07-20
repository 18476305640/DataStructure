package org.zhuangjie.tree;

import org.zhuangjie.tree.javabean.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        BST<Person> bst = new AVLTree<>();
        List<Integer> ages = Arrays.asList(1,2,3);
//        for (Integer age : ages) {
//
//        }
        for (int i = 0; i < 1000; i++) {
            bst.add(new Person(i+"号", new Random().nextInt(10000)));
        }
        System.out.println(bst);
    }
}
