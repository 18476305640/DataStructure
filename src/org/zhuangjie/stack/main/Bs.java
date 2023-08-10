package org.zhuangjie.stack.main;

import java.util.HashMap;
import java.util.Stack;

//{([])} 返回true   {([))} 返回false
public class Bs {
    private Stack<Character> m = new Stack<>();
    private static HashMap<Character,Character> hashMap = new HashMap<>();
    static {
        hashMap.put('{','}');
        hashMap.put('(',')');
        hashMap.put('[',']');
    }
    public boolean check(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(hashMap.containsKey(c)) {
                m.push(c);
            }else {
                if(hashMap.get(m.pop()) != c) return false;
            }
        }
        return m.isEmpty();
    }
}
