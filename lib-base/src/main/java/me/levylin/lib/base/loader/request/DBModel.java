package me.levylin.lib.base.loader.request;

import com.levylin.loader.listener.OnLoadListener;
import com.levylin.loader.model.impl.ListModel;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据库加载器
 * Created by LinXin on 2017/1/18 14:28.
 */
public abstract class DBModel<ITEM> extends ListModel<List<ITEM>, ITEM> {

    private Disposable mDisposable;

    public DBModel(List<ITEM> items) {
        super(items);
    }

    @Override
    public void load(OnLoadListener<List<ITEM>> listener) {
        listener.onStart();
        mDisposable = Flowable.create((FlowableOnSubscribe<List<ITEM>>) e -> {
            e.setCancellable(this::cancelQuery);
            List<ITEM> list = getQueriedList(page);
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


    @Override
    public List<ITEM> map(List<ITEM> response) {
        return response;
    }

    protected abstract List<ITEM> getQueriedList(int page) throws SQLException;

    protected abstract void cancelQuery();
}
