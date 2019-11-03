package com.mhmmdyldi.mytemplate.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mhmmdyldi.mytemplate.R;
import com.mhmmdyldi.mytemplate.TemplateAppApplication;
import com.mhmmdyldi.mytemplate.di.component.ActivityBaseComponent;
import com.mhmmdyldi.mytemplate.di.component.DaggerActivityBaseComponent;
import com.mhmmdyldi.mytemplate.di.modules.ActivityModule;
import com.mhmmdyldi.mytemplate.ui.login.LoginActivity;
import com.mhmmdyldi.mytemplate.utils.CommonUtils;
import com.mhmmdyldi.mytemplate.utils.NetworkUtils;

import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity implements MvpActivityView{

    private ProgressDialog mProgressDialog;
    private Unbinder mUnbinder;
    private ActivityBaseComponent activityBaseComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBaseComponent = DaggerActivityBaseComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationBaseComponent(((TemplateAppApplication) getApplication()).getmApplicationBaseComponent())
                .build();
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    public ActivityBaseComponent getActivityBaseComponent() {
        return activityBaseComponent;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void onError(int resID) {
        onError(getString(resID));
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(int resID) {
        showMessage(getString(resID));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    public void setUnbinder(Unbinder unbinder){
        this.mUnbinder = unbinder;
    }
}
