package me.levylin.easymemo.ui.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.levylin.easymemo.EMApplication;
import me.levylin.easymemo.R;
import me.levylin.easymemo.entity.Memo;
import me.levylin.easymemo.ui.main.adapter.MemoAdapter;
import me.levylin.easymemo.ui.main.dagger.DaggerMainComponent;
import me.levylin.easymemo.ui.main.dagger.MainModule;
import me.levylin.lib.base.BaseActivity;
import me.levylin.lib.base.loader.SimpleListLoader;
import me.levylin.lib.base.loader.helper.LoadStateHelper;
import me.levylin.lib.base.loader.helper.RecyclerViewHelper;
import me.levylin.lib.base.loader.helper.RefreshHelper;

public class MainActivity extends BaseActivity {

    @Inject
    List<Memo> memos;
    @Inject
    SimpleListLoader<Memo> mLoader;

    @BindView(R.id.main_srl)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.main_memo_rv)
    RecyclerView mRecyclerView;

    @OnClick(R.id.main_fab)
    void onClick() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .appComponent(((EMApplication) getApplication()).getAppComponent())
                .build()
                .inject(this);
        initViews();
        mLoader.setListViewHelper(new RecyclerViewHelper(mRecyclerView));
        mLoader.setRefreshViewHelper(new RefreshHelper(mRefreshLayout));
        mLoader.setLoadStateHelper(new LoadStateHelper(mRefreshLayout));
        mLoader.load();
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        MemoAdapter adapter = new MemoAdapter(memos);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected boolean isMainActivity() {
        return true;
    }
}
