package me.levylin.easymemo;

import android.app.Application;

/**
 * application
 * Created by LinXin on 2017/1/13 15:23.
 */
public class EMApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .daoModule(new DaoModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
