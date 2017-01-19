package me.levylin.lib.base.loader.helper;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.levylin.loader.helper.intf.IFooterViewHelper;
import com.levylin.loader.helper.listener.OnReloadListener;
import com.levylin.loader.helper.state.LoadMoreState;

import me.levylin.lib.base.R;


/**
 * 底部加载更多帮助类
 * Created by LinXin on 2016/6/27 14:38.
 */
public class FooterViewHelper implements IFooterViewHelper, View.OnClickListener {

    private View mFooterView;
    private ProgressBar mProgressBar;
    private TextView mLoadMoreTv;
    private OnReloadListener mReloadListener;
    private LoadMoreState state = LoadMoreState.IDLE;

    private int noMoreTextResId;

    public FooterViewHelper(ViewGroup group) {
        Context context = group.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mFooterView = inflater.inflate(R.layout.layout_load_more_footer, group, false);
        mFooterView.setOnClickListener(this);
        mProgressBar = (ProgressBar) mFooterView.findViewById(R.id.layout_load_more_footer_pb);
        mLoadMoreTv = (TextView) mFooterView.findViewById(R.id.layout_load_more_footer_tv);
        setNoMoreTextResId(R.string.no_more);
    }

    public void setNoMoreTextResId(@StringRes int noMoreTextResId) {
        this.noMoreTextResId = noMoreTextResId;
    }

    @Override
    public View getFooterView() {
        return mFooterView;
    }

    @Override
    public void setOnReloadListener(OnReloadListener listener) {
        mReloadListener = listener;
    }

    @Override
    public void showIdle() {
        this.state = LoadMoreState.IDLE;
        mProgressBar.setVisibility(View.GONE);
        mLoadMoreTv.setText("");
    }

    @Override
    public void showLoading() {
        this.state = LoadMoreState.LOADING;
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadMoreTv.setText(R.string.common_load_more);
    }

    @Override
    public void showNoMore() {
        this.state = LoadMoreState.NO_MORE;
        mProgressBar.setVisibility(View.GONE);
        if (noMoreTextResId != 0) {
            mLoadMoreTv.setText(noMoreTextResId);
        } else {
            mLoadMoreTv.setText("");
        }
    }

    @Override
    public void showError() {
        this.state = LoadMoreState.ERROR;
        mProgressBar.setVisibility(View.GONE);
        mLoadMoreTv.setText(R.string.common_load_more_error);
    }

    @Override
    public boolean isIdle() {
        return state == LoadMoreState.IDLE;
    }

    @Override
    public boolean isLoadingMore() {
        return state == LoadMoreState.LOADING;
    }

    @Override
    public boolean isGoneWhenNoMore() {
        return noMoreTextResId == 0;
    }

    @Override
    public void onClick(View v) {
        if (state == LoadMoreState.ERROR && mReloadListener != null) {
            showLoading();
            mReloadListener.onReLoad();
        }
    }
}
