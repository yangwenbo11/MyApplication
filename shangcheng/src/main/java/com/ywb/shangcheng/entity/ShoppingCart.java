package com.ywb.shangcheng.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/24.
 */

public class ShoppingCart extends Wares implements Serializable{
    private int count;
    private boolean isChecked=true;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
