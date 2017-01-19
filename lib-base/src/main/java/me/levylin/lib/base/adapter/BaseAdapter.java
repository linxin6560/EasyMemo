package me.levylin.lib.base.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Adapter基类
 * Created by LinXin on 2017/1/18 14:08.
 */
public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public BaseAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public BaseAdapter(List<T> data) {
        super(data);
    }
}
