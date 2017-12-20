package com.lgc.garylianglib.util.cusview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.lgc.garylianglib.util.L;

import java.util.LinkedList;
import java.util.List;


/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/10/17 21:14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SwipeBackLayout extends FrameLayout {
    private static final String TAG = SwipeBackLayout.class.getSimpleName();
    private View mContentView;
    private int mTouchSlop;
    private int downX;
    private int downY;
    private int tempX;
    private int tempY;
    private Scroller mScroller;
    private int viewWidth;
    private int viewHeight;
    private boolean isSilding;
    private boolean isFinish;
    private boolean state = false;
    //	private Drawable mShadowDrawable;
    private Activity mActivity;
    private List<ViewPager> mViewPagers = new LinkedList<ViewPager>();


    private List<HorizontalScrollView> mHorizontalScrollViews = new LinkedList<HorizontalScrollView>();
    private List<ScrollView> mScrollViews = new LinkedList<ScrollView>();

    private int type = 0;

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);

//		mShadowDrawable = getResources().getDrawable(R.drawable.shadow_left);
    }

    public void setViewType(int _type) {
        this.type = _type;
    }

    public void attachToActivity(Activity activity) {
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    private void setContentView(View decorChild) {
        mContentView = (View) decorChild.getParent();
    }

    /**
     * 事件拦截操作
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //处理ViewPager冲突问题
        ViewPager mViewPager = getTouchViewPager(mViewPagers, ev);
//        LinearLayout mHorizontalScrollView = getTouchHorizontalScrollView(mHorizontalScrollViews, ev);
//        ScrollView mScrollView = getTouchScrollView(mScrollViews, ev);
//        RecyclerView mRecyclerView = getTouchRecyclerView(mRecyclerViews, ev);
        HorizontalScrollView mHorizontalScrollView = getTouchHorizontalScrollView(mHorizontalScrollViews, ev);
        if (mViewPager != null && mViewPager.getCurrentItem() != 0) {
            return super.onInterceptTouchEvent(ev);
        }
        if (mHorizontalScrollView!=null){
            if (mHorizontalScrollView.getScrollX()!=0){
                return super.onInterceptTouchEvent(ev);
            }
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = tempX = (int) ev.getRawX();
                downY = tempY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                // 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
                if (moveX - downX > mTouchSlop
                        && Math.abs((int) ev.getRawY() - downY) < mTouchSlop) {
                    state = false;
                    return true;
                }
                if (type != 0) {
                    int moveY = (int) ev.getRawY();
                    if (moveY - downY > mTouchSlop
                            && Math.abs((int) ev.getRawY() - downY) > mTouchSlop) {
                        state = true;
                        return true;
                    }
                }

                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = tempX - moveX;
                tempX = moveX;
                int moveY = (int) event.getRawY();
                int deltaY = tempY - moveY;
                tempY = moveY;
                if (!state) {
                    if (moveX - downX > mTouchSlop
                            && Math.abs((int) event.getRawY() - downY) < mTouchSlop) {
                        isSilding = true;
                    }
                    if (moveX - downX >= 0 && isSilding) {
                        mContentView.scrollBy(deltaX, 0);
                    }
                } else {
                    if (moveY - downY > mTouchSlop
                            && Math.abs((int) event.getRawY() - downY) > mTouchSlop) {
                        isSilding = true;
                    }
                    if (moveY - downY >= 0 && isSilding) {
                        mContentView.scrollBy(0, deltaY);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                isSilding = false;
                if (!state) {
                    if (mContentView.getScrollX() <= -viewWidth / 2 / 2) {
                        isFinish = true;
                        scrollRight();
                    } else {
                        scrollOrigin();
                        isFinish = false;
                    }
                } else {
                    if (mContentView.getScrollY() <= -viewHeight / 2 / 2) {
                        isFinish = true;
                        scrollBottom();
                    } else {
                        scrollOrigin2();
                        isFinish = false;
                    }
                }


                break;
        }

        return true;
    }

    /**
     * 获取SwipeBackLayout里面的ViewPager的集合
     *
     * @param mViewPagers
     * @param parent
     */
    private void getAlLViewPager(List<ViewPager> mViewPagers, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewPager) {
                mViewPagers.add((ViewPager) child);
            } else if (child instanceof ViewGroup) {
                getAlLViewPager(mViewPagers, (ViewGroup) child);
            }
        }
    }


    /**
     * 返回我们touch的ViewPager
     *
     * @param mViewPagers
     * @param ev
     * @return
     */
    private ViewPager getTouchViewPager(List<ViewPager> mViewPagers, MotionEvent ev) {
        if (mViewPagers == null || mViewPagers.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (ViewPager v : mViewPagers) {
            v.getHitRect(mRect);

            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }

    /**
     * 获取SwipeBackLayout里面的ViewPager的集合
     *
     * @param mViewPagers
     * @param parent
     */
    private void getAlLHorizontalScrollView(List<HorizontalScrollView> mViewPagers, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof HorizontalScrollView) {
                L.e("xx", "HorizontalScrollView....");
                mViewPagers.add((HorizontalScrollView) child);
            } else if (child instanceof ViewGroup) {
                L.e("xx", "ViewGroup....");
                getAlLHorizontalScrollView(mHorizontalScrollViews, (ViewGroup) child);
            }
        }
    }


    /**
     * 返回我们touch的ViewPager
     *
     * @param mViewPagers
     * @param ev
     * @return
     */
    private LinearLayout getTouchLinearLayoutView(List<HorizontalScrollView> mViewPagers, MotionEvent ev) {

        if (mViewPagers == null || mViewPagers.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (HorizontalScrollView v : mViewPagers) {
            LinearLayout ll = (LinearLayout) v.getChildAt(0);
            ll.getHitRect(mRect);
            L.e("xx", "mRect..left" + mRect.left + " top  " + mRect.top+ "  right" + mRect.right+ "  bottom" + mRect.bottom);
            L.e("xx", "ll...." + (int) ev.getX() + "  " + (int) ev.getY());

            int left=mRect.left;
            int top=mRect.top;
            int right=mRect.right;
            int bottom=mRect.bottom*2+50;

            if(left < right && top < bottom  // check for empty first
                    && (int) ev.getX() >= left && (int) ev.getX() < right && (int) ev.getY() >= top&& (int) ev.getY() < bottom){
                L.e("xx", "返回...." );
                return ll;
            }
        }
        return null;
    }
    private HorizontalScrollView getTouchHorizontalScrollView(List<HorizontalScrollView> mViewPagers, MotionEvent ev) {

        if (mViewPagers == null || mViewPagers.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (HorizontalScrollView v : mViewPagers) {
            v.getHitRect(mRect);
            L.e("xx", "mRect..left" + mRect.left + " top  " + mRect.top+ "  right" + mRect.right+ "  bottom" + mRect.bottom);
            L.e("xx", "ll...." + (int) ev.getX() + "  " + (int) ev.getY());

            int left=mRect.left;
            int top=mRect.top;
            int right=mRect.right;
            int bottom=mRect.bottom*2+50;
            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                L.e("xx", "返回1...." );
                return v;
            }
            if(left < right && top < bottom  // check for empty first
                    && (int) ev.getX() >= left && (int) ev.getX() < right && (int) ev.getY() >= top&& (int) ev.getY() < bottom){
                L.e("xx", "返回.2..." );
                return v;
            }
        }
        return null;
    }


    /**
     * 获取SwipeBackLayout里面的ViewPager的集合
     *
     * @param mViewPagers
     * @param parent
     */
    private void getAlLScrollView(List<ScrollView> mViewPagers, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ScrollView) {
                mViewPagers.add((ScrollView) child);
            } else if (child instanceof ViewGroup) {
                getAlLScrollView(mScrollViews, (ViewGroup) child);
            }
        }
    }


    /**
     * 返回我们touch的ViewPager
     *
     * @param mViewPagers
     * @param ev
     * @return
     */
    private ScrollView getTouchScrollView(List<ScrollView> mViewPagers, MotionEvent ev) {
        if (mViewPagers == null || mViewPagers.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (ScrollView v : mViewPagers) {
            v.getHitRect(mRect);

            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            viewWidth = this.getWidth();
            viewHeight = this.getHeight();
            getAlLViewPager(mViewPagers, this);
            getAlLHorizontalScrollView(mHorizontalScrollViews, this);
//            getAlLScrollView(mScrollViews, this);
//            getAlLRecyclerView(mRecyclerViews, this);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

    }


    /**
     * 滚动出界面
     */
    private void scrollRight() {
        final int delta = (viewWidth + mContentView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0,
                Math.abs(delta));
        postInvalidate();
    }

    private void scrollBottom() {
        final int delta = (viewHeight + mContentView.getScrollY());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(0, mContentView.getScrollY(), 0, -delta + 1,
                Math.abs(delta));
        postInvalidate();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int delta = mContentView.getScrollX();
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate();
    }

    private void scrollOrigin2() {
        int delta = mContentView.getScrollY();
        mScroller.startScroll(0, mContentView.getScrollY(), 0, -delta,
                Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (mScroller.computeScrollOffset()) {
            mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished() && isFinish) {
                mActivity.finish();
            }
        }
    }


}
