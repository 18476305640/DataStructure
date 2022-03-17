package com.zjazn.dsaa;

import com.zjazn.dsaa.tree.AVL;
import com.zjazn.dsaa.tree.BinarySearchTree;
import com.zjazn.dsaa.tree.BinaryTree;

public class test {

    public static void main(String[] args) {


//        AVL<Integer> bst = new AVL<>();
        AVL<Integer> bst = new AVL<>();
//        int[] arr = new int[]{5, 2, 10, 1, 3, 7,9,10,11,13,15,17,19,14};

        int[] arr = new int[]{5,3,6,2,4,8};
        for (int i = 0; i < arr.length; i++) {
            bst.add(arr[i]);
        }
//        int num = 10000;
//        for (int i = 0; i < num; i++) {
//            bst.add((int)(Math.random()*1000)+1);
//        }

//        int[] un_install_arr = new int[]{5, 2, 10, 1, 3,7,11};
//        for (int i = 0; i < un_install_arr.length; i++) {
//            System.out.println("即将删除："+un_install_arr[i]);
//            System.out.println(bst);
//            bst.remove(arr[i]);
//        }
        System.out.println(bst);
        bst.iteration(new BinaryTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer unit) {
                System.out.print(unit+" ");
                return false;
            }
        });



    }




}
