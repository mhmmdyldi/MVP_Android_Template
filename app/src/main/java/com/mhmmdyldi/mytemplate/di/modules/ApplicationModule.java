package com.mhmmdyldi.mytemplate.di.modules;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mhmmdyldi.mytemplate.BuildConfig;
import com.mhmmdyldi.mytemplate.data.AppDataManager;
import com.mhmmdyldi.mytemplate.data.DataManager;
import com.mhmmdyldi.mytemplate.data.network.ApiHeader;
import com.mhmmdyldi.mytemplate.data.network.ApiHelper;
import com.mhmmdyldi.mytemplate.data.network.AppApiHelper;
import com.mhmmdyldi.mytemplate.data.network.AppApiService;
import com.mhmmdyldi.mytemplate.data.prefs.AppPreferencesHelper;
import com.mhmmdyldi.mytemplate.data.prefs.PreferencesHelper;
import com.mhmmdyldi.mytemplate.di.ApiAppScope;
import com.mhmmdyldi.mytemplate.di.ApiInfo;
import com.mhmmdyldi.mytemplate.di.ApplicationContext;
import com.mhmmdyldi.mytemplate.di.PreferenceInfo;
import com.mhmmdyldi.mytemplate.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    @Provides
    @ApiAppScope
    public AppApiService provideApiService(Retrofit appApiRetrofit){
        return appApiRetrofit.create(AppApiService.class);
    }

    @Provides
    @ApiAppScope
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }

    @Provides
    @ApiAppScope
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .build();
    }


}
