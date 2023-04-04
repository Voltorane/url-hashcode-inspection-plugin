package com.github.voltorane.urlhashcodeinspectionplugin;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlHashcodeTest {
    public static void main(String[] args) {
        try {
            URL url = new URL("www.google.com");
            System.out.println(<caret>url.hashCode());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
