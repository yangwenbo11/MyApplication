package com.ywb.shangcheng.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ywb.shangcheng.R;
import com.ywb.shangcheng.entity.Wares;
import com.ywb.shangcheng.http.Contants;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class HWAdapter extends SimpleAdapter<Wares> {
    @Override
    protected void convert(BaseViewHolder holder, Wares item) {
        SimpleDraweeView draweeView= (SimpleDraweeView) holder.getView(R.id.hot_wares_img_sdv);
        draweeView.setImageURI(Uri.parse(Contants.API.BASE_URL+item.getImgUrl()));
        holder.getTextView(R.id.hot_wares_title_tv).setText(item.getName());
        holder.getTextView(R.id.hot_wares_price_tv).setText(item.getPrice()+"");
    }

    public HWAdapter(Context context, List<Wares> datas) {
        super(context, R.layout.item_hot_wares, datas);
    }
}
