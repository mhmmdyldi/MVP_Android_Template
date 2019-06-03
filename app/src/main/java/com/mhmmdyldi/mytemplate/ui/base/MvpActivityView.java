package com.mhmmdyldi.mytemplate.ui.base;

import android.support.annotation.StringRes;

public interface MvpActivityView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resID);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resID);

    boolean isNetworkConnected();

    void hideKeyboard();
}
