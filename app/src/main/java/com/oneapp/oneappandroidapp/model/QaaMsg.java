package com.oneapp.oneappandroidapp.model;

public class QaaMsg {
    public static final int RECEIVE = 0;
    public static final int SEND = 1;

    private int type;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QaaMsg(String content, int type) {
        this.type = type;
        this.content = content;
    }
}
