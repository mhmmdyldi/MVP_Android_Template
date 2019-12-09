package com.mhmmdyldi.mytemplate.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mhmmdyldi.mytemplate.data.network.model.LoginRequest;
import com.mhmmdyldi.mytemplate.data.network.model.LoginResponse;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    AppApiService appApiService;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        appApiService.getReposForUser()
    }


}
