package com.mhmmdyldi.mytemplate.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.mhmmdyldi.mytemplate.R;
import com.mhmmdyldi.mytemplate.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView{

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityBaseComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        mPresenter.onAttach(LoginActivity.this);
    }

    @OnClick(R.id.button_server_login)
    void onServerLoginClicked(View view){
        mPresenter.onServerLoginTouched("testUser", "testPass");
    }


}
