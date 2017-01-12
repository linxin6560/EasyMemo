package com.forman.lib.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 编辑帮助类
 * Created by LinXin on 2016/8/30 9:41.
 */
@SuppressWarnings("unused")
public class EditHelper<T> {

    private boolean isEditMode;
    private HashSet<T> mSelectedSet;
    private HashSet<OnItemChangeListener> listenerSet;

    public EditHelper() {
        mSelectedSet = new HashSet<>();
    }

    public void addOnItemChangeListener(OnItemChangeListener listener) {
        if (listenerSet == null) {
            listenerSet = new HashSet<>();
        }
        listenerSet.add(listener);
    }

    public void removeOnItemChangeListener(OnItemChangeListener listener) {
        if (listenerSet == null)
            return;
        listenerSet.remove(listener);
    }

    /**
     * 设置编辑模式
     *
     * @param editMode 是否是编辑模式
     */
    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
        callChange();
    }

    /**
     * 是否是编辑模式
     *
     * @return 是否是编辑模式
     */
    public boolean isEditMode() {
        return isEditMode;
    }

    /**
     * 获取所有选中的集合
     *
     * @return 选中集合
     */
    public Set<T> getSelectedSet() {
        return mSelectedSet;
    }

    /**
     * 是否包含该子项
     *
     * @param t 子项
     * @return true：有包含
     */
    public boolean contains(T t) {
        return mSelectedSet.contains(t);
    }

    /**
     * 包含一个列表
     *
     * @param c
     * @return
     */
    public boolean containsList(Collection<T> c) {
        return mSelectedSet.containsAll(c);
    }

    /**
     * 添加列表
     *
     * @param t 选中项
     */
    public void addItem(T t) {
        mSelectedSet.add(t);
        callChange();
    }

    /**
     * 删除列表
     *
     * @param t 选中项
     */
    public void removeItem(T t) {
        mSelectedSet.remove(t);
        callChange();
    }

    /**
     * 添加列表
     *
     * @param c 选中项
     */
    public void addList(Collection<T> c) {
        mSelectedSet.addAll(c);
        callChange();
    }

    /**
     * 删除列表
     *
     * @param c 选中项
     */
    public void removeList(Collection<T> c) {
        mSelectedSet.removeAll(c);
        callChange();
    }

    /**
     * 清除
     */
    public void clear() {
        mSelectedSet.clear();
        callChange();
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return mSelectedSet.isEmpty();
    }

    private void callChange() {
        if (listenerSet == null)
            return;
        for (OnItemChangeListener listener : listenerSet) {
            listener.onItemChange();
        }
    }

    public interface OnItemChangeListener {
        void onItemChange();
    }
}
