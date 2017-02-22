package com.ywb.shangcheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.ywb.shangcheng.R;
import com.ywb.shangcheng.adapter.HomeCategoryAdapter;
import com.ywb.shangcheng.adapter.decoration.CardViewItemDecoration;
import com.ywb.shangcheng.entity.Banner;
import com.ywb.shangcheng.entity.Campaign;
import com.ywb.shangcheng.entity.HomeCampaign;
import com.ywb.shangcheng.http.BaseCallBack;
import com.ywb.shangcheng.http.Contants;
import com.ywb.shangcheng.http.OkHttpHelper;
import com.ywb.shangcheng.http.SpotsCallBack;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class HomeFragment extends Fragment {
    private SliderLayout mSliderLayout;
    private PagerIndicator mIndicator;
    private RecyclerView mRecyclerView;
    private HomeCategoryAdapter mAdapter;
    private Gson mGson = new Gson();
    private List<Banner> mBanners;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        mIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        requestImages();
        initRecyclerView(view);
        return view;
    }

    //okhttp联网
    private void requestImages() {
        String url = Contants.API.BANNER + "?type=1";

        okHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanners = banners;
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
        /* String url = "http://101.200.167.75:8080/phoenixshop/banner/query?type=1";
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("type", "1")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


//        Request request=new Request.Builder()
//                .url(url)
//                .build();


        client.newCall(request).enqueue(new Callback() {
            //请求网络时出现不可恢复的错误时调用该方法
            @Override
            public void onFailure(Call call, IOException e) {

            }

            //请求网络成功时调用该方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //判断http状态码
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.e("TAG", "json=" + json);
                    Type type = new TypeToken<List<Banner>>() {
                    }.getType();
                    mBanners = mGson.fromJson(json, type);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initSlider();
                        }
                    });

                }
            }
        });*/
    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        okHttpHelper.get(Contants.API.CAMPAIGN_HOME,
                new BaseCallBack<List<HomeCampaign>>() {
                    @Override
                    public void onRequestBefore(Request request) {

                    }

                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                        initData(homeCampaigns);
                    }


                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }

                    @Override
                    public void onResponse(Response response) {

                    }
                });

// List<HomeCategory> datas = new ArrayList<>();
//        HomeCategory category = new HomeCategory(
//                "热门活动", R.drawable.img_big_1,
//                R.drawable.img_1_small1, R.drawable.img_1_small2);
//        datas.add(category);
//        category = new HomeCategory("品牌街", R.drawable.img_big_2,
//                R.drawable.img_2_small1, R.drawable.img_2_small2);
//        datas.add(category);
//        category = new HomeCategory("金融街 包赚翻", R.drawable.img_big_3,
//                R.drawable.img_3_small1, R.drawable.img_3_small2);
//        datas.add(category);
//        category = new HomeCategory("超值购", R.drawable.img_big_0,
//                R.drawable.img_0_small1, R.drawable.img_0_small2);
//        datas.add(category);
//
//        mAdapter = new HomeCategoryAdapter(datas);
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(
//                this.getActivity()));
//        //添加装饰器
    }

    private void initData(List<HomeCampaign> homeCampaigns) {
        mAdapter = new HomeCategoryAdapter(homeCampaigns,getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this.getActivity()));
        //添加装饰器
        mRecyclerView.addItemDecoration(new CardViewItemDecoration());
        mAdapter.setOnCampaignClickListener(new HomeCategoryAdapter.OnCampaignClickListener() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Toast.makeText(getContext(),"title="+campaign.getTitle(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //初始化SliderLayout
    public void initSlider() {
        if (mBanners != null) {
            for (Banner banner : mBanners) {
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView
                        .description(banner.getDescription())
                        .image(banner.getImgUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit);//设置图片填充
                mSliderLayout.addSlider(textSliderView);
            }
        }

        //设置默认指示器(如图片底部的小圆点)
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //设置自定义的指示器
        mSliderLayout.setCustomIndicator(mIndicator);
        //设置自定义动画
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        //设置转场效果
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        //设置转场时间
        mSliderLayout.setDuration(3000);

        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            //滚动时调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("TAG", "onPageScrolled------->1");
            }

            //新页卡被选中时调用
            @Override
            public void onPageSelected(int position) {
                Log.e("TAG", "onPageSelected------->2");
            }

            //滚动状态改变时调用
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("TAG", "onPageScrollStateChanged------->3");
            }
        });
    }

    @Override
    public void onStop() {
        //与fragment的生命周期进行绑定 fragment死mSliderLayout也死
        mSliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onStart() {
        //恢复
        mSliderLayout.startAutoCycle();
        super.onStart();
    }

    //判断当前页面是否隐藏
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            mSliderLayout.stopAutoCycle();
        } else {
            mSliderLayout.startAutoCycle();
        }
        super.onHiddenChanged(hidden);
    }
}
