package com.mhmmdyldi.mytemplate.di.modules;

import android.app.Application;
import android.content.Context;

import com.mhmmdyldi.mytemplate.BuildConfig;
import com.mhmmdyldi.mytemplate.data.AppDataManager;
import com.mhmmdyldi.mytemplate.data.DataManager;
import com.mhmmdyldi.mytemplate.data.network.ApiHeader;
import com.mhmmdyldi.mytemplate.data.network.ApiHelper;
import com.mhmmdyldi.mytemplate.data.network.AppApiHelper;
import com.mhmmdyldi.mytemplate.data.prefs.AppPreferencesHelper;
import com.mhmmdyldi.mytemplate.data.prefs.PreferencesHelper;
import com.mhmmdyldi.mytemplate.di.ApiInfo;
import com.mhmmdyldi.mytemplate.di.ApplicationContext;
import com.mhmmdyldi.mytemplate.di.PreferenceInfo;
import com.mhmmdyldi.mytemplate.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return mApplication;
    }

    @Provides
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper){
        return appApiHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManger(AppDataManager appDataManager){
        return appDataManager;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey, PreferencesHelper preferencesHelper){
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken());
    }


}
