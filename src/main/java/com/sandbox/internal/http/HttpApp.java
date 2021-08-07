package com.sandbox.internal.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpApp {

    public static void get() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/arcjsonapi/ApiSampleData/master/api/users");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()))) {
                String inputLine;
                StringBuilder sb = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    sb.append(inputLine);
                }
                System.out.println(sb.toString().replace(" ", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int remAnagram(String str1, String str2)
    {
        // make hash array for both string
        // and calculate frequency of each
        // character
        int count1[] = new int[26];
        int count2[] = new int[26];

        // count frequency of each character
        // in first string
        for (int i = 0; i < str1.length() ; i++)
            count1[str1.charAt(i) -'a']++;

        // count frequency of each character
        // in second string
        for (int i = 0; i < str2.length() ; i++)
            count2[str2.charAt(i) -'a']++;

        // traverse count arrays to find
        // number of characters to be removed
        int result = 0;
        for (int i = 0; i < 26; i++)
            result += Math.abs(count1[i] -
                    count2[i]);
        return result;
    }

    // Driver program to run the case
    public static void main(String[] args)
    {
        String str1 = "abc", str2 = "bbc";
        System.out.println(remAnagram(str1, str2));
    }
}
