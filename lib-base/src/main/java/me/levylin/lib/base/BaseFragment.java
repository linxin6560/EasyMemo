package me.levylin.lib.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.levylin.loader.DataLoader;
import com.levylin.loader.ILoaderView;

import butterknife.ButterKnife;
import me.levylin.lib.base.mvp.BasePresenter;
import me.levylin.lib.base.mvp.IMVPView;

/**
 * Fragment基类
 * Created by LinXin on 2016/8/3 15:10.
 */
public abstract class BaseFragment extends Fragment implements IMVPView, ILoaderView {

    private View mainView;
    private DataLoader mDataLoader;//Loader总控,防止loader发现内存泄露
    private BasePresenter mPresenter;//Presenter总控

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(getLayoutResId(), container, false);
            ButterKnife.bind(this, mainView);
            init(mainView);
        } else {
            ViewGroup parent = (ViewGroup) mainView.getParent();
            if (parent != null) {
                parent.removeView(mainView);
            }
        }
        return mainView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取布局ID
     *
     * @return 布局ID
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 初始化界面视图
     *
     * @param mainView 主视图
     */
    protected abstract void init(View mainView);

    @Override
    public void setDataLoader(DataLoader loader) {
        mDataLoader = loader;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void destroyLoader() {
        if (mDataLoader != null) {
            mDataLoader.onDestroy();
            mDataLoader = null;
        }
    }

    @Override
    public void destroyPresenter() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLoader();
        destroyPresenter();
    }


    @Override
    public void showLoadingDialog(boolean isShow) {
    }
}
