package me.levylin.lib.base.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Presenter基础类,用于在Activity销毁的时候，回收view，以免造成内存泄露
 * Created by LinXin on 2016/8/25 19:16.
 */
public class BasePresenter<View extends IMVPView> {

    protected CompositeDisposable mSubscription;
    protected View view;

    public BasePresenter(View view) {
        this.view = view;
        view.setPresenter(this);
    }

    protected void addDisposable(Disposable disposable) {
        if (mSubscription == null) {
            mSubscription = new CompositeDisposable();
        }
        mSubscription.add(disposable);
    }

    public void onDestroy() {
        if (mSubscription != null) {//取消订阅
            mSubscription.dispose();
            mSubscription = null;
        }
        view = null;
    }

    public void cancel() {
    }
}
