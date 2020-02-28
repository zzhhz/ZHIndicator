package com.zzh.lib.indicator.group;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zzh.lib.indicator.adapter.PagerIndicatorAdapter;
import com.zzh.lib.indicator.item.PagerIndicatorItem;
import com.zzh.lib.indicator.item.impl.ImagePagerIndicatorItem;
import com.zzh.lib.indicator.track.PagerIndicatorTrack;
import com.zzh.lib.viewpager.helper.HPagerPercentChangeListener;
import com.zzh.lib.viewpager.helper.HPagerSelectedChangeListener;
import com.zzh.lib.viewpager.helper.HViewPagerHolder;


/**
 * ViewPager指示器Group
 */
public abstract class BasePagerIndicatorGroup extends LinearLayout implements PagerIndicatorGroup {
    public BasePagerIndicatorGroup(Context context) {
        super(context);
        init();
    }

    public BasePagerIndicatorGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasePagerIndicatorGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private PagerIndicatorAdapter mAdapter;
    private PagerIndicatorTrack mPagerIndicatorTrack;

    private void init() {
        //设置一个默认的Adapter
        setAdapter(new PagerIndicatorAdapter() {
            @Override
            public PagerIndicatorItem onCreatePagerIndicatorItem(int position, ViewGroup viewParent) {
                return new ImagePagerIndicatorItem(getContext());
            }
        });
    }

    private final HViewPagerHolder mViewPagerHolder = new HViewPagerHolder() {
        @Override
        protected void onViewPagerChanged(ViewPager oldPager, ViewPager newPager) {
            mPagerSelectedChangeListener.setViewPager(newPager);
            mPagerPercentChangeListener.setViewPager(newPager);
        }
    };

    /**
     * 页面数量变化和选中监听
     */
    private final HPagerSelectedChangeListener mPagerSelectedChangeListener = new HPagerSelectedChangeListener() {
        @Override
        protected void onDataSetChanged() {
            BasePagerIndicatorGroup.this.onDataSetChanged(getAdapterCount());
            super.onDataSetChanged();
        }

        @Override
        protected void onSelectedChanged(int index, boolean selected) {
            BasePagerIndicatorGroup.this.onSelectChanged(index, selected);
        }
    };

    /**
     * 滚动百分比监听
     */
    private final HPagerPercentChangeListener mPagerPercentChangeListener = new HPagerPercentChangeListener() {
        @Override
        public void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft) {
            BasePagerIndicatorGroup.this.onShowPercent(position, showPercent, isEnter, isMoveLeft);
        }
    };

    @Override
    public void setViewPager(ViewPager viewPager) {
        mViewPagerHolder.setViewPager(viewPager);
    }

    @Override
    public ViewPager getViewPager() {
        return mViewPagerHolder.getViewPager();
    }

    @Override
    public void setAdapter(PagerIndicatorAdapter adapter) {
        if (mAdapter != null)
            mAdapter.unregisterDataSetObserver(mIndicatorAdapterDataSetObserver);

        mAdapter = adapter;

        if (adapter != null)
            adapter.registerDataSetObserver(mIndicatorAdapterDataSetObserver);
    }

    @Override
    public PagerIndicatorAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setPagerIndicatorTrack(PagerIndicatorTrack pagerIndicatorTrack) {
        mPagerIndicatorTrack = pagerIndicatorTrack;
    }

    @Override
    public PagerIndicatorTrack getPagerIndicatorTrack() {
        return mPagerIndicatorTrack;
    }

    @Override
    public void onDataSetChanged(int count) {
        final PagerIndicatorTrack track = getPagerIndicatorTrack();
        if (track != null)
            track.onDataSetChanged(count);
    }

    @Override
    public void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft) {
        final PagerIndicatorItem item = getPagerIndicatorItem(position);
        if (item != null) {
            item.onShowPercent(showPercent, isEnter, isMoveLeft);

            final PagerIndicatorTrack track = getPagerIndicatorTrack();
            if (track != null)
                track.onShowPercent(position, showPercent, isEnter, isMoveLeft, item.getPositionData());
        }
    }

    @Override
    public void onSelectChanged(int position, boolean selected) {
        final PagerIndicatorItem item = getPagerIndicatorItem(position);
        if (item != null) {
            item.onSelectChanged(selected);

            final PagerIndicatorTrack track = getPagerIndicatorTrack();
            if (track != null)
                track.onSelectChanged(position, selected, item.getPositionData());
        }
    }

    private final DataSetObserver mIndicatorAdapterDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            onDataSetChanged(mViewPagerHolder.getAdapterCount());
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };
}
