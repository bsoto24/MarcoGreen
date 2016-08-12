package doapps.marcogreen.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import doapps.marcogreen.R;

/**
 * Created by Bryam Soto on 11/08/2016.
 */
public class WaterProgress extends View{
    private int score;
    private int progress;
    private Paint circlePaint;
    private static final int MSG_WAVE = 10;
    private int radius = dpToPx(100);
    private int stroke = dpToPx(6);
    private Paint paintInnerCircle;
    private Paint paintWater;
    private int centerXY;
    private Path path = new Path();
    private float mOffsetX = 0;
    private double mWaveHeight = 8;
    private boolean isWaving = true;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mOffsetX = mOffsetX + 3;
            if (mOffsetX > 10000) {
                mOffsetX = 0;
            }
            invalidate();
        }
    };

    public WaterProgress(Context context) {
        this(context, null);
    }

    public WaterProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        init();
    }

    private void init() {

        /*BORDES*/
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStrokeWidth(10);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);

    }

    private void initView(Context cxt, AttributeSet attrs) {
        TypedArray ta = cxt.obtainStyledAttributes(attrs, R.styleable.WaveViewStyle);
        int waveColor = ta.getColor(R.styleable.WaveViewStyle_waveColor, 0x99000000);
        int innerBgColor = ta.getColor(R.styleable.WaveViewStyle_innerBgColor, 0x20FFFFFF);
        ta.recycle();

        paintInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintInnerCircle.setDither(true);
        paintInnerCircle.setColor(innerBgColor);
        paintInnerCircle.setStyle(Paint.Style.FILL);

        paintWater = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintWater.setDither(true);
        paintWater.setColor(waveColor);
        paintWater.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width, height;

        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = radius * 2 + stroke * 2;
        }

        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = radius * 2 + stroke * 2;
        }

        int size = Math.min(width, height);
        setMeasuredDimension(size, size);

        centerXY = size / 2;

        if (centerXY <= radius + stroke) {
            radius = centerXY - stroke;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int radius = getWidth() / 2 - 10;

        canvas.drawCircle(centerXY, centerXY, radius, circlePaint);
        canvas.drawCircle(centerXY, centerXY, this.radius, paintInnerCircle);
        createWavePath(progress);
        canvas.drawPath(path, paintWater);
        if (isWaving) {
            handler.sendEmptyMessageDelayed(MSG_WAVE, 10);
        }
    }

    private void createWavePath(int progress) {
        path.reset();
        float absY;
        float sweepAngle;
        float startAngle;
        float absX;
        if (progress >= 50) {
            absY = radius * 2 * (progress * 1.0f / 100);
            float angle = (float) (Math.asin((absY - radius) * 1.0f / radius) * 180 / Math.PI);
            absX = (float) (radius * Math.cos(angle * Math.PI / 180));
            sweepAngle = angle * 2 + 180;
            startAngle = -angle;
        } else {
            absY = radius * 2 * (progress * 1.0f / 100);
            float angle = (float) (Math.acos((radius - absY) * 1.0f / radius) * 180 / Math.PI);
            absX = (float) (radius * Math.sin(angle * Math.PI / 180));
            sweepAngle = angle * 2;
            startAngle = 90 - angle;
        }

        int startX = (int) (radius - absX) + stroke;

        float x, y;
        for (int i = 0; i < absX * 2; i++) {
            x = i + startX;
            y = (float) (mWaveHeight * Math.sin((i * 1.5f + mOffsetX) / radius * Math.PI)) + (radius * 2
                    - absY) + stroke;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.quadTo(x, y, x + 1, y);
            }
        }
        RectF rectF = new RectF(stroke, stroke, radius * 2 + stroke,
                radius * 2 + stroke);
        path.arcTo(rectF, startAngle, sweepAngle);
        path.close();
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setScore(int score) {

        this.score = score;
        invalidate();
    }
}
