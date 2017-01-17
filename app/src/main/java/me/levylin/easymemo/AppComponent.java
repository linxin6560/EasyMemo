package me.levylin.easymemo;

import javax.inject.Singleton;

import dagger.Component;
import me.levylin.easymemo.ui.main.MainActivity;

/**
 * App Component
 * Created by LinXin on 2017/1/16 15:45.
 */
@Singleton
@Component(modules = {AppModule.class, DaoModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
