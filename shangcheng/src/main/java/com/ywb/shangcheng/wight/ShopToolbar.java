package com.ywb.shangcheng.wight;

/**
 * Created by Administrator on 2017/2/13.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ywb.shangcheng.R;


/**
 * Created by Administrator on 2017/1/13 0013.
 */
public class ShopToolbar extends Toolbar {
    private LayoutInflater mInflater;
    private View mView;
    private EditText mSearchview;
    private TextView mTextTitle;
    private ImageButton mLeftButton;

    public Button getmRightButton() {
        return mRightButton;
    }

    public void setmRightButton(Button mRightButton) {
        this.mRightButton = mRightButton;
    }

    private Button mRightButton;

    public ShopToolbar(Context context) {
        this(context, null);
    }

    public ShopToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShopToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setContentInsetsRelative(10, 10);
        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.shopToolbar, defStyleAttr, 0);

            final Drawable leftIcon = a.getDrawable(R.styleable.shopToolbar_leftButtonIcon);
            if (leftIcon != null) {
                setLeftButtonIcon(leftIcon);
            }
            final Drawable rightIcon = a.getDrawable(R.styleable.shopToolbar_rightButtonIcon);
            if (rightIcon != null) {
                setRightButtonIcon(rightIcon);
            }

            CharSequence rightButtonText = a.getText(R.styleable.shopToolbar_rightButtonText);
            if (rightButtonText != null) {
                setRightButtonText(rightButtonText);
            }
            boolean isShowSearchView = a.getBoolean(R.styleable.shopToolbar_isShowSearchView, false);
            if (isShowSearchView) {
                showSearchView();
                hideTitleView();
            }
            a.recycle();
        }
    }

    public void setRightButtonText(CharSequence rightButtonText) {
        if (mRightButton != null) {
            mRightButton.setText(rightButtonText);
        }
        mRightButton.setVisibility(VISIBLE);
    }

    private void initView() {
        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar, null);
            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mSearchview = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mRightButton = (Button) mView.findViewById(R.id.toolbar_rightButton);
            mLeftButton = (ImageButton) mView.findViewById(R.id.toolbar_leftButton);

            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);
        }
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mTextTitle != null) {
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    public void showSearchView() {
        if (mSearchview != null) {
            mSearchview.setVisibility(VISIBLE);
        }
    }

    public void hideSearchView() {
        if (mSearchview != null) {
            mSearchview.setVisibility(GONE);
            mRightButton.setVisibility(GONE);
        }
    }

    public void showTitleView() {
        if (mTextTitle != null) {
            mTextTitle.setVisibility(VISIBLE);
        }
    }

    public void hideTitleView() {
        if (mTextTitle != null) {
            mTextTitle.setVisibility(GONE);
        }
    }
    public void setLeftButtonIcon(Drawable leftIcon) {
        if (mLeftButton != null) {
            mLeftButton.setImageDrawable(leftIcon);
            mLeftButton.setVisibility(VISIBLE);
        }
    }
    //设置右边的按钮
    public void setRightButtonIcon(Drawable rightIcon) {
        if (mRightButton != null) {
            mRightButton.setBackground(rightIcon);
            mRightButton.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonClickLinstener(OnClickListener listener) {
        mRightButton.setOnClickListener(listener);
    }


}

