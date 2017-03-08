package com.ywb.shangcheng.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.ywb.shangcheng.R;
import com.ywb.shangcheng.adapter.HWAdapter;
import com.ywb.shangcheng.adapter.decoration.DividerItemDecoration;
import com.ywb.shangcheng.entity.PageResult;
import com.ywb.shangcheng.entity.Wares;
import com.ywb.shangcheng.http.Contants;
import com.ywb.shangcheng.utils.PageUtils;
import com.ywb.shangcheng.wight.ShopToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WareListActivity extends AppCompatActivity
        implements PageUtils.OnPageListener,
        TabLayout.OnTabSelectedListener,
        View.OnClickListener {
    private static final int TAG_DEFAULT = 0;
    private static final int TAG_SALE = 1;
    private static final int TAG_PRICE = 2;

    private static final int ACTION_LIST = 1;
    private static final int ACTION_GRID = 2;

    @BindView(R.id.ware_list_tl)
    TabLayout mTabLayout;
    @BindView(R.id.ware_list_summary_tv)
    TextView mSummaryTv;
    @BindView(R.id.ware_list_rv)
    RecyclerView mWaresRecylerView;
    @BindView(R.id.ware_list_mrl)
    MaterialRefreshLayout mRefreshLayout;
    @BindView(R.id.toolbar)
    ShopToolbar mToolBar;

    private int orderBy = 0;
    private long campaignId = 0;
    private HWAdapter mWaresAdapter;
    private PageUtils pageUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_list);
        ButterKnife.bind(this);

        campaignId = getIntent().getLongExtra(
                Contants.COMPAIGN_ID, 0);

        initToolBar();
        initTab();
        getData();
    }

    private void initToolBar() {
        mToolBar.setLeftButtonClickLinstener(this);
        mToolBar.setRightButtonIcon(R.drawable.icon_grid_32);
        mToolBar.setRightButtonClickLinstener(this);
        mToolBar.getRightButton().setTag(ACTION_LIST);
    }

    private void initTab() {
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setText("默认");
        tab.setTag(TAG_DEFAULT);
        mTabLayout.addTab(tab);

        tab = mTabLayout.newTab();
        tab.setText("价格");
        tab.setTag(TAG_PRICE);
        mTabLayout.addTab(tab);

        tab = mTabLayout.newTab();
        tab.setText("销量");
        tab.setTag(TAG_SALE);
        mTabLayout.addTab(tab);

        mTabLayout.setOnTabSelectedListener(this);
    }

    private void getData() {
        pageUtils = PageUtils.newBuilder()
                .setUrl(Contants.API.CAMPAIGN_LIST)
                .putParam("campaignId", campaignId)
                .putParam("orderBy", orderBy)
                .setRefreshLayout(mRefreshLayout)
                .setLoadMore(true)
                .setOnPageListener(this)
                .build(this, new TypeToken<PageResult<Wares>>() {
                }.getType());
        pageUtils.request();

    }

    @Override
    public void load(List datas, int totalPage, int totalCount) {
        mSummaryTv.setText("共有" + totalCount + "件商品");
        if (mWaresAdapter == null) {
            mWaresAdapter = new HWAdapter(this, datas);
            mWaresRecylerView.setAdapter(mWaresAdapter);
            mWaresRecylerView.setLayoutManager(new LinearLayoutManager(this));
            mWaresRecylerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));
            mWaresRecylerView.setItemAnimator(new DefaultItemAnimator());
        } else {
            mWaresAdapter.refreshData(datas);
        }
    }

    @Override
    public void refresh(List datas, int totalPage, int totalCount) {
        mWaresAdapter.refreshData(datas);
        mWaresRecylerView.scrollToPosition(0);
    }

    @Override
    public void loadMore(List datas, int totalPage, int totalCount) {
        mWaresAdapter.loadMoreData(datas);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        orderBy = (int) tab.getTag();
        pageUtils.putParam("orderBy", orderBy);
        pageUtils.request();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_leftButton) {
            WareListActivity.this.finish();
        } else {
            int action = (int) v.getTag();
            switch (action) {
                case ACTION_LIST:
                    mToolBar.setRightButtonIcon(R.drawable.icon_list_32);
                    mToolBar.getRightButton().setTag(ACTION_GRID);
                    mWaresRecylerView.setLayoutManager(new GridLayoutManager(this, 2));
                    mWaresAdapter.resetLayout(R.layout.item_grid_wares);
                    break;
                case ACTION_GRID:
                    mToolBar.setRightButtonIcon(R.drawable.icon_grid_32);
                    mToolBar.getRightButton().setTag(ACTION_LIST);
                    mWaresRecylerView.setLayoutManager(new LinearLayoutManager(this));
                    mWaresAdapter.resetLayout(R.layout.item_hot_wares);
                    break;
            }

        }
    }
}
