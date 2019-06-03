package com.mhmmdyldi.mytemplate.ui.login;

import android.util.Log;

import com.mhmmdyldi.mytemplate.R;
import com.mhmmdyldi.mytemplate.data.DataManager;
import com.mhmmdyldi.mytemplate.ui.base.BasePresenter;
import com.mhmmdyldi.mytemplate.ui.base.MvpActivityView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V>{
    private static final String TAG = LoginPresenter.class.getName();

    @Inject
    public LoginPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onServerLoginTouched(String userName, String password) {
        if (userName == null || userName.isEmpty()){
            getmMvpActivityView().onError(R.string.empty_username_login);
            return;
        }
        if (password == null || password.isEmpty()){
            getmMvpActivityView().onError(R.string.empty_password_login);
            return;
        }
        getmMvpActivityView().showLoading();
//        getCompositeDisposable().add();

    }
}
