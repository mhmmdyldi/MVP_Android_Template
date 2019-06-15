package com.mhmmdyldi.mytemplate;

import android.app.Application;

import com.mhmmdyldi.mytemplate.data.DataManager;
import com.mhmmdyldi.mytemplate.di.component.ApplicationBaseComponent;

import javax.inject.Inject;

public class TemplateAppApplication extends Application {

    @Inject
    DataManager mDataManger;

    private ApplicationBaseComponent mApplicationBaseComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//        mApplicationBaseComponent = DaggerApplicationBaseComponent.builder()
//                .applicationModule(new ApplicationModule(this)).build();

        mApplicationBaseComponent.inject(this);
    }

    public ApplicationBaseComponent getmApplicationBaseComponent() {
        return mApplicationBaseComponent;
    }
}
