package com.ywb.shangcheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.ywb.shangcheng.R;
import com.ywb.shangcheng.adapter.CategoryAdapter;
import com.ywb.shangcheng.adapter.decoration.DividerItemDecoration;
import com.ywb.shangcheng.entity.Banner;
import com.ywb.shangcheng.entity.Category;
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
public class CategoryFragment extends Fragment {
    @BindView(R.id.category_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.category_wares_rv)
    RecyclerView mWaresRecyclerView;
    @BindView(R.id.category_sl)
    SliderLayout mSliderLayout;
    @BindView(R.id.category_mrl)
    MaterialRefreshLayout mRefreshLayout;

    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private CategoryAdapter mCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this,view);
        requestCategoryData();
        requestBannerData();
        return view;
    }

    private void requestCategoryData() {
        okHttpHelper.get(Contants.API.CATEGORY_LIST,
                new SpotsCallBack<List<Category>>(getContext()) {

                    @Override
                    public void onSuccess(Response response, List<Category> categories) {
                        showCategoryData(categories);
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }

    private void showCategoryData(List<Category> categories) {
        mCategoryAdapter = new CategoryAdapter(getContext(), categories);
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
    }
    private void requestBannerData() {
        String url = Contants.API.BANNER + "?type=2";
        //带对话框的
        okHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                showBannerData(banners);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    private void showBannerData(List<Banner> mBanners) {
        if (mBanners != null) {
            for (Banner banner : mBanners) {
                DefaultSliderView sliderView = new DefaultSliderView(getActivity());
                sliderView
                        //  .description(banner.getDescription())
                        .image(banner.getImgUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(sliderView);
            }
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//设置默认的指示器
        //sliderShow.setCustomIndicator(mIndicator);//设置自定义指示器
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);//设置转场效果
        mSliderLayout.setDuration(3000);

    }
}
