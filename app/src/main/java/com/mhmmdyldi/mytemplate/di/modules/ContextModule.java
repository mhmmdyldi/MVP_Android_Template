package com.mhmmdyldi.mytemplate.di.modules;

import android.content.Context;

import com.mhmmdyldi.mytemplate.di.ApiAppScope;
import com.mhmmdyldi.mytemplate.di.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private final Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    @ApiAppScope
    @ApplicationContext
    public Context context(){
        return context;
    }
}
