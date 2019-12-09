package com.mhmmdyldi.mytemplate.data.network;

import com.mhmmdyldi.mytemplate.data.network.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AppApiService {
    @GET("users/{username}/repos")
    Call<LoginResponse> getReposForUser(@Path("username") String username);
}
