package com.ywb.shangcheng.adapter;

import android.content.Context;

import com.ywb.shangcheng.R;
import com.ywb.shangcheng.entity.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class CategoryAdapter extends SimpleAdapter<Category> {


    public CategoryAdapter(Context context, List<Category> datas) {
        super(context, R.layout.item_category_text, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, Category item) {
        holder.getTextView(R.id.item_category_tv)
                .setText(item.getName());
    }
}
