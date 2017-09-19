package com.gondia.dailyneeds.root;

import com.gondia.dailyneeds.Login.Login;
import com.gondia.dailyneeds.Login.LoginModule;
//import com.gondia.dailyneeds.MainPage.ApiModule;
import com.gondia.dailyneeds.MainPage.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nilesh1 on 11-09-2017.
 */
@Singleton
@Component(modules = {ApplicationModule.class,LoginModule.class})
public interface ApplicationComponent {
    void inject(Login target);
    //void inject(MainActivity mainActivity);


}
