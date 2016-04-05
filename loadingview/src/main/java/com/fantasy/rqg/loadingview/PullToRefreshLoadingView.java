package com.fantasy.rqg.loadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import java.util.concurrent.TimeUnit;


/**
 * @author rqg
 * @date 1/12/16.
 */
public class PullToRefreshLoadingView extends View {
    public static final String TAG = PullToRefreshLoadingView.class.getSimpleName();

    private Paint mCirclePaint, mCenterPaint;

    private int mColor = Color.WHITE;
    private int mStrokeWidth = 10;

    private boolean isLoading = false;

    private RectF mBounds = new RectF();

    private Float mRotation = 0f;

    private ValueAnimator mAnimator;

    private String mCenterChar;

    private String mUp, mDown, mDot1, mDot2, mDot3;

    public PullToRefreshLoadingView(Context context) {
        this(context, null);
    }

    public PullToRefreshLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mUp = getContext().getString(R.string.icon_arrow_up);
        mDown = getContext().getString(R.string.icon_arrow_down);
        mDot1 = getContext().getString(R.string.icon_dot_1);
        mDot2 = getContext().getString(R.string.icon_dot_2);
        mDot3 = getContext().getString(R.string.icon_dot_3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumWidth(), heightMeasureSpec);

        int size = Math.min(width, height);
        mBounds.set(mStrokeWidth, mStrokeWidth, size - mStrokeWidth, size - mStrokeWidth);


        initPaint();

        setMeasuredDimension(size, size);
    }

    private void initPaint() {
        mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterPaint.setColor(mColor);
        Typeface tf = Typeface.createFromAsset(getResources().getAssets(), "icomoon.ttf");
        mCenterPaint.setTypeface(tf);
        mCenterPaint.setTextAlign(Paint.Align.CENTER);
        mCenterPaint.setTextSize(mBounds.width() / 2f);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mStrokeWidth);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        SweepGradient gradient = new SweepGradient(mBounds.right / 2f, mBounds.bottom / 2f, getColors(mColor), new float[]{0f, 0.98f});

        mCirclePaint.setShader(gradient);

        enableLoading(true);
    }


    private int[] getColors(int c) {
        int[] result = new int[2];
        int alpha = Color.alpha(c);
        int red = Color.red(c);
        int green = Color.green(c);
        int blue = Color.blue(c);
        result[0] = c;
        result[1] = Color.argb((int) (alpha * 0.0f), red, green, blue);

        return result;
    }

    public void enableLoading(boolean enableLoading) {
        isLoading = enableLoading;
        if (isLoading) {
            startLoadingAnimation();
        } else {

        }
    }


    private void startLoadingAnimation() {


        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mCenterChar = mDot1;

        mAnimator = ValueAnimator.ofFloat(0f, 360f);
        mAnimator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float v = (Float) animation.getAnimatedValue();

                updateDot(mRotation, v);

                mRotation = v;

                invalidate();
            }
        });
        mAnimator.setDuration(TimeUnit.SECONDS.toMillis(2));
        mAnimator.setRepeatMode(ValueAnimator.INFINITE);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
//        post(new RefreshRunnable(this));

    }

    private void updateDot(Float last, Float current) {
        if ((last > 350f || last <= 0f) && current > 0f) {
            mCenterChar = mDot1;
        } else if (last <= 120f && current > 120f) {
            mCenterChar = mDot2;
        } else if (last < 240f && current > 240f) {
            mCenterChar = mDot3;
        }
    }


    private void drawCenter(Canvas canvas) {
        String cc = mCenterChar;
        if (cc != null) {
            canvas.drawText(cc, getWidth() / 2f, (getHeight() + mCenterPaint.getTextSize()) / 2f, mCenterPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(-90f + mRotation, getWidth() / 2f, getHeight() / 2f);
        canvas.drawArc(mBounds, 0, 350, false, mCirclePaint);
        canvas.restore();
        drawCenter(canvas);
    }
}
