package com.ywb.shangcheng.entity;

/**
 * Created by Administrator on 2017/2/20 0020.
 */
public class Wares extends BaseBean {
    private String imgUrl;
    private String name;
    private Float price;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}