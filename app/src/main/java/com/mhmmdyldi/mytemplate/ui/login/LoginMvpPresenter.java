package com.mhmmdyldi.mytemplate.ui.login;

import com.mhmmdyldi.mytemplate.ui.base.MvpActivityView;
import com.mhmmdyldi.mytemplate.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {
    void onServerLoginTouched(String userName, String password);
}
