package com.ywb.shangcheng.utils;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.ywb.shangcheng.entity.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/24.
 */

public class CartProvider {
    private static final String CART_JSON="cart_json";
    private SparseArray<ShoppingCart> datas=null;
    private Context mContext;
    public CartProvider(Context context){
        datas=new SparseArray<>(10);
        this.mContext=context;
        listToSparse();
    }
    //放
    public void put(ShoppingCart cart){
        ShoppingCart temp=datas.get(cart.getId().intValue());
        if(temp!=null){
            temp.setCount(temp.getCount()+1);
        }else{
            temp=cart;
            temp.setCount(1);
        }
        datas.put(cart.getId().intValue(),temp);
        commit();
    }
    public void update(ShoppingCart cart){
        datas.put(cart.getId().intValue(),cart);
        commit();
    }
    public void delete(ShoppingCart cart){
        datas.delete(cart.getId().intValue());
        commit();
    }
    //向本地存数据
    public void commit(){
       List<ShoppingCart> carts=sparseToList();
        PreferenceUtils.putString(mContext,CART_JSON,GsonUtils.toJson(carts));

    }
    //转换
    private List<ShoppingCart> sparseToList(){
        int size=datas.size();
        List<ShoppingCart> list=new ArrayList<>(size);
        for(int i=0;i<size;i++){
            list.add(datas.valueAt(i));
        }
        return list;
    }

    private void listToSparse(){
        List<ShoppingCart> carts=getDataFromLocal();
        if(carts!=null&&carts.size()>0){
            for(ShoppingCart cart:carts){
                datas.put(cart.getId().intValue(),cart);
            }
        }
    }

    public List<ShoppingCart> getAll(){
        return getDataFromLocal();
    }
    public List<ShoppingCart> getDataFromLocal(){
        String json=PreferenceUtils.getString(mContext,CART_JSON);
        List<ShoppingCart> carts=null;
        if(json!=null){
            carts=GsonUtils.fromJson(json,new TypeToken<List<ShoppingCart>>(){}.getType());

        }
        return carts;
    }
}
