package bong.ginshell.com.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author rqg
 * @date 1/26/16.
 */
public class PieProgressView extends View {
    //    public static final String TAG = PieProgressView.class.getSimpleName();
    private RectF mBounds = new RectF();
    private int mStrokeWidth = 5;
    private float mProgress;

    private Paint mCirclePaint, mPiePaint;
    private int mColor;

    public PieProgressView(Context context) {
        super(context);
    }

    public PieProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumWidth(), heightMeasureSpec);

        int size = Math.min(width, height);

        mStrokeWidth = (int) (size * 0.05f);
        mBounds.set(mStrokeWidth, mStrokeWidth, size - mStrokeWidth, size - mStrokeWidth);


        initPaint();

        setMeasuredDimension(size, size);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mBounds, 0, 360, true, mCirclePaint);

        canvas.drawArc(mBounds, -90, 360 * mProgress, true, mPiePaint);
    }

    private void initPaint() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mStrokeWidth);


        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setColor(mColor);
        mPiePaint.setStyle(Paint.Style.FILL);
    }


    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {

        if (progress < 0f) {
            progress = 0f;
        } else if (progress > 1f) {
            progress = 1f;
        }

        mProgress = progress;

        invalidate();
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
        if (mCirclePaint != null && mPiePaint != null) {
            mCirclePaint.setColor(mColor);
            mPiePaint.setColor(mColor);
        }
    }
}
