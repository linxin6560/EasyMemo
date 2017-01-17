package me.levylin.easymemo.ui.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.levylin.easymemo.EMApplication;
import me.levylin.easymemo.R;
import me.levylin.easymemo.entity.MemoDao;
import me.levylin.lib.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Inject
    MemoDao memoDao;

    @BindView(R.id.main_memo_rv)
    RecyclerView mMemoRv;

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
        ((EMApplication) getApplication()).getAppComponent().inject(this);
    }
}
