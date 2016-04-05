package bong.ginshell.com.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author rqg
 * @date 3/2/16.
 */
public class HeartSelectView extends View {
    public final static String TAG = HeartSelectView.class.getSimpleName();

    private Bitmap mLikeBitmap, mUnlikeBitmap;


    private Paint mPaint;
    private int mHeartSize = 10;

    private int mIndex = 0;

    private float mItemSize;
    private RectF mRectF1 = new RectF();
    private Rect mLikeRect = new Rect(), mUnlikeRect = new Rect();

    private float mMargin = 15f;

    public HeartSelectView(Context context) {
        super(context);
        init();
    }

    public HeartSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public HeartSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mLikeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart_like);
        mUnlikeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart_unlike);

        mLikeRect.set(0, 0, mLikeBitmap.getWidth(), mLikeBitmap.getHeight());
        mUnlikeRect.set(0, 0, mUnlikeBitmap.getWidth(), mUnlikeBitmap.getHeight());

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        mItemSize = width / mHeartSize;

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawHeart(canvas);
    }


    private void drawHeart(Canvas canvas) {

        float top = (getHeight() - mLikeBitmap.getHeight()) / 2f;
        float left = (mItemSize - mLikeBitmap.getWidth()) / 2f;
        for (int i = 0; i < mHeartSize; i++) {
            Log.d(TAG, "drawHeart top = " + top + ", left = " + left);
            if (i <= mIndex) {
                canvas.drawBitmap(mLikeBitmap, left, top, mPaint);
            } else {
                canvas.drawBitmap(mUnlikeBitmap, left, top, mPaint);
            }

            left += mItemSize;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();


        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_UP) {
            setSelect((int) (event.getX() / mItemSize));
        }
        return true;
    }

    public void setSelect(int index) {
        if (mIndex != index) {
            mIndex = index;
            invalidate();
        }
    }
}
