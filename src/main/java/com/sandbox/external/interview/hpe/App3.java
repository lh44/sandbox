package com.sandbox.external.interview.hpe;

public class App3 {

    public static void main(String[] args) {
        System.out.println(solution("CBACD"));
    }

    public static String solution(String S) {
        StringBuilder sb = new StringBuilder(S);
        while (true) {
            boolean goAgain = false;
            String[] arr = new String[]{"AB", "BA", "CD", "DC"};
            for (String e : arr) {
                if (sb.indexOf(e) != -1) {
                    int index = sb.indexOf(e);
                    sb.delete(index, index+2);
                    goAgain = true;
                }
            }
            if (!goAgain) return sb.toString();
        }
    }
}
