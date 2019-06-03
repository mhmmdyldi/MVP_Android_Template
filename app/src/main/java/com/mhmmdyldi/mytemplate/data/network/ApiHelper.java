package com.mhmmdyldi.mytemplate.data.network;

import com.mhmmdyldi.mytemplate.data.network.model.LoginRequest;
import com.mhmmdyldi.mytemplate.data.network.model.LoginResponse;

import io.reactivex.Single;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<LoginResponse> doServerLoginWScall(LoginRequest.ServerLoginRequest request);

}
