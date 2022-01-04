package com.oneapp.oneappandroidapp.model;


public class DashboardItem {
    private String title;
    private String data;
    private String remark;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DashboardItem(String title, String data, String remark) {
        this.title = title;
        this.data = data;
        this.remark = remark;
    }
}
