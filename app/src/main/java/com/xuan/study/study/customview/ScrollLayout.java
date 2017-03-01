package com.xuan.study.study.customview;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by dengzhaoxuan on 16/1/12.
 */
public class ScrollLayout extends ViewGroup {

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove;

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;

    private Context context;

    public ScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // 第一步，创建Scroller的实例
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        Log.e("mTouchSlop",String.valueOf(mTouchSlop));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 为ScrollerLayout中的每一个子控件在水平方向上进行布局
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            // 初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
            Log.i("leftBorder",String.valueOf(leftBorder));
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //手指落下时的x坐标
                mXDown = ev.getRawX();
                //Log.e("XDown",String.valueOf(mXDown));
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                //手指移动时的x的坐标，随着移动变化
                mXMove = ev.getRawX();
                //Log.e("XMove",String.valueOf(mXMove));
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                //TouchSlop系统认为的滑动最小距离，不同的机型不同值，当小于这个值时，系统
                //不认为在滑动
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                //并将滑动事件交给OnTouchEvent处理
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //随着手指移动，X坐标
                mXMove = event.getRawX();
                //滑动的距离（滑动停下后就会重新从0开始计数）
                int scrolledX = (int) (mXLastMove - mXMove);
                //getScrollX()返回View的左边界相较于初始左边界的距离
                /**
                 * 当在第一个界面时，getScrollX=0，这时如果向右滑，
                 * scrolledx为负值（滑动前的位置减去滑动后的位置），
                 * 所以getScrollX+scrolledx为负值，leftBorder=0
                 * 所以不能滑动
                 */
                if (getScrollX() + scrolledX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                /**
                 * getSrcollX的值如果大于1/2的width，
                 * 那么targetIndex=1（int强转），
                 * 小于1/2的Width，那么targetIndex = 0;
                 */
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}