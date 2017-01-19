package me.levylin.easymemo.ui.main.mvp;

import org.greenrobot.greendao.query.Query;

import java.sql.SQLException;
import java.util.List;

import me.levylin.easymemo.entity.Memo;
import me.levylin.easymemo.entity.MemoDao;
import me.levylin.lib.base.loader.request.DBModel;

/**
 * 主界面Model
 * Created by LinXin on 2017/1/18 17:06.
 */
public class MainModel extends DBModel<Memo> {

    private MemoDao memoDao;

    public MainModel(List<Memo> memos, MemoDao memoDao) {
        super(memos);
        System.out.println("memos="+memos);
        this.memoDao = memoDao;
    }

    @Override
    protected List<Memo> getQueriedList(int page) throws SQLException {
        Query<Memo> query = memoDao.queryBuilder().offset(page * PAGE_SIZE).limit(PAGE_SIZE).build();
        return query.list();
    }

    @Override
    protected void cancelQuery() {

    }
}
