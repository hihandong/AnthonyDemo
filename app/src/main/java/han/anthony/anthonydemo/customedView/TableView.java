package han.anthony.anthonydemo.customedView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import han.anthony.anthonydemo.R;

/**
 * Created by senior on 2016/11/23.
 */

public class TableView extends View {
    private static final String TAG = "TableView";
    private final int mCoordinatesLineColor;
    private final int mCoordinatesTextColor;
    private final int mBrokenLineColor;
    private final int mBrokenLineWidth;
    private final int mCircleRadius;
    private final int mCircleColor;
    private int mBgColor;
    private int mCoordinatesTextSize;
    private int mCoordinatesLineWidth;
    private int mWidth;
    private int mHeight;
    private Paint coorPaint;
    private String[] mWeek = {"一", "二", "三", "四", "五", "六", "七"};
    private int[] mWeekValues = {23, 44, 155, 66, 44, 33, 199};
    private int xScale;
    private int yScale;
    private Paint textPaint;
    private Rect textBound;
    private int valueMin;
    private Paint littleCirclePaint;
    private Paint bigCirclePaint;
    private int numScale;
    private Paint brokenPanit;


    public TableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context) {
        this(context, null);
    }

    public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TableView, defStyleAttr,0);
        //坐标颜色和粗细
        mCoordinatesLineWidth = ta.getDimensionPixelSize(R.styleable.TableView_coordinatesLineWidth, 2);
        mCoordinatesLineColor=ta.getColor(R.styleable.TableView_coordinatesLineColor,0xff2b2b2b);
        //坐标上文字大小和颜色
        mCoordinatesTextSize = ta.getDimensionPixelSize(R.styleable.TableView_coordinatesTextSize, 40);
        mCoordinatesTextColor=ta.getColor(R.styleable.TableView_coordinatesTextColor,Color.GRAY);
        //折线颜色和粗细
        mBrokenLineColor=ta.getColor(R.styleable.TableView_brokenLineColor,Color.BLUE);
        mBrokenLineWidth=ta.getDimensionPixelSize(R.styleable.TableView_brokenLineWidth,2);
        //圆点大小和颜色
        mCircleRadius=ta.getDimensionPixelSize(R.styleable.TableView_circleRadius,10);
        mCircleColor=ta.getColor(R.styleable.TableView_circleColor,Color.GREEN);
        //背景颜色
        mBgColor = ta.getColor(R.styleable.TableView_bgColor, Color.WHITE);

        ta.recycle();
        init();
    }

    private void init() {
        coorPaint = new Paint();
        coorPaint.setStrokeWidth(mCoordinatesLineWidth);
        coorPaint.setColor(mCoordinatesLineColor);
        coorPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setTextSize(mCoordinatesTextSize);
        textPaint.setColor(mCoordinatesTextColor);
        textPaint.setAntiAlias(true);

        littleCirclePaint = new Paint();
        littleCirclePaint.setColor(mBgColor);
        littleCirclePaint.setAntiAlias(true);

        bigCirclePaint = new Paint();
        bigCirclePaint.setColor(mCircleColor);
        bigCirclePaint.setAntiAlias(true);

        //折线
        brokenPanit=new Paint();
        brokenPanit.setColor(mBrokenLineColor);
        brokenPanit.setStrokeWidth(mBrokenLineWidth);
        brokenPanit.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY) {
            mWidth = 300;
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            mHeight = 230;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDraw(canvas);
        drawCoordinates(canvas);
        drawValueX(canvas);
        drawValueY(canvas);
        drawCircle(canvas);
    }

    private void initDraw(Canvas canvas) {
        canvas.drawColor(mBgColor);
        textBound = new Rect();
        zeroY = mHeight - getPaddingBottom() - 20 - getWordsMaxHeight();
        zeroX = getPaddingLeft() + 30 + getWordsMaxWeight();
        xScale = (mWidth - zeroX ) / (mWeek.length+1);
        //除了x轴再画四条数值线
        yScale = zeroY  / 6;
    }

    //获得y坐标文字的最大宽度
    private int getWordsMaxWeight() {
        int maxWeight = 0;
        for (int num : mWeekValues) {
            textPaint.getTextBounds(num + "", 0, (num + "").length(), textBound);
            maxWeight = Math.max(maxWeight, textBound.width());
        }
        return maxWeight;
    }

    //获得X坐标文字的最大高度
    private int getWordsMaxHeight() {
        int maxHeight = 0;
        for (String text : mWeek) {
            textPaint.getTextBounds(text, 0, text.length(), textBound);
            maxHeight = Math.max(maxHeight, textBound.height());
        }
        return maxHeight;
    }

    private int zeroX;
    private int zeroY;

    //画坐标系
    private void drawCoordinates(Canvas canvas) {
        //坐标轴x
        canvas.drawLine(zeroX, zeroY, mWidth - getPaddingRight(), zeroY, coorPaint);
        //x轴箭头
        canvas.drawLine(mWidth - getPaddingRight() - 40, zeroY - 20, mWidth - getPaddingRight(), zeroY, coorPaint);
        canvas.drawLine(mWidth - getPaddingRight() - 40, zeroY + 20, mWidth - getPaddingRight(), zeroY, coorPaint);

        //坐标轴y和箭头
        canvas.drawLine(zeroX, zeroY, zeroX, getPaddingTop(), coorPaint);
        canvas.drawLine(zeroX - 20, getPaddingTop() + 40, zeroX, getPaddingTop(), coorPaint);
        canvas.drawLine(zeroX + 20, getPaddingTop() + 40, zeroX, getPaddingTop(), coorPaint);


    }

    //画X轴上的间断线和文字
    private void drawValueX(Canvas canvas) {
        int smallLineX = zeroX - xScale;
        for (int i = 0; i < mWeek.length; i++) {
            smallLineX += xScale;
            //x轴间断线
            canvas.drawLine(smallLineX, zeroY - 20, smallLineX, zeroY, coorPaint);
            //测量文字
            textPaint.getTextBounds(mWeek[i], 0, mWeek[i].length(), textBound);
            //下标文字
            canvas.drawText(mWeek[i], 0, mWeek[0].length(), smallLineX - textBound.width() / 2, mHeight-getPaddingBottom() - 10, textPaint);
        }
    }

    //画y轴数值线和文字
    private void drawValueY(Canvas canvas) {
        if (mWeekValues.length == 0) {
            return;
        }
        int max = mWeekValues[0];
        valueMin = mWeekValues[0];
        for (int num : mWeekValues) {
            max = Math.max(max, num);
            valueMin = Math.min(valueMin, num);
        }
        numScale = (max - valueMin) / 3;

        int tempY;
        int tempNum = valueMin - numScale;
        for (int i = 1; i < 5; i++) {
            tempY = zeroY - yScale * i;

            //画线
            canvas.drawLine(zeroX, tempY, mWidth - getPaddingRight(), tempY, coorPaint);
            tempNum += numScale;
            //测量文字
            String text = tempNum + "";
            textPaint.getTextBounds(text, 0, text.length(), textBound);
            //画文字
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            int baseLine = tempY + (int) ((fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent);
            canvas.drawText(text, 0, text.length(), zeroX - textBound.width() - 30, baseLine, textPaint);
        }
    }

    //画折线和小圆点
    private void drawCircle(Canvas canvas) {
        int circleX=zeroX-xScale;
        float circleY;

        int lastX=zeroX;
        float lastY=getY(mWeekValues[0]);
        //画折线
        for(int i=1;i<mWeekValues.length;i++){
            canvas.drawLine(lastX,lastY,lastX+=xScale,lastY=getY(mWeekValues[i]),brokenPanit);
        }


        for(int i=0;i<mWeekValues.length;i++){
            circleX+=xScale;
            circleY=getY(mWeekValues[i]);
            //画大圆
            canvas.drawCircle(circleX,circleY,mCircleRadius+5,bigCirclePaint);
            //画小圆
            canvas.drawCircle(circleX,circleY,mCircleRadius,littleCirclePaint);
        }

    }
    //根据数值获得y坐标
    private float getY(float num){
        return zeroY-(yScale+(float)yScale/numScale*(num-valueMin));
    }
}
