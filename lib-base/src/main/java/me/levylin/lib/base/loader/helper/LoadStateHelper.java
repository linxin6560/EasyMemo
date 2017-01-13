package me.levylin.lib.base.loader.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.levylin.lib.loader.base.helper.intf.ILoadStateHelper;
import com.levylin.lib.loader.base.helper.listener.OnReloadListener;

import me.levylin.lib.base.R;

/**
 * 加载状态图
 * Created by LinXin on 2017/1/12 15:00.
 */
public class LoadStateHelper implements ILoadStateHelper {

    private View mContentView;
    private TextView mStateTv;
    private ProgressBar mProgressBar;
    private ViewGroup mStateContainer;
    private boolean isError;

    public LoadStateHelper(View contentView) {
        this.mContentView = contentView;
        Context context = contentView.getContext();
        ViewGroup parent = (ViewGroup) contentView.getParent();
        LayoutInflater.from(context).inflate(R.layout.layout_load_state, parent, true);

        mStateContainer = (ViewGroup) parent.findViewById(R.id.load_state_container);
        mStateTv = (TextView) parent.findViewById(R.id.load_state_tv);
        mProgressBar = (ProgressBar) parent.findViewById(R.id.load_state_pb);
    }

    @Override
    public void showContent() {
        isError = false;
        mContentView.setVisibility(View.VISIBLE);
        mStateContainer.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        isError = false;
        mContentView.setVisibility(View.GONE);
        mStateContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mStateTv.setText(R.string.loading);
    }

    @Override
    public void showEmpty() {
        isError = false;
        mContentView.setVisibility(View.GONE);
        mStateContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mStateTv.setText(R.string.have_not_write_memo);
    }

    @Override
    public void showError(boolean isEmpty, Throwable t) {
        if (!isEmpty)
            return;
        isError = true;
        mContentView.setVisibility(View.GONE);
        mStateContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mStateTv.setText(R.string.load_error);
    }

    @Override
    public void setReloadListener(OnReloadListener listener) {
        mStateContainer.setOnClickListener(v -> {
            if (isError) {
                listener.onReLoad();
            }
        });
    }
}
