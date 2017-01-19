package me.levylin.easymemo.ui.main.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.forman.lib.utils.TimeUtils;

import java.util.List;

import me.levylin.easymemo.R;
import me.levylin.easymemo.entity.Memo;
import me.levylin.lib.base.adapter.BaseAdapter;

/**
 * 备忘录Adapter
 * Created by LinXin on 2017/1/13 15:26.
 */
public class MemoAdapter extends BaseAdapter<Memo> {

    public MemoAdapter(List<Memo> data) {
        super(R.layout.act_main_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Memo memo) {
        holder.setText(R.id.memo_content_tv, memo.getContent())
                .setText(R.id.memo_create_time_tv, TimeUtils.getNormalTime(memo.getTime()));

    }
}
