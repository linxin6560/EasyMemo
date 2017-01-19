package me.levylin.easymemo.ui.main.dagger;

import dagger.Component;
import me.levylin.easymemo.AppComponent;
import me.levylin.easymemo.ui.main.MainActivity;
import me.levylin.easymemo.utils.ActivityScoped;

/**
 * Created by LinXin on 2017/1/18 17:18.
 */
@ActivityScoped
@Component(dependencies = AppComponent.class, modules = {MainModule.class})
public interface MainComponent {

    void inject(MainActivity activity);
}
