package me.levylin.lib.base;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.forman.lib.utils.ToastUtils;
import com.levylin.lib.loader.base.DataLoader;
import com.levylin.lib.loader.base.ILoaderView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.levylin.lib.base.mvp.BasePresenter;
import me.levylin.lib.base.mvp.IMVPView;

/**
 * Activity基类
 * Created by LinXin on 2016/6/7 10:08.
 */
public abstract class BaseActivity extends AppCompatActivity implements IMVPView, ILoaderView {

    protected View mainView;
    protected Toolbar mToolbar;
    private long mLastTime;
    private Unbinder mUnbinder;
    private DataLoader mDataLoader;//Loader总控,防止loader发现内存泄露
    private BasePresenter mPresenter;//Presenter总控

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!useCustomLayoutId()) {//如果没有设置toolbar，则不用默认布局，以减少层级
            setContentView(R.layout.act_base);
            initToolbar();
            initContentView();
        } else if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
        }
        mUnbinder = ButterKnife.bind(this);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.act_base_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(!isMainActivity());
        }
    }

    /**
     * 初始化内容视图
     */
    private void initContentView() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.act_base_content_view_stub);
        viewStub.setLayoutResource(getLayoutResId());
        mainView = viewStub.inflate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityCompat.finishAfterTransition(this);
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
     * 是否使用自定义的布局
     */
    protected boolean useCustomLayoutId() {
        return false;
    }

    /**
     * 是否是主界面，主界面的话，点击返回键会弹出退出提示框
     *
     * @return true：是，false：否
     */
    protected boolean isMainActivity() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!isMainActivity()) {
            super.onBackPressed();
            return;
        }
        long time = System.currentTimeMillis();
        if (mLastTime == 0 || time - mLastTime > 3000) {
            ToastUtils.makeShortToast(R.string.app_exit);
            mLastTime = time;
        } else {
            finish();
        }
    }

    /**
     * 添加Presenter
     *
     * @param presenter the presenter
     */
    @Override
    public void setPresenter(BasePresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setDataLoader(DataLoader loader) {
        this.mDataLoader = loader;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLoader();
        mUnbinder.unbind();
        unbindDrawables(findViewById(android.R.id.content));
        System.gc();
    }

    @Override
    public void destroyPresenter() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void destroyLoader() {
        if (mDataLoader != null) {
            mDataLoader.onDestroy();
            mDataLoader = null;
        }
    }

    /**
     * 解除图片绑定
     *
     * @param view 要解除的视图
     */
    private void unbindDrawables(View view) {
        if (view == null)
            return;
        if (view.getBackground() != null && view.getBackground() instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) view.getBackground();
            view.setBackgroundResource(0);
            bd.setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    @Override
    public void showLoadingDialog(boolean isShow) {
        if (isFinishing())
            return;
    }
}
