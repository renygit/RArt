package com.reny.git.rart.core;

import android.app.Application;

import com.reny.git.mvvmlib.base.RBaseRepository;
import com.reny.git.mvvmlib.base.RBaseViewModel;

import androidx.annotation.NonNull;

/**
 * Created by reny on 2019/10/24.
 */
public abstract class BaseViewModel<T extends RBaseRepository> extends RBaseViewModel<T> {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

}
