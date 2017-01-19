package me.levylin.easymemo.ui.main.dagger;

import com.levylin.loader.ILoaderView;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import me.levylin.easymemo.entity.Memo;
import me.levylin.easymemo.entity.MemoDao;
import me.levylin.easymemo.ui.main.mvp.MainModel;
import me.levylin.lib.base.loader.SimpleListLoader;

/**
 * 主界面模块
 * Created by LinXin on 2017/1/18 14:10.
 */
@Module
public class MainModule {

    private ILoaderView loaderView;

    public MainModule(ILoaderView loaderView) {
        this.loaderView = loaderView;
    }

    @Provides
    List<Memo> provideMemoList() {
        return new ArrayList<>();
    }

    @Provides
    MainModel provideModel(List<Memo> list, MemoDao dao) {
        return new MainModel(list, dao);
    }

    @Provides
    SimpleListLoader<Memo> provideLoader(MainModel model) {
        return new SimpleListLoader<>(loaderView, model);
    }
}
