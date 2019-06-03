package com.mhmmdyldi.mytemplate.di.modules;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.mhmmdyldi.mytemplate.di.PerActivity;
import com.mhmmdyldi.mytemplate.ui.login.LoginMvpPresenter;
import com.mhmmdyldi.mytemplate.ui.login.LoginMvpView;
import com.mhmmdyldi.mytemplate.ui.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @PerActivity
    @Provides
    Context provideContext(){
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @PerActivity
    @Provides
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter){
        return presenter;
    }
}
