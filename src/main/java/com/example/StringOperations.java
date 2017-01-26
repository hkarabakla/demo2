package com.example;

import java.util.StringJoiner;

/**
 * Created by z003rv2s on 26.01.2017.
 */
public class StringOperations {

    public void testStringFeatures() {

        StringJoiner joiner = new StringJoiner(" , ");
        joiner.add("One").add("Two").add("Three");

        System.out.println(joiner);

        StringJoiner stringJoiner = new StringJoiner(" , ", "{", "}");
        stringJoiner.add("One").add("Two").add("Three");
        System.out.println(stringJoiner);
    }
}
