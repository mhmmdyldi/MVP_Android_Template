package com.mhmmdyldi.mytemplate.di.component;

import android.app.Application;
import android.content.Context;

import com.mhmmdyldi.mytemplate.TemplateAppApplication;
import com.mhmmdyldi.mytemplate.data.DataManager;
import com.mhmmdyldi.mytemplate.di.ApplicationContext;
import com.mhmmdyldi.mytemplate.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationBaseComponent {

    void inject(TemplateAppApplication app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
