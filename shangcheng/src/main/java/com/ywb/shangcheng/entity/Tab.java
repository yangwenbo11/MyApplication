package com.ywb.shangcheng.entity;

/**
 * Created by Administrator on 2017/1/12.
 */
public class Tab {
    private int title;
    private int icon;
    private Class fragment;

    public Tab(Class fragment, int icon,int title) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
