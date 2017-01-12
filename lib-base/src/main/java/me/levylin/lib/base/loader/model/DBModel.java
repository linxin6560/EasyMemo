package me.levylin.lib.base.loader.model;

import com.levylin.lib.loader.base.listener.OnLoadListener;
import com.levylin.lib.loader.base.model.IListModel;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据库Model
 * Created by LinXin on 2017/1/12 11:59.
 */
public abstract class DBModel<ITEM> implements IListModel<List<ITEM>, ITEM> {

    protected static int FIRST_PAGE = 0;
    private List<ITEM> mList;
    private int page;
    private boolean hasNext;
    private boolean isManualRefresh;
    private Disposable mDisposable;
    private int pageCount;

    public DBModel(List<ITEM> mList) {
        this.mList = mList;
    }

    @Override
    public void clear() {
        mList.clear();
    }

    @Override
    public void preLoadNext() {
        page++;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public boolean ensureHasNext(List<ITEM> response, List<ITEM> mapList) {
        return page < pageCount;
    }

    @Override
    public List<ITEM> map(List<ITEM> response) {
        return response;
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    @Override
    public void setData(boolean isRefreshing, List<ITEM> response) {
        if (page == FIRST_PAGE) {
            clear();
        }
        List<ITEM> mapList = map(response);
        hasNext = ensureHasNext(response, mapList);
        if (mapList == null) {
            return;
        }
        mList.addAll(mapList);
    }

    @Override
    public boolean isManualRefresh() {
        return isManualRefresh;
    }

    @Override
    public void setManualRefresh(boolean isManualRefresh) {
        this.isManualRefresh = isManualRefresh;
    }

    @Override
    public void preRefresh() {
        page = FIRST_PAGE;
    }

    @Override
    public void preReLoad() {

    }

    @Override
    public void load(final OnLoadListener<List<ITEM>> listener) {
        listener.onStart();
        mDisposable = Flowable.create((FlowableOnSubscribe<List<ITEM>>) e -> {
            e.setCancellable(this::cancelQuery);
            List<ITEM> list = getQueriedList();
            pageCount = getPageCount();
            if (e.isCancelled())
                return;
            e.onNext(list);
            e.onComplete();
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onSuccess, listener::onError);
    }

    @Override
    public void cancel() {
        if (mDisposable == null)
            return;
        if (mDisposable.isDisposed())
            return;
        mDisposable.dispose();
        mDisposable = null;
    }

    protected abstract List<ITEM> getQueriedList() throws SQLException;

    protected abstract int getPageCount() throws SQLException;

    protected abstract void cancelQuery();
}
