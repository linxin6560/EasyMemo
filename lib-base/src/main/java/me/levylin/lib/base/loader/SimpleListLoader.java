package me.levylin.lib.base.loader;

import com.levylin.loader.ILoaderView;
import com.levylin.loader.ListLoader;
import com.levylin.loader.model.IListModel;

import java.util.List;

/**
 * 简化的ListLoader
 * Created by LinXin on 2017/1/19 10:03.
 */
public class SimpleListLoader<ITEM> extends ListLoader<List<ITEM>, ITEM> {
    public SimpleListLoader(ILoaderView view, IListModel<List<ITEM>, ITEM> listModel) {
        super(view, listModel);
    }
}
