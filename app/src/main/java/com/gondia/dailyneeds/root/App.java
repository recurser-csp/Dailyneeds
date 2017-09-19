package com.gondia.dailyneeds.root;

import android.app.Application;

import com.gondia.dailyneeds.Login.LoginModule;
//import com.gondia.dailyneeds.MainPage.ApiModule;

/**
 * Created by Nilesh1 on 11-09-2017.
 */

public class App extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
               .applicationModule(new ApplicationModule(this))
                .loginModule(new LoginModule())
                //.apiModule(new ApiModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
