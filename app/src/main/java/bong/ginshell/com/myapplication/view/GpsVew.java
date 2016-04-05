package bong.ginshell.com.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * *Created by rqg on 4/5/16.
 */
public class GpsVew extends View {

    private Paint mBackgroundPaint, mStrokePaint, mSignalPaint, mTextPaint;

    private RectF mBound = new RectF();
    private RectF[] mGpsBounds = new RectF[]{new RectF(), new RectF(), new RectF()};
    private Rect mTextBound = new Rect();

    private static final String GPS = "GPS";
    private float mTextX, mTextY, mBoundRadius;
    private int mSignal = 0;
    private float mGpsWidth = 10f;


    public GpsVew(Context context) {
        this(context, null);
    }

    public GpsVew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GpsVew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }


    private void initPaint() {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(Color.parseColor("#3C000000"));
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setColor(Color.parseColor("#7FFFFFFF"));
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(3f);

        mSignalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSignalPaint.setColor(Color.RED);
        mSignalPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#CCFFFFFF"));
        mTextPaint.setTextAlign(Paint.Align.LEFT);

    }


    private void initBounds(int width, int height) {
        float strokeWidth = height * 0.04f;

        mBound = new RectF();
        mBound.set(0, 0, width, height);
        mBound.inset(strokeWidth, strokeWidth);

        mTextPaint.setTextSize(height * 0.7f);

        mBoundRadius = height / 2f;
        mTextX = mBoundRadius / 3f;

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

        mTextY = mBoundRadius - ((fontMetrics.descent + fontMetrics.ascent) / 2);

        float gpsInterval = height * 0.1f;
        mGpsWidth = height * 0.05f;
        mStrokePaint.setStrokeWidth(strokeWidth);

        mTextPaint.getTextBounds(GPS, 0, GPS.length(), mTextBound);

        float gpsX = mBound.left + mBound.width() - mBoundRadius - 3 * (mGpsWidth + gpsInterval);

        float textBottom = mTextY + fontMetrics.bottom - fontMetrics.descent;

        gpsX += gpsInterval;
        mGpsBounds[0].set(gpsX, textBottom - mTextBound.height() * 0.3f, gpsX + mGpsWidth, textBottom);
        gpsX += gpsInterval + mGpsWidth;
        mGpsBounds[1].set(gpsX, textBottom - mTextBound.height() * 0.6f, gpsX + mGpsWidth, textBottom);
        gpsX += gpsInterval + mGpsWidth;
        mGpsBounds[2].set(gpsX, textBottom - mTextBound.height() * 1f, gpsX + mGpsWidth, textBottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawRoundRect(mBound, mBoundRadius, mBoundRadius, mBackgroundPaint);
        canvas.drawRoundRect(mBound, mBoundRadius, mBoundRadius, mStrokePaint);

        canvas.drawText(GPS, mTextX, mTextY, mTextPaint);


        float halfGpsWidth = mGpsWidth / 2f;
        for (int i = 0; i < mGpsBounds.length; i++) {
            if (i < mSignal) {
                canvas.drawRoundRect(mGpsBounds[i], halfGpsWidth, halfGpsWidth, mSignalPaint);
            } else {
                canvas.drawRoundRect(mGpsBounds[i], halfGpsWidth, halfGpsWidth, mTextPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumWidth(), heightMeasureSpec);

        float radio = 2.8f;
        float h = width / radio;
        h = Math.max(h, height);

        width = (int) (h * radio);
        height = (int) h;

        initBounds(width, height);

        setMeasuredDimension(width, height);
    }
}
