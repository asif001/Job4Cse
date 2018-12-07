package com.example.myjava;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Main {

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    public static void main(String[] args) throws URISyntaxException,IOException {
        String value = "\\frac{\\sin{\\pi/4} + \\sqrt{2}}{1.625^2}";
        String encodedValue = encodeValue(value);
        System.out.println(encodedValue);

        load(encodedValue);
    }

    public static void load(String encoded) throws URISyntaxException,IOException{

        String alphaUrl = "http://www.wolframalpha.com/input/?i=" + encoded;


        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(alphaUrl));
        }

    }

}
