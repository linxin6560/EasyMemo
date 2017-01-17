package me.levylin.easymemo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.levylin.easymemo.entity.DaoMaster;
import me.levylin.easymemo.entity.DaoSession;
import me.levylin.easymemo.entity.MemoDao;

/**
 * 数据库Module
 * Created by LinXin on 2017/1/17 11:48.
 */
@Module
public class DaoModule {

    @Provides
    @Singleton
    public DaoMaster providerDaoMaster(EMApplication application) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application, "memo.db");
        return new DaoMaster(helper.getWritableDb());
    }

    @Provides
    @Singleton
    public DaoSession provideDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

    @Provides
    public MemoDao provideMemoDao(DaoSession session) {
        return session.getMemoDao();
    }
}
