package com.oneapp.oneappandroidapp.netty_model;



import java.io.Serializable;


public class MyString implements Serializable {
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public MyString() {
    }

    public MyString(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "MyString{" +
                "str='" + str + '\'' +
                '}';
    }

}
