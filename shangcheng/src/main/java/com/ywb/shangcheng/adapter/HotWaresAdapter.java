package com.ywb.shangcheng.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ywb.shangcheng.entity.Wares;
import com.ywb.shangcheng.R;
import com.ywb.shangcheng.http.Contants;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15 0015.
 */
public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Wares> mDatas;


    public HotWaresAdapter(List<Wares> datas) {
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_hot_wares, null);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wares wares = mDatas.get(position);
//        holder.draweeView.setImageURI(Uri.parse(Contants.API.BASE_URL+wares.getImgUrl()));
        holder.draweeView.setImageURI(Uri.parse(Contants.API.BASE_URL+wares.getImgUrl()));
        holder.textTitle.setText(wares.getName());
        holder.textPrice.setText("¥"+wares.getPrice());
    }

    public Wares getData(int position){
        return mDatas.get(position);
    }
    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.size();
        }
        return 0;
    }
    public void addData(List<Wares> datas){
        addData(0,datas);
    }
    public void addData(int position,List<Wares> datas){
        if(datas!=null&&datas.size()>0){
            mDatas.addAll(datas);
            notifyItemRangeChanged(position,mDatas.size());
        }
    }

    //清空数据源
    public void clearData(){
        mDatas.clear();
        notifyItemRangeRemoved(0,mDatas.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView draweeView;
        TextView textTitle;
        TextView textPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            draweeView= (SimpleDraweeView) itemView.findViewById(R.id.hot_wares_img_sdv);
            textTitle = (TextView) itemView.findViewById(R.id.hot_wares_title_tv);
            textPrice = (TextView) itemView.findViewById(R.id.hot_wares_price_tv);
        }

    }
}
