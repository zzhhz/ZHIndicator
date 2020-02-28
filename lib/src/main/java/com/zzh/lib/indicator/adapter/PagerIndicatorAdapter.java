package com.zzh.lib.indicator.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import com.zzh.lib.indicator.item.PagerIndicatorItem;

public abstract class PagerIndicatorAdapter {
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public final void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public final void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public final void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    /**
     * 创建Item
     *
     * @param position
     * @param viewParent
     * @return
     */
    public final View createPagerIndicatorItem(int position, ViewGroup viewParent) {
        final PagerIndicatorItem item = onCreatePagerIndicatorItem(position, viewParent);
        if (item instanceof View) {
            return (View) item;
        } else {
            throw new IllegalArgumentException("onCreatePagerIndicatorItem method must return instance of View");
        }
    }

    /**
     * 创建Item
     *
     * @param position
     * @param viewParent
     * @return
     */
    protected abstract PagerIndicatorItem onCreatePagerIndicatorItem(int position, ViewGroup viewParent);
}
