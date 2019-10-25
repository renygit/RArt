package com.reny.git.mvvmlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.reny.git.mvvmlib.R;
import com.wang.avi.AVLoadingIndicatorView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Created by admin on 2017/7/21.
 */

public class MultiStateView extends FrameLayout {

    public static final int STATE_LOADING       = 0x00;
    public static final int STATE_ERROR         = 0x01;
    public static final int STATE_NO_NETWORK    = 0x02;
    public static final int STATE_EMPTY         = 0x03;
    public static final int STATE_CONTENT       = 0x04;

    private MultiStateConfig config;

    private String indicatorName;
    private int indicatorColor;
    private int bgColor;
    private int textColor;
    private String tipError;
    private String tipNoNetwork;
    private String tipEmpty, tipEmptyBtn;
    private Drawable imgError;
    private Drawable imgNoNetwork;
    private Drawable imgEmpty;


    private View stateView;
    private AVLoadingIndicatorView pb_loading;
    private View ll_tip;
    private ImageView iv_tip;
    private TextView tv_tip, tv_empty_btn;

    private int mViewState = -1;

    private OnRetryListener onRetryListener;
    private OnEmptyBtnListener onEmptyBtnListener;

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        this.onRetryListener = onRetryListener;
    }

    public void setOnEmptyBtnListener(OnEmptyBtnListener onEmptyBtnListener) {
        this.onEmptyBtnListener = onEmptyBtnListener;
    }

    public MultiStateView(@NonNull Context context) {
        this(context, null);
    }

    public MultiStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        config = MultiStateConfig.getInstance();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MultiStateView);
        indicatorName = ta.getString(R.styleable.MultiStateView_msv_indicatorName);
        indicatorColor = ta.getColor(R.styleable.MultiStateView_msv_indicatorColor, ContextCompat.getColor(context, config.getIndicatorColor()));
        bgColor = ta.getColor(R.styleable.MultiStateView_msv_bgColor, ContextCompat.getColor(context, config.getBgColor()));
        textColor = ta.getColor(R.styleable.MultiStateView_msv_textColor, ContextCompat.getColor(context, config.getTextColor()));
        tipError = ta.getString(R.styleable.MultiStateView_msv_tipError);
        tipNoNetwork = ta.getString(R.styleable.MultiStateView_msv_tipNoNetwork);
        tipEmpty = ta.getString(R.styleable.MultiStateView_msv_tipEmpty);
        tipEmptyBtn = ta.getString(R.styleable.MultiStateView_msv_tipEmpty_btn);
        imgError = ta.getDrawable(R.styleable.MultiStateView_msv_imgError);
        imgNoNetwork = ta.getDrawable(R.styleable.MultiStateView_msv_imgNoNetwork);
        imgEmpty = ta.getDrawable(R.styleable.MultiStateView_msv_imgEmpty);
        ta.recycle();

        if(TextUtils.isEmpty(indicatorName)){
            indicatorName = config.getIndicatorName();
        }

        if(TextUtils.isEmpty(tipError)){
            tipError = config.getTipError();
        }

        if(TextUtils.isEmpty(tipNoNetwork)){
            tipNoNetwork = config.getTipNoNetwork();
        }

        if(TextUtils.isEmpty(tipEmpty)){
            tipEmpty = config.getTipEmpty();
        }

        if(null == imgError){
            imgError = ContextCompat.getDrawable(context, config.getImgError());
        }
        if(null == imgNoNetwork){
            imgNoNetwork = ContextCompat.getDrawable(context, config.getImgNoNetwork());
        }
        if(null == imgEmpty){
            imgEmpty = ContextCompat.getDrawable(context, config.getImgEmpty());
        }


        stateView = LayoutInflater.from(context).inflate(R.layout.view_wrapper, null);
        stateView.setOnClickListener(null);

        pb_loading = (AVLoadingIndicatorView) stateView.findViewById(R.id.pb_loading);
        ll_tip = stateView.findViewById(R.id.ll_tip);
        iv_tip = (ImageView) stateView.findViewById(R.id.iv_tip);
        tv_tip = (TextView) stateView.findViewById(R.id.tv_tip);
        tv_empty_btn = (TextView) stateView.findViewById(R.id.tv_empty_btn);

        stateView.setBackgroundColor(bgColor);
        pb_loading.setIndicator(indicatorName);
        pb_loading.setIndicatorColor(indicatorColor);
        tv_tip.setTextColor(textColor);

        addView(stateView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        showLoading();
    }

    //直接用MultiStateConfig.getInstance().setConfig(build); 单例的
    /*public void setConfig(MultiStateConfig.Build build){
        config.setConfig(build);
    }*/

    public void setTipError(String tipError) {
        this.tipError = tipError;
    }

    public void setTipNoNetwork(String tipNoNetwork) {
        this.tipNoNetwork = tipNoNetwork;
    }

    public void setTipEmpty(String tipEmpty) {
        this.tipEmpty = tipEmpty;
    }

    public void setImgError(Drawable imgError) {
        this.imgError = imgError;
    }

    public void setImgNoNetwork(Drawable imgNoNetwork) {
        this.imgNoNetwork = imgNoNetwork;
    }

    public void setImgEmpty(Drawable imgEmpty) {
        this.imgEmpty = imgEmpty;
    }

    public void setTipEmptyBtn(String tipEmptyBtn) {
        this.tipEmptyBtn = tipEmptyBtn;
    }

    public int getViewState() {
        return mViewState;
    }

    public void showLoading(){
        showViewByStatus(STATE_LOADING);
    }

    public void showError(){
        showViewByStatus(STATE_ERROR);
    }

    public void showNoNetwork(){
        showViewByStatus(STATE_NO_NETWORK);
    }

    public void showEmpty(){
        showViewByStatus(STATE_EMPTY);
    }

    public void showContent(){
        showViewByStatus(STATE_CONTENT);
    }

    public void showViewByStatus(int viewState) {
        if(getViewState() == viewState){
            return;
        }
        mViewState = viewState;

        if(viewState == STATE_CONTENT){
            //显示内容
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                if(view.equals(stateView) && stateView.getVisibility() == VISIBLE){
                    view.setVisibility(GONE);
                }else {
                    view.setVisibility(R.id.ll_state == view.getId() ? View.GONE : View.VISIBLE);
                }
            }

        }else {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                if(view.equals(stateView) && stateView.getVisibility() == GONE){
                    view.setVisibility(VISIBLE);
                }else {
                    view.setVisibility(R.id.ll_state == view.getId() ? View.VISIBLE : View.GONE);
                }
            }

            pb_loading.setVisibility(viewState == STATE_LOADING ? VISIBLE : GONE);
            ll_tip.setVisibility(viewState == STATE_LOADING ? GONE : VISIBLE);

            if(viewState == STATE_LOADING){
                stateView.setOnClickListener(null);
            }else {
                stateView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(null != onRetryListener){
                            showLoading();
                            onRetryListener.onRetry();
                        }
                    }
                });
            }

            switch (viewState) {
                case STATE_ERROR:
                    if(null != imgError) {
                        iv_tip.setImageDrawable(imgError);
                    }
                    tv_tip.setText(tipError);
                    if(tv_empty_btn.getVisibility() == VISIBLE){
                        tv_empty_btn.setVisibility(GONE);
                    }
                    break;
                case STATE_NO_NETWORK:
                    if(null != imgNoNetwork) {
                        iv_tip.setImageDrawable(imgNoNetwork);
                    }
                    tv_tip.setText(tipNoNetwork);

                    if(tv_empty_btn.getVisibility() == VISIBLE){
                        tv_empty_btn.setVisibility(GONE);
                    }
                    break;
                case STATE_EMPTY:
                    if(null != imgEmpty) {
                        iv_tip.setImageDrawable(imgEmpty);
                    }
                    tv_tip.setText(tipEmpty);

                    if(!TextUtils.isEmpty(tipEmptyBtn)){
                        tv_empty_btn.setText(tipEmptyBtn);
                        tv_empty_btn.setVisibility(VISIBLE);
                        tv_empty_btn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(null != onEmptyBtnListener){
                                    onEmptyBtnListener.onClickEmptyBtn();
                                }
                            }
                        });
                    }else {
                        tv_empty_btn.setVisibility(GONE);
                    }
                    break;
            }
        }
    }

    public interface OnRetryListener{
        void onRetry();
    }

    public interface OnEmptyBtnListener{
        void onClickEmptyBtn();
    }

}
