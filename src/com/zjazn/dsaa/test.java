package com.zjazn.dsaa;

import com.zjazn.dsaa.tree.AVL;
import com.zjazn.dsaa.tree.BinarySearchTree;

public class test {

    public static void main(String[] args) {


        AVL<Integer> bst = new AVL<>();
//        int[] arr = new int[]{5, 2, 10, 1, 3, 7,9,10,11,13,15,17,19,14};

//        int[] arr = new int[]{5, 2, 11, 1, 3, 10, 12,7,13};
//        for (int i = 0; i < 20; i++) {
//            bst.add((int)(Math.random()*100)+1);
//        }
        int num = 1000000000;
        for (int i = 0; i < num; i++) {
            bst.add(i);
        }
//        int[] un_install_arr = new int[]{5, 2, 10, 1, 3,7,11};
//        for (int i = 0; i < un_install_arr.length; i++) {
//            System.out.println("即将删除："+un_install_arr[i]);
//            System.out.println(bst);
//            bst.remove(arr[i]);
//        }





    }




}
