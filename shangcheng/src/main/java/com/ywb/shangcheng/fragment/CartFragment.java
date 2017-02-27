package com.ywb.shangcheng.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ywb.shangcheng.R;
import com.ywb.shangcheng.activity.MainActivity;
import com.ywb.shangcheng.adapter.CartAdapter;
import com.ywb.shangcheng.adapter.decoration.DividerItemDecoration;
import com.ywb.shangcheng.entity.ShoppingCart;
import com.ywb.shangcheng.utils.CartProvider;
import com.ywb.shangcheng.wight.ShopToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class CartFragment extends Fragment implements View.OnClickListener {
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_COMPLETE = 2;

    @BindView(R.id.cart_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.cart_checkbox_all)
    CheckBox mCheckBox;
    @BindView(R.id.cart_txt_total)
    TextView mTextTotal;
    @BindView(R.id.cart_btn_order)
    Button mBtnOrder;
    @BindView(R.id.cart_btn_del)
    Button mBtnDel;

    private CartAdapter mAdapter;
    private CartProvider cartProvider;
    private ShopToolbar mToolBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        cartProvider = new CartProvider(getContext());
        showData();
        return view;
    }

    private void showData() {
        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter = new CartAdapter(getContext(), carts, mCheckBox, mTextTotal);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));

    }

    public void refData() {
        mAdapter.clearData();
        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter.addData(carts);
        mAdapter.showTotalPrice();
        mCheckBox.setChecked(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;
            mToolBar = (ShopToolbar) activity.findViewById(R.id.toolbar);
            changeToolBar();
        }
    }

    public void changeToolBar() {
        mToolBar.hideSearchView();
        mToolBar.showTitleView();
        mToolBar.setTitle(R.string.cart);
        mToolBar.setRightButtonText("编辑");
        mToolBar.setRightButtonClickLinstener(this);
        mToolBar.getmRightButton().setTag(ACTION_EDIT);
    }

    @Override
    public void onClick(View v) {
        int action = (int) v.getTag();
        if (ACTION_EDIT == action) {
            showDelControl();
        } else if (ACTION_COMPLETE == action) {
            hideDelControl();
        }
    }

    private void hideDelControl() {
        mToolBar.setRightButtonText("编辑");
        mTextTotal.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);
        mBtnDel.setVisibility(View.GONE);
        mToolBar.getmRightButton().setTag(ACTION_EDIT);
        mAdapter.checkAllOrNone(true);
        mAdapter.showTotalPrice();
        mCheckBox.setChecked(true);
    }

    private void showDelControl() {
        mToolBar.setRightButtonText("完成");
        mTextTotal.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mToolBar.getmRightButton().setTag(ACTION_COMPLETE);
        mAdapter.checkAllOrNone(false);
        mCheckBox.setChecked(false);
    }

    @OnClick(R.id.cart_btn_del)
    public void cartBtnClick(View view) {
        mAdapter.delCart();
    }
}
