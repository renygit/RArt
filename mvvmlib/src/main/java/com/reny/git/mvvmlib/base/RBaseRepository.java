package com.reny.git.mvvmlib.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by reny on 2019/10/24.
 */
public abstract class RBaseRepository implements IRRepository{

    private CompositeDisposable mCompositeDisposable;

    public RBaseRepository() {}

    @Override
    public void addDisposable(Disposable disposable) {
        if(null == mCompositeDisposable)mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        if(null != mCompositeDisposable) {
            this.mCompositeDisposable.clear();
            this.mCompositeDisposable = null;
        }
    }
}
