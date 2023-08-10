package org.zhuangjie.map;


public class Test {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new LinkedHashMap<>();

        for (int i = 0; i < 30; i++) {
            map.put("猪猪"+i,i);
        }

        System.out.println(map.size());
        map.traversal(node-> {
            System.out.print(node+"->");
            return true;
        });
        System.out.println("");
        System.out.println(map.size());





    }
}
