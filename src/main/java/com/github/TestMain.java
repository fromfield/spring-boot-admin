package com.github;

import java.util.Stack;

/**
 * @author fromfield
 * @create 2017-12-20 14:13
 **/

public class TestMain {
    //练习 在一篇英语文章中找出出现频率最高的单词


    public static void main(String[] args) {
//        String target = "9 + ( 3 - 1 ) * 3 + 10 / 2";
        String target = "9 + ( 3 - 1 ) * 3 + 10 / 2 + 10";
        sou(target);
    }

    /**
     * 后缀表达式算法
     * @param target
     */
    private static void sou(String target){
        String s = zhongToLast(target);
        Stack<Integer> t = new Stack();
        String[] is = s.split(" ");
        for (String i : is) {
            if ("+".equals(i)) {
                int a = t.pop();
                int b = t.pop();
                t.push(b + a);
            } else if ("-".equals(i)) {
                int a = t.pop();
                int b = t.pop();
                t.push(b - a);
            } else if ("*".equals(i)) {
                int a = t.pop();
                int b = t.pop();
                t.push(b * a);
            } else if ("/".equals(i)) {
                int a = t.pop();
                int b = t.pop();
                t.push(b / a);
            } else {
                t.push(Integer.valueOf(i));
            }
        }
        System.out.println(t.pop());
    }

    /**
     * 中缀表达式转后缀表达式
     * @param target
     * @return
     */
    private static String zhongToLast(String target) {
        Stack<String> t = new Stack();
        String[] is = target.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String i : is) {
            if ("+".equals(i) || "-".equals(i)) {
                if(!t.isEmpty()){
                    String n = t.pop();
                    if("*".equals(n) || "/".equals(n) || ")".equals(n)){
                        sb.append(" ").append(n).append(out(t, new StringBuilder()));
                    }else{
                        t.push(n);
                    }
                }
                t.push(i);
            } else if ("*".equals(i) || "/".equals(i)) {
                if(!t.isEmpty()){
                    String n = t.pop();
                    if("+".equals(n) || "-".equals(n)){
                        t.push(n);
                        t.push(i);
                    }else{
                        piPei(t, new StringBuilder()).deleteCharAt(0);
                    }
                }else{
                    t.push(i);
                }
            } else if ("(".equals(i)) {
                t.push(i);
            } else if (")".equals(i)) {
                sb.append(" ").append(piPei(t, new StringBuilder()).deleteCharAt(0));
            } else {
                sb.append(" ").append(i);
            }
        }
        String out = sb.append(out(t, new StringBuilder())).deleteCharAt(0).toString();
        System.out.println(out);
        return out;
    }

    private static StringBuilder out(Stack<String> s, StringBuilder out){
        if(s.isEmpty()){
            return out;
        }else{
            out.append(" ").append(s.pop());
            return out(s, out);
        }
    }

    private static StringBuilder piPei(Stack<String> s, StringBuilder out){
        if(s.isEmpty()){
            return out;
        }
        String n = s.pop();
        if("(".equals(n)){
            return out;
        }else{
            out.append(" ").append(n);
            return piPei(s, out);
        }
    }
}
