package com.ywb.shangcheng.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder>{
    public SimpleAdapter(Context context,int layoutResId,List<T> datas){
        super(context,layoutResId,datas);

    }
}
