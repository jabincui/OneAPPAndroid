package com.oneapp.oneappandroidapp.netty_model;



import java.io.Serializable;


public class MessagePush implements Serializable {
    private String messagePushTitle;
    private String messagePushContent;

    public String getMessagePushTitle() {
        return messagePushTitle;
    }

    public void setMessagePushTitle(String messagePushTitle) {
        this.messagePushTitle = messagePushTitle;
    }

    public String getMessagePushContent() {
        return messagePushContent;
    }

    public void setMessagePushContent(String messagePushContent) {
        this.messagePushContent = messagePushContent;
    }

    public MessagePush() {
    }

    public MessagePush(String messagePushTitle, String messagePushContent) {
        this.messagePushTitle = messagePushTitle;
        this.messagePushContent = messagePushContent;
    }
}
