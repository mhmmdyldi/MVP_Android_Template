package com.mhmmdyldi.mytemplate.ui.login;

import com.mhmmdyldi.mytemplate.R;
import com.mhmmdyldi.mytemplate.data.DataManager;
import com.mhmmdyldi.mytemplate.data.network.model.LoginRequest;
import com.mhmmdyldi.mytemplate.data.network.model.LoginResponse;
import com.mhmmdyldi.mytemplate.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

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
        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest("Email", "Password"))
                .subscribeOn()
                .observeOn()
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
        );
        getmMvpActivityView().launchMainActivity();

    }
}
