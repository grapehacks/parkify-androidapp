package com.grapeup.parkify.mvp.messages;


import android.support.v7.view.menu.MenuItemImpl;

public class MessageModelImpl implements MessageModel {

    private String title;
    private String body;
    private int type;

    public MessageModelImpl(String title, String body, int type) {
        this.title = title;
        this.body = body;
        this.type = type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public int getType() {
        return type;
    }
}
