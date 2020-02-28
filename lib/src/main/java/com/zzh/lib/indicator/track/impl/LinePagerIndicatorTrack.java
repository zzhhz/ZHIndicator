package com.zzh.lib.indicator.track.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zzh.lib.indicator.model.PositionData;
import com.zzh.lib.indicator.track.PagerIndicatorTrack;


/**
 * 线形的Item追踪view（开发中，未完成）
 */
public class LinePagerIndicatorTrack extends View implements PagerIndicatorTrack
{
    public LinePagerIndicatorTrack(Context context)
    {
        super(context);
        init();
    }

    public LinePagerIndicatorTrack(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private static final int DEFAULT_LINE_HEIGHT = 2;
    private static final int DEFAULT_LINE_COLOR = Color.parseColor("#d43d3d");
    private static final int DEFAULT_LINE_CORNER = 1;

    private int mLineColor;
    private float mLineHeight;
    private float mLineCorner;
    private RectF mLineRect = new RectF();

    private Paint mPaint;

    private void init()
    {
        mLineColor = DEFAULT_LINE_COLOR;
        mLineHeight = getResources().getDisplayMetrics().density * DEFAULT_LINE_HEIGHT;
        mLineCorner = getResources().getDisplayMetrics().density * DEFAULT_LINE_CORNER;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mLineColor);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawRoundRect(mLineRect, mLineCorner, mLineCorner, mPaint);
    }

    @Override
    public void onDataSetChanged(int count)
    {
        if (count <= 0)
        {
            setVisibility(View.GONE);
        } else
        {
            setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft, PositionData positionData)
    {
        if (positionData == null)
        {
            return;
        }

        mLineRect.top = positionData.bottom - mLineHeight;
        mLineRect.bottom = mLineRect.top + mLineHeight;

        if (isEnter)
        {
            if (isMoveLeft)
            {
                mLineRect.right = positionData.getLeftPercent(showPercent);
            } else
            {
                mLineRect.left = positionData.getLeftPercent(1 - showPercent);
            }
        } else
        {
            if (isMoveLeft)
            {
                mLineRect.left = positionData.getLeftPercent(1 - showPercent);
            } else
            {
                mLineRect.right = positionData.getLeftPercent(showPercent);
            }
        }

        invalidate();
    }

    @Override
    public void onSelectChanged(int position, boolean selected, PositionData positionData)
    {

    }
}
