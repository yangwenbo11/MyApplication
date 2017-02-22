package com.ywb.shangcheng.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ywb.shangcheng.R;
import com.ywb.shangcheng.adapter.BaseAdapter;
import com.ywb.shangcheng.adapter.BaseViewHolder;
import com.ywb.shangcheng.adapter.HWAdapter;
import com.ywb.shangcheng.adapter.SimpleAdapter;
import com.ywb.shangcheng.adapter.decoration.DividerItemDecoration;
import com.ywb.shangcheng.entity.PageResult;
import com.ywb.shangcheng.entity.Wares;
import com.ywb.shangcheng.http.Contants;
import com.ywb.shangcheng.http.OkHttpHelper;
import com.ywb.shangcheng.http.SpotsCallBack;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class HotFragment extends Fragment {
    private static final int STATE_MORMAL=0;//正常状态
    private static final int STATE_REFRESH=1;//刷新
    private static final int STATE_MORE=2;//加载更多
    private int state=STATE_MORMAL;//默认状态是正常状态

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
    private int currPage=1;
    private int totalPage=1;
    private int pageSize=10;
    private HWAdapter mAdapter;

    @BindView(R.id.hot_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.hot_mrl)
    MaterialRefreshLayout mRefreshLayout;
    private List<Wares> datas;

    public HotFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container,false);
        ButterKnife.bind(this,view);
        initRefreshLayout();
        getData();
        return view;

    }
    private void getData(){
        String url= Contants.API.WARES_HOT+"?curPage="+currPage+"&pageSize="+pageSize;
        okHttpHelper.get(url, new SpotsCallBack<PageResult<Wares>>(getContext()) {

            @Override
            public void onSuccess(Response response, PageResult<Wares> waresPageResult) {
                datas=waresPageResult.getList();
                currPage=waresPageResult.getCurrentPage();
                totalPage=waresPageResult.getTotalPage();
                showData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    private void showData(){
        switch (state){
            case STATE_MORMAL:
                mAdapter=new HWAdapter(getContext(),datas);
                mAdapter.setmOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getContext(),"点击了"+position,
                                Toast.LENGTH_LONG).show();
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(
                        getContext(),DividerItemDecoration.VERTICAL_LIST));
               mRecyclerView.setAdapter(new SimpleAdapter<Wares>(getContext(),R.layout.item_hot_wares,datas) {
                   @Override
                   protected void convert(BaseViewHolder holder, Wares item) {
                       SimpleDraweeView draweeView= (SimpleDraweeView) holder.getView(R.id.hot_wares_img_sdv);
                       draweeView.setImageURI(Uri.parse(Contants.API.BASE_URL+item.getImgUrl()));
                        holder.getTextView(R.id.hot_wares_title_tv).setText(item.getName());
                        holder.getTextView(R.id.hot_wares_price_tv).setText(item.getPrice()+"");

                   }

               });
                break;
            case STATE_REFRESH:
                mAdapter.clearData();
                mAdapter.addData(datas);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mAdapter.addData(mAdapter.getItemCount(),datas);
                mRecyclerView.scrollToPosition(mAdapter.getItemCount());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }


    }
    private void initRefreshLayout(){
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreeshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if(currPage<=totalPage){
                    loadMoreData();
                }else{
                    Toast.makeText(getContext(),"已经没有更多数据了",
                            Toast.LENGTH_LONG).show();
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }
    private void refreeshData(){
        currPage=1;
        state=STATE_REFRESH;
        getData();
    }
    private void loadMoreData(){
        currPage+=1;
        state=STATE_MORE;
        getData();
    }
}
