package com.zzh.lib.indicator.group.impl;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.zzh.lib.indicator.adapter.PagerIndicatorAdapter;
import com.zzh.lib.indicator.group.BasePagerIndicatorGroup;
import com.zzh.lib.indicator.item.PagerIndicatorItem;

/**
 * 线性的ViewPager指示器Group
 */
public class LinearPagerIndicatorGroup extends BasePagerIndicatorGroup {
    public LinearPagerIndicatorGroup(Context context) {
        super(context);
        init();
    }

    public LinearPagerIndicatorGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearPagerIndicatorGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    public PagerIndicatorItem getPagerIndicatorItem(int position) {
        return (PagerIndicatorItem) getChildAt(position);
    }

    @Override
    public void onDataSetChanged(int pageCount) {
        super.onDataSetChanged(pageCount);
        removeAllViews();
        onAddPagerIndicatorItem(pageCount);
    }

    /**
     * 添加Item
     *
     * @param count 要添加的数量
     */
    protected void onAddPagerIndicatorItem(int count) {
        if (count <= 0)
            return;

        final PagerIndicatorAdapter adapter = getAdapter();
        if (adapter == null)
            return;

        for (int i = 0; i < count; i++) {
            final View view = adapter.createPagerIndicatorItem(i, this);

            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params == null) {
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(params);
            }

            if (!view.hasOnClickListeners()) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ViewPager viewPager = getViewPager();
                        if (viewPager != null)
                            viewPager.setCurrentItem(indexOfChild(v));
                    }
                });
            }

            addView(view, params);
        }
    }
}
