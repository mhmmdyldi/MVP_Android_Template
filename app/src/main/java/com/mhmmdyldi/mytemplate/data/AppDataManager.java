package com.mhmmdyldi.mytemplate.data;

import android.content.Context;

import com.mhmmdyldi.mytemplate.data.network.ApiHelper;
import com.mhmmdyldi.mytemplate.data.network.model.LoginRequest;
import com.mhmmdyldi.mytemplate.data.network.model.LoginResponse;
import com.mhmmdyldi.mytemplate.di.ApplicationContext;

import javax.inject.Inject;

import io.reactivex.Single;

public class AppDataManager implements DataManager{
    private static final String TAG = AppDataManager.class.getName();

    private final Context mContext;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context, ApiHelper apiHelper) {
        this.mContext = context;
        this.mApiHelper = apiHelper;
    }

    @Override
    public void updateApiHeader(Long userID, String accessToken) {

    }

    @Override
    public void setUserAsLoggedOut() {

    }

    @Override
    public Single<LoginResponse> doServerLoginWScall(LoginRequest.ServerLoginRequest request) {
        return null;
    }
}
