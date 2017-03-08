package com.ywb.shangcheng.adapter;

import android.content.Context;
import android.net.Uri;

import com.ywb.shangcheng.R;
import com.ywb.shangcheng.entity.Wares;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23 0023.
 */

public class WaresAdapter  extends SimpleAdapter<Wares>{
    public WaresAdapter(Context context,List<Wares> datas) {
        super(context, R.layout.item_category_wares, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, Wares item) {
//        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.category_wares_img_sdv);
//        draweeView.setImageURI(Uri.parse(Contants.API.BASE_URL+item.getImgUrl()));
//        holder.getTextView(R.id.catogory_wares_title_tv).setText(item.getName());
//        holder.getTextView(R.id.category_wares_price_tv).setText(item.getPrice() + "");
        holder.getImageView(R.id.category_wares_img_sdv).setImageURI(Uri.parse(item.getImgUrl()));
        holder.getTextView(R.id.catogory_wares_title_tv).setText(item.getName());
        holder.getTextView(R.id.category_wares_price_tv).setText(item.getPrice()+"");
    }
}
