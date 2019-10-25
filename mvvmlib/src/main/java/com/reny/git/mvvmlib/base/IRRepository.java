package com.reny.git.mvvmlib.base;

import io.reactivex.disposables.Disposable;

/**
 * Created by reny on 2017/11/15.
 */

public interface IRRepository {

    void addDisposable(Disposable disposable);

    void onDestroy();

}
