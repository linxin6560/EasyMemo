package me.levylin.easymemo.db;

/**
 * Created by LinXin on 2017/1/16 15:19.
 */
public class GreenDaoManager {

    private static class Holder {
        private static GreenDaoManager mInstance = new GreenDaoManager();
    }

    private GreenDaoManager() {
    }

    public static GreenDaoManager getInstance() {
        return Holder.mInstance;
    }
}
