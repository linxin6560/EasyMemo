package me.levylin.easymemo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 总Module
 * Created by LinXin on 2017/1/16 15:42.
 */
@Module
public class AppModule {

    private EMApplication application;

    public AppModule(EMApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public EMApplication provideApplication() {
        return application;
    }
}
