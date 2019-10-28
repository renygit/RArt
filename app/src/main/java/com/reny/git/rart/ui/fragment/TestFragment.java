package com.reny.git.rart.ui.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.reny.git.mvvmlib.utils.LogUtils;
import com.reny.git.rart.R;
import com.reny.git.rart.R2;
import com.reny.git.rart.core.BaseFragment;
import com.reny.git.rart.ui.vm.TestVM;
import com.reny.git.rart.utils.glide.GlideHelper;

import butterknife.BindView;

public class TestFragment extends BaseFragment<TestVM> {

    @BindView(R2.id.tv)
    TextView tv;
    @BindView(R2.id.iv)
    ImageView iv;


    private int tabIndex;

    public TestFragment(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        vm.liveData.observe(this, splashPushData -> {
            if (vm.isRefresh) {
                LogUtils.e("splashPushData...init  " + tabIndex);
            } else {
                LogUtils.e("splashPushData...loadmore  " + tabIndex);
            }

            tv.setText(splashPushData.getResults().get(0).getTitle());
            GlideHelper.display(iv, "https://ww1.sinaimg.cn/large/0073sXn7ly1fze98gfasag30a80i84qp?imageView2/2/w/460");
        });
    }

    @Override
    protected void lazyLoad() {
        vm.loadData(true);
    }

}
