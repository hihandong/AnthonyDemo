package han.anthony.anthonydemo.customedView;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import han.anthony.anthonydemo.R;
import han.anthony.anthonydemo.activity_main.myLogger.L;

/**
 * Created by senior on 2016/11/4.
 */

public class ColorTrackTabLayout extends LinearLayout {
    private Context mContext;
    private ColorTrackTab mCurTab;
    public static final int TAB_DIRECTION_LEFT = 0;
    public static final int TAB_DIRECTION_RIGHT = 1;
    /**
     * 可以在XML设置的属性
     */
    private int mTextSize = 60;
    private int mChangeColor = 0xffff0000;
    private int mOriginColor = 0xff000000;


    private ViewPager mViewPager;
    //mCurrentVpSelectedListener
    private OnTabSelectedListener mCurTabSelectedListener;
    private List<OnTabSelectedListener> mTabSelctedListeners=new ArrayList<>();
    //ViewPager的两个回调
    private ViewPager.OnAdapterChangeListener mAdapterChangeListener;
    private ViewPager.OnPageChangeListener mPageChangeListener;

    private PagerAdapter mPagerAdapter;
    private DataSetObserver mPagerAdapterObserver;
    //用来保存tab view
    private List<ColorTrackTab> mTabs = new ArrayList<>();


    public ColorTrackTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.ColorTrackTabLayout);
        mTextSize=ta.getDimensionPixelSize(R.styleable.ColorTrackTabLayout_text_size,60);
        mOriginColor=ta.getColor(R.styleable.ColorTrackTabLayout_origin_color,0xff000000);
        mChangeColor=ta.getColor(R.styleable.ColorTrackTabLayout_change_color,0xffff0000);
        ta.recycle();
    }

    /**
     *更新Tab view ,当PagerAdapter数据变化时和初始化时会调用
     */
    private void populateTabs() {
        this.removeAllViews();
        //TODO
        mTabs.clear();
        if (mPagerAdapter != null) {
            int count = mPagerAdapter.getCount();
            for (int i = 0; i < count; i++) {
                ColorTrackTab tab = new ColorTrackTab(mContext, (String) mPagerAdapter.getPageTitle(i));
                tab.setPosition(i);
                this.addView(tab);
                mTabs.add(tab);
            }
        }
    }

    /**
     * ColorTrackTabLayout 和 ViewPager绑定
     * @param viewPager
     */
    public void setupWithViewPager(ViewPager viewPager) {
        //之前已经设置过ViewPager,移除之前注册在ViewPager上的回调
        if (mViewPager != null) {
            if (mAdapterChangeListener != null) {
                mViewPager.removeOnAdapterChangeListener(mAdapterChangeListener);
            }
            if (mPageChangeListener != null) {
                mViewPager.removeOnPageChangeListener(mPageChangeListener);
            }
        }
        //向新的ViewPager注册回调
        if (viewPager != null) {
            mViewPager = viewPager;

            // Add our custom OnPageChangeListener to the ViewPager
            if (mPageChangeListener == null) {
                mPageChangeListener = new MyOnPageChangeListener(this);
            }
            viewPager.addOnPageChangeListener(mPageChangeListener);
            //点击tab时显示当前的ViewPager页面
            if(mCurTabSelectedListener==null){
                mCurTabSelectedListener = new MyOnTabSelectedListener(viewPager);
                mTabSelctedListeners.add(mCurTabSelectedListener);
            }

            // Add a listener so that we're notified of any adapter changes
            if (mAdapterChangeListener == null) {
                mAdapterChangeListener = new AdapterChangeListener();
            }
            viewPager.addOnAdapterChangeListener(mAdapterChangeListener);
            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                // Now we'll populate ourselves from the pager adapter, adding an observer
                setPagerAdapter(adapter);
            }
        }


    }

    /**
     * 向PagerAdapter设置观察者,当数据发生变化时会回调
     */
    private void setPagerAdapter(PagerAdapter adapter) {
        if (mPagerAdapter != null && mPagerAdapterObserver != null) {
            // If we already have a PagerAdapter, unregister our observer
            mPagerAdapter.unregisterDataSetObserver(mPagerAdapterObserver);
        }

        mPagerAdapter = adapter;

        if (adapter != null) {
            // Register our observer on the new adapter
            if (mPagerAdapterObserver == null) {
                mPagerAdapterObserver = new PagerAdapterObserver();
            }
            adapter.registerDataSetObserver(mPagerAdapterObserver);
        }

        // Finally make sure we reflect the new adapter
        populateTabs();
    }

    public void addOnTabSelectedListener(OnTabSelectedListener listener){
        mTabSelctedListeners.add(listener);
    }
    /**
     *
     */
    public interface OnTabSelectedListener{
        void onTabSelected(ColorTrackTab tab);
    }

    /**
     * 当ViewPager的Adapter更换时,设置新的Adapter
     */
    private class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {

        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
            if (viewPager == mViewPager) {
                setPagerAdapter(newAdapter);
            }
        }
    }

    /**
     *当Adapter数据发生变化时回调
     */
    private class PagerAdapterObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            L.e("onChanged");
        }
    }

    /**
     *当点击Tab view时回调
     */
    public class MyOnTabSelectedListener implements ColorTrackTabLayout.OnTabSelectedListener {
        private ViewPager mViewPager;
        public MyOnTabSelectedListener(ViewPager viewPager){
            mViewPager=viewPager;
        }

        @Override
        public void onTabSelected(ColorTrackTab tab) {
            //设置点击tab view对应的ViewPager页面
            mViewPager.setCurrentItem(tab.getPosition(),false);
            for(ColorTrackTab t:mTabs){
                if(t!=tab){
                    t.setProgress(0);
                    t.invalidate();
                }
            }
        }
    }

    /**
     * ViewPager滑动等情况时的回调
     * 模仿谷歌官方的TabLayout声明了一个弱引用,但是在本例中并没有实际使用
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private WeakReference<ColorTrackTabLayout> mColorTrackLayoutRef;

        public MyOnPageChangeListener(ColorTrackTabLayout colorTrackTabLayout) {
            mColorTrackLayoutRef = new WeakReference<>(colorTrackTabLayout);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(mTabs.size()<2){
                return;
            }
            ColorTrackTab currentTab = mTabs.get(position);
            if (positionOffset != 0) {
                mCurTab=currentTab;
                ColorTrackTab  nextTab = mTabs.get(position+1);
                //更新Tab view的颜色
                nextTab.setProgress(positionOffset);
                nextTab.setDirection(TAB_DIRECTION_LEFT);
                nextTab.invalidate();
            }
            //更新Tab view的颜色
            currentTab.setProgress(1-positionOffset);
            currentTab.setDirection(TAB_DIRECTION_RIGHT);
            currentTab.invalidate();

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    /**
     * Tab View
     */
    class ColorTrackTab extends View {
        private int mTextWidth;
        private Paint mPaint;
        private int mTextHeight;
        private int mDirection = TAB_DIRECTION_LEFT;
        //y坐标
        private int mDrawTextY;
        private int mTextStartX;
        private float mProgress;
        private String mText="Default";
        private int mPosition;


        /**
         * 文字可以变色的Tab
         */
        public ColorTrackTab(Context context, String text) {
            super(context);
            mText = text;
            mPaint = new Paint();
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
            lp.gravity= Gravity.CENTER;
            setLayoutParams(lp);
            /**
             * 设置回调
             */
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(OnTabSelectedListener listener:mTabSelctedListeners){
                        listener.onTabSelected(ColorTrackTab.this);
                    }
                }
            });
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            measureText();
            int width = widthMeasure(widthMeasureSpec);
            int height = heightMeasure(heightMeasureSpec);
            setMeasuredDimension(width, height);
            mTextStartX = (int) (0.5 * width - 0.5 * mTextWidth);
        }

        private void measureText() {
            mPaint.setTextSize(mTextSize);
            mTextWidth = (int) mPaint.measureText(mText);
            Paint.FontMetrics a = mPaint.getFontMetrics();
            mTextHeight = (int) (a.bottom - a.top);

        }


        private int widthMeasure(int widthMeasureSpec) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            int measureSize = MeasureSpec.getSize(widthMeasureSpec);
            int result;
            switch (measureMode) {
                case MeasureSpec.EXACTLY:
                    result = measureSize;
                    break;
                default:
                    result = mTextWidth + getPaddingRight() + getPaddingLeft();

            }
            return result;
        }

        private int heightMeasure(int widthMeasureSpec) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            int measureSize = MeasureSpec.getSize(widthMeasureSpec);
            int result;
            switch (measureMode) {
                case MeasureSpec.EXACTLY:
                    result = measureSize;
                    break;
                default:
                    result = mTextHeight + getPaddingTop() + getPaddingBottom();

            }
            return result;
        }

        /**
         * ------onDraw
         */
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //文字的绘制区域,Y坐标
            mDrawTextY = (int) (getMeasuredHeight() / 2 - ((mPaint.descent() + mPaint.ascent()) / 2));
            if (mDirection == TAB_DIRECTION_LEFT) {
                drawOriginLeft(canvas);
                drawChangeLeft(canvas);
            } else {
                drawOriginRight(canvas);
                drawChangeRight(canvas);
            }
        }

        private void drawChangeLeft(Canvas canvas) {
            drawText(canvas, mChangeColor, mTextStartX, (int) (mTextStartX + mTextWidth * mProgress));
        }

        private void drawOriginLeft(Canvas canvas) {
            drawText(canvas, mOriginColor, (int) (mTextStartX + mTextWidth * mProgress), mTextStartX + mTextWidth);
        }

        /**
         *
         */
        private void drawChangeRight(Canvas canvas) {
            drawText(canvas, mChangeColor, (int) (mTextStartX + mTextWidth * (1-mProgress)),mTextStartX+mTextWidth);
        }

        private void drawOriginRight(Canvas canvas) {
            drawText(canvas, mOriginColor, mTextStartX,(int) (mTextStartX + mTextWidth * (1-mProgress)));
        }


        /**
         * 核心代码!!通过clipRect设置显示的区域,实现颜色的变化
         */
        private void drawText(Canvas canvas, int color, int startX, int endX) {
            canvas.save(Canvas.CLIP_SAVE_FLAG);
            mPaint.setColor(color);
            canvas.clipRect(startX, 0, endX, getMeasuredHeight());
            canvas.drawText(mText, mTextStartX, mDrawTextY, mPaint);
            canvas.restore();
        }

        public void setProgress(float progress) {
            mProgress = progress;
            //invalidate();
        }
        public void setDirection(int direction){
            mDirection=direction;
        }
        public void setPosition(int position){
            mPosition=position;
        }

        public int getPosition() {
            return mPosition;
        }
    }


}
