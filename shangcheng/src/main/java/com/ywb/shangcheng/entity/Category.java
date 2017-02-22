package com.ywb.shangcheng.entity;

/**
 * Created by Administrator on 2017/2/15.
 */
public class Category extends BaseBean {
    private String name;

    public Category() {
        this.name = name;
    }
    public Category(String name) {
        this.name = name;
    }
    public Category(long id,String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
