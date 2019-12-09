package com.mhmmdyldi.mytemplate.di.modules;

import android.content.Context;

import com.mhmmdyldi.mytemplate.di.ApiAppScope;
import com.mhmmdyldi.mytemplate.di.ApplicationContext;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = ContextModule.class)
public class NetworkModule {
    @Provides
    @ApiAppScope
    public HttpLoggingInterceptor loggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @ApiAppScope
    public Cache cache(File cacheFile){
        return new Cache(cacheFile, 10 * 1000 * 1000);
    }

    @Provides
    @ApiAppScope
    public File cacheFile(@ApplicationContext Context context){
        return new File(context.getCacheDir(), "okhttp_cache");

    }

    @Provides
    @ApiAppScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache){
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }
}
