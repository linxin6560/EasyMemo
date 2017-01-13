package me.levylin.lib.base.loader;

import java.util.List;

/**
 * 数据库加载列表专用
 * Created by LinXin on 2017/1/13 15:14.
 */
public class PageVo<ITEM> {

    private List<ITEM> itemList;
    private int count;

    public List<ITEM> getItemList() {
        return itemList;
    }

    public void setItemList(List<ITEM> itemList) {
        this.itemList = itemList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
