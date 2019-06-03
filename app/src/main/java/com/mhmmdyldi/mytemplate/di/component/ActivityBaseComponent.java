package com.mhmmdyldi.mytemplate.di.component;

import com.mhmmdyldi.mytemplate.di.PerActivity;
import com.mhmmdyldi.mytemplate.di.modules.ActivityModule;
import com.mhmmdyldi.mytemplate.ui.login.LoginActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationBaseComponent.class, modules = ActivityModule.class)
public interface ActivityBaseComponent {
    void inject(LoginActivity loginActivity);
}
