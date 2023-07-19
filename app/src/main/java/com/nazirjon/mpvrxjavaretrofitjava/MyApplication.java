package com.nazirjon.mpvrxjavaretrofitjava;

import android.app.Application;

import com.nazirjon.mpvrxjavaretrofitjava.di.components.ApplicationComponent;
import com.nazirjon.mpvrxjavaretrofitjava.di.modules.ApplicationModule;

public class MyApplication extends Application {
    private static ApplicationComponent applicationComponent;

    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
