package com.zjazn.dsaa;

import com.zjazn.dsaa.tree.BinarySearchTree;

public class test {

    public static void main(String[] args) {


        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        int[] arr = new int[]{5, 2, 10, 1, 3, 7, 11};
//        for (int i = 0; i < 20; i++) {
//            bst.add((int)(Math.random()*100)+1);
//        }
        for (int i = 0; i < arr.length; i++) {
            bst.add(arr[i]);
        }
//        int[] un_install_arr = new int[]{5, 2, 10, 1, 3,7,11};
//        for (int i = 0; i < un_install_arr.length; i++) {
//            System.out.println("即将删除："+un_install_arr[i]);
//            System.out.println(bst);
//            bst.remove(arr[i]);
//        }
        System.out.println(bst);
        System.out.println("全部删除完了！");





    }




}
