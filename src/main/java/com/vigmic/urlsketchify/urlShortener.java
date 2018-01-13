package com.vigmic.urlsketchify;

import org.springframework.stereotype.Component;

@Component
public class urlShortener {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    public String encode(Long id){
        StringBuilder stringBuilder = new StringBuilder();
        while(id > 0){
            stringBuilder.append(ALPHABET.charAt(Math.toIntExact((id % BASE))));

            id /= BASE;
        }
        return stringBuilder.reverse().toString();
    }

    public Long decode(String bijectedUrl){
        Long num = Long.valueOf(0);
        for ( int i = 0; i < bijectedUrl.length(); i++ )
            num = num * BASE + ALPHABET.indexOf(bijectedUrl.charAt(i));
        return num;
    }
}
