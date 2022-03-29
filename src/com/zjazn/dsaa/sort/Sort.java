package com.zjazn.dsaa.sort;

interface Sort {
    int[]  start(int arr[]);
}

//插入排序
class InsertSort implements Sort {

    @Override
    public  int[] start(int arr[]) {
        /**
         *插入排序
         */
//        for (int i = 2; i < arr.length; i++) {
//            if (arr[i] >= arr[i - 1]) {
//                continue;
//            }
//            int tmp = arr[i];
//            for (int j = i - 1; j >= 0; j--) {
//                arr[j + 1] = arr[j];
//                if (j > 0 && (tmp <= arr[j + 1] && tmp >= arr[j - 1])) {
//                    arr[j] = tmp;
//                    break;
//                } else if (j == 0) {
//                    arr[j] = tmp;
//                }
//
//            }
//        }
        int i, j, insertNote;// 要插入的数据
        for (i = 1; i < arr.length; i++) {// 从数组的第二个元素开始循环将数组中的元素插入
            insertNote = arr[i];// 设置数组中的第2个元素为第一次循环要插入的数据
            j = i - 1;
            while (j >= 0 && insertNote < arr[j]) {
                arr[j + 1] = arr[j];// 如果要插入的元素小于第j个元素,就将第j个元素向后移动
                j--;
            }
            arr[j + 1] = insertNote;// 直到要插入的元素不小于第j个元素,将insertNote插入到数组中
        }

        return arr;
    }
}

//希尔排序
class HillSort implements Sort {

    @Override
    public int[] start(int[] a) {
        for (int g = a.length / 2; g > 0; g--) {
            for (int i = g, j = 0; i < a.length; i++, j++) {
                if (a[j] > a[i]) {
                    int tmp = a[j];
                    a[j] = a[i];
                    a[i] = tmp;
                }
            }
        }
        return a;
    }
}

//冒泡排序
class BubblingSort implements Sort {

    @Override
    public int[] start(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }

        return arr;
    }
}

//简单排序
class SimpleSort implements Sort {

    @Override
    public int[] start(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    private static void quickSort(int[] arr, int low, int high) {

        if (low >= high) {
            return;
        }
        int central = arr[low];
        int left = low;
        int right = high;
        int pos = 1;
        while (left < right) {
            if (pos == -1) {
                //到左边操作
                if (arr[left] > central) {
                    arr[right] = arr[left];
                    pos = 1;
                    right--;
                } else {
                    while (arr[left] <= central && left < right) {
                        left++;
                    }
                }

            } else {
                //到右边操作
                if (arr[right] < central) {
                    arr[left] = arr[right];
                    pos = -1;
                    left++;
                } else {
                    while (arr[right] >= central && left < right) {
                        right--;
                    }
                }
            }
        }
        arr[left] = central;
        //将pivot中心轴（也就是一个元素），放在left=right相等时的这个索引上
        //将左右子序列提取出来，单独作为数组，重复上面的操作
        quickSort(arr, low, left - 1);
        quickSort(arr, right + 1, high);


    }

}