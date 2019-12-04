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
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(GithubRepo.class, new GithubRepoDeserializer())
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                                Credentials.basic(username, password));

                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                }).build();
        Retrofit rxTutorialRetrofit = new Retrofit.Builder()
                .baseUrl(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.)
                .build();

        githubAPI = retrofit.create(GithubAPI.class);
    }


}
