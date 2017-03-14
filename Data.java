package com.example.soyeon.login2;

import java.io.Serializable;

/**
 * Created by soyeon on 2017-03-12.
 */

public class Data implements Serializable {

    public String name;
    public int date;
    public int hum;

    public Data() {}

    public Data(String name, int date, int hum) {
        this.name = name;
        this.date = date;
        this.hum = hum;
    }
}

