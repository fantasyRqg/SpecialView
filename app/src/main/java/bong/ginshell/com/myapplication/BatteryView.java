package bong.ginshell.com.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author rqg
 * @date 1/29/16.
 */
public class BatteryView extends View {
    public final static String TAG = BatteryView.class.getSimpleName();
    private int mWidth, mHeight;
    private float mProgress = 1f;
    private Paint mBatteryPaint, mUseagePaint;
    private RectF mBodyBounds = new RectF(), mHeadBounds = new RectF(), mUseBounds = new RectF(), tmp = new RectF();
    private float mStrokeWidth = 2;
    private int mBatteryColor = Color.WHITE, mProgressColor = Color.GREEN;

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumWidth(), heightMeasureSpec);

        float perW = width / 2.2f;
        float perH = height / 1f;
        float per = Math.min(perH, perW);
        width = (int) (per * 2.2f);
        height = (int) (per * 1f);


        mWidth = width;
        mHeight = height;

        float padding = mStrokeWidth / 2f;
        mBodyBounds.set(padding, padding, mWidth * 0.9f - padding, mHeight - padding);
        mHeadBounds.set(mWidth * 0.8f - 1, mHeight * 0.2f, mWidth, mHeight * 0.8f);

        mUseBounds.set(mBodyBounds.left + padding + 1, mBodyBounds.top + padding + 1, mBodyBounds.right - padding - 1, mBodyBounds.bottom - padding - 1);
        initPaint();

        setMeasuredDimension(width, height);
    }

    private void initPaint() {
        mBatteryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBatteryPaint.setStrokeWidth(mStrokeWidth);
        mBatteryPaint.setColor(mBatteryColor);

        mUseagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUseagePaint.setStyle(Paint.Style.FILL);
        mUseagePaint.setColor(mProgressColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBatteryPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mBodyBounds, mBatteryPaint);

        mBatteryPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(mHeadBounds, -90, 180, true, mBatteryPaint);

        tmp.set(mUseBounds);
        float len = mUseBounds.width() * mProgress;
        tmp.right = tmp.left + (len <= 1.5f ? 1.5f : len);
        canvas.drawRect(tmp, mUseagePaint);
    }


    public int getProgressColor() {
        return mProgressColor;
    }

    public void setProgressColor(int mProgressColor) {
        this.mProgressColor = mProgressColor;

    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }
}
