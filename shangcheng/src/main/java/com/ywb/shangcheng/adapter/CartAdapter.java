package com.ywb.shangcheng.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ywb.shangcheng.R;
import com.ywb.shangcheng.entity.ShoppingCart;
import com.ywb.shangcheng.utils.CartProvider;
import com.ywb.shangcheng.wight.NumberAddSubView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class CartAdapter extends SimpleAdapter<ShoppingCart>
        implements BaseAdapter.OnItemClickListener {
    private CheckBox checkBox;
    private TextView textView;
    private CartProvider cartProvider;

    public CartAdapter(Context context, List<ShoppingCart> datas, final CheckBox checkBox,
                       TextView textView) {
        super(context, R.layout.item_cart, datas);
        this.checkBox = checkBox;
        this.textView = textView;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllOrNone(checkBox.isChecked());
                showTotalPrice();
            }
        });
        cartProvider = new CartProvider(context);
        setmOnItemClickListener(this);
        showTotalPrice();
    }

    public void checkAllOrNone(boolean isChecked) {
        if (!isNull()) {
            return;
        }
        int i = 0;
        for (ShoppingCart cart : mDatas) {
            cart.setChecked(isChecked);
            notifyItemChanged(i);
            i++;
        }
    }


    @Override
    protected void convert(BaseViewHolder holder, final ShoppingCart item) {
        holder.getTextView(R.id.item_cart_title_tv).setText(item.getName());
        holder.getTextView(R.id.item_cart_price_tv).setText("¥" + item.getPrice());
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.item_cart_dv);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));

        CheckBox checkBox = (CheckBox) holder.getView(R.id.item_cart_cb);
        checkBox.setChecked(item.isChecked());

        NumberAddSubView numberAddSubView = (NumberAddSubView) holder.getView(R.id.item_cart_num_nasv);
        numberAddSubView.setValue(item.getCount());
        //最大值是库存
        numberAddSubView.setMaxValue(item.getStock());
        numberAddSubView.setmOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {
                addOrSubUpdate(value, item);
            }

            @Override
            public void onButtonSubClick(View view, int value) {
                addOrSubUpdate(value, item);
            }
        });
    }

    private void addOrSubUpdate(int value, ShoppingCart item) {
        item.setCount(value);
        cartProvider.update(item);
        showTotalPrice();
    }

    private float getTotalPrice() {
        float sum = 0;
        if (!isNull()) {
            return sum;
        }
        for (ShoppingCart cart : mDatas) {
            if (cart.isChecked()) {
                sum += cart.getCount() * cart.getPrice();
            }
        }
        return sum;
    }

    public void showTotalPrice() {
        float total = getTotalPrice();
//        textView.setText(Html.fromHtml("合计<font color='#eb4f38'>"+
//        "¥"+total+"</font>"), TextView.BufferType.SPANNABLE);
        SpannableString sp = new SpannableString("合计 ¥" + total);
        sp.setSpan(new ForegroundColorSpan(0xFFeb4f38), 3, sp.length(),
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(sp);
    }

    private boolean isNull() {
        return (mDatas != null && mDatas.size() > 0);
    }

    @Override
    public void onItemClick(View view, int position) {
        ShoppingCart cart = getItem(position);
        cart.setChecked(!cart.isChecked());
        notifyItemChanged(position);
        checkListener();
        showTotalPrice();
    }

    private void checkListener() {
        int count = 0;
        int checkNum = 0;
        if (mDatas != null) {
            count = mDatas.size();
            for (ShoppingCart cart : mDatas) {
                if (!cart.isChecked()) {
                    checkBox.setChecked(false);
                    break;
                } else {
                    checkNum += 1;
                }
            }
            if (count == checkNum) {
                checkBox.setChecked(true);
            }
        }
    }

    public void delCart() {
        if (!isNull()) {
            return;
        }
//        for (ShoppingCart cart : mDatas) {
//            if (cart.isChecked()) {
//                int position = mDatas.indexOf(cart);
//                cartProvider.delete(cart);
//                mDatas.remove(cart);
//                notifyItemRemoved(position);
//            }
//        }
        for (Iterator iterator = mDatas.iterator(); iterator.hasNext(); ) {
            ShoppingCart cart = (ShoppingCart) iterator.next();
            if (cart.isChecked()) {
                int position = mDatas.indexOf(cart);
                cartProvider.delete(cart);
                iterator.remove();
                notifyItemRemoved(position);
            }
        }
    }
}
