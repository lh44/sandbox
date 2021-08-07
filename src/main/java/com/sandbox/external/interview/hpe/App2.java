package com.sandbox.external.interview.hpe;

public class App2 {

    public static void main(String[] args) {
        App2 app2 = new App2();
        System.out.println(app2.solution("BAABAB"));
    }

    public int solution(String S) {
        char[] chars = S.toCharArray();
        int l = 0;
        int r = chars.length-1;
        int count = 0;
        int countToRemoveEitherOfCharacters = Math.max(S.lastIndexOf("A") - S.indexOf("A"), S.lastIndexOf("B") - S.indexOf("B"));
        if (validate(S)) {
            return count;
        }

        while (l < r) {
            if (chars[l] != 'A') {
                count++;
            }
            if (chars[r] != 'B') {
                count++;
            }
            l++;
            r--;
        }
        return Math.min(count, countToRemoveEitherOfCharacters);
    }

    private boolean validate(String s) {
        if (!s.contains("A") || !s.contains("B")) {
            return true;
        }
        boolean isChanged = false;
        for (char c : s.toCharArray()) {
            if (c == 'B') {
                isChanged = true;
            } else if (c == 'A') {
                if (isChanged) return false;
            }
        }
        return isChanged;
    }
}
