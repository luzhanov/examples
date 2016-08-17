package com.luzhanov;


import rx.Observable;

import java.util.Collections;

public class App {
    public static void main(String[] args) {

        System.out.println("Hello World!");

        Observable<Integer> observable = Observable.from(Collections.singletonList(42));


    }
}
