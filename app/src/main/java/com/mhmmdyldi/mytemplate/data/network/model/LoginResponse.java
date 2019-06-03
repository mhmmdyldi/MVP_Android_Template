package com.mhmmdyldi.mytemplate.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @Expose
    @SerializedName("sampleOne")
    private String responseObjectOne;

    @Expose
    @SerializedName("sampleOne")
    private String responseObjectTwo;


    public String getResponseObjectOne() {
        return responseObjectOne;
    }

    public void setResponseObjectOne(String responseObjectOne) {
        this.responseObjectOne = responseObjectOne;
    }

    public String getResponseObjectTwo() {
        return responseObjectTwo;
    }

    public void setResponseObjectTwo(String responseObjectTwo) {
        this.responseObjectTwo = responseObjectTwo;
    }
}
