package bong.ginshell.com.myapplication.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * *Created by rqg on 4/6/16.
 */
public class StateIndicator extends View {
    private Paint mPaint;
    private RectF mBound = new RectF();
    private float mStrokeWidth = 10;
    private int mPathSize = 10;
    private List<Path> mPathList;
    private int mCurrentStep = 0;
    private ObjectAnimator mAnimator;

    private boolean isOpen = false;


    public StateIndicator(Context context) {
        this(context, null);
    }

    public StateIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAnimator();
    }

    private void initAnimator() {
        mAnimator = ObjectAnimator.ofInt(this, "animationStep", 0, mPathSize - 1);
        mAnimator.setDuration(200);
    }


    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.WHITE);
    }


    private void initBounds(int width, int height) {
        mBound.set(0, 0, width, height);


        mStrokeWidth = width * 0.05f;
        mPaint.setStrokeWidth(mStrokeWidth);
        mBound.inset(mStrokeWidth, mStrokeWidth);


        mPathList = animateArrow(mBound);

    }

    private List<Path> animateArrow(RectF bound) {

        float halfWidth = bound.width() / 2f;
        float lA = halfWidth * 0.1f;
        float lC = halfWidth * 0.7f;
        float lD = halfWidth * 0.85f;

        XY a = new XY();
        XY b = new XY();
        XY c = new XY();
        XY d = new XY();

        float arrowSize = halfWidth * 0.3f;


        c.x = halfWidth * 0.85f + mBound.left;
        c.y = halfWidth + mBound.top;

        a.x = c.x - arrowSize;
        a.y = c.y - arrowSize;
        b.x = c.x - arrowSize;
        b.y = c.y + arrowSize;


        d.x = 0f + mBound.left;
        d.y = halfWidth + mBound.top;


        List<Path> pathList = new ArrayList<>(mPathSize);

        float diffA = lA / (mPathSize - 1);
        float diffC = lC / (mPathSize - 1);
        float diffD = lD / (mPathSize - 1);

        for (int i = 0; i < mPathSize; i++) {


            Path path = new Path();
            path.moveTo(a.x, a.y);
            path.lineTo(c.x, c.y);
            path.lineTo(b.x, b.y);
            path.moveTo(c.x, c.y);
            path.lineTo(d.x, d.y);

            a.x -= diffA;
            b.x -= diffA;

            c.x -= diffC;

            d.x += diffD;

            pathList.add(path);

        }

        return pathList;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Path path = mPathList.get(mCurrentStep);
        canvas.save();
        float halfw = getWidth() / 2f;
        float halfh = getHeight() / 2f;

        canvas.rotate(-30, halfw, halfh);
        canvas.drawPath(path, mPaint);

        canvas.rotate(180, halfw, halfh);
        canvas.drawPath(path, mPaint);
        canvas.restore();

        canvas.drawOval(mBound, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumWidth(), heightMeasureSpec);

        int h = Math.min(width, height);

        width = h;
        height = h;

        initBounds(width, height);

        setMeasuredDimension(width, height);


    }

    private void openAnimation() {
        if (mAnimator.isRunning()) {
            mAnimator.end();
        }
        mAnimator.start();
    }

    private void closeAnimation() {
        mAnimator.reverse();
    }

    public void setState(boolean open) {
        if (open != isOpen) {
            isOpen = open;
            if (isOpen) {
                openAnimation();
            } else {
                closeAnimation();
            }
        }
    }

    public void setAnimationStep(int step) {
        if (step < 0 || step > mPathSize) {
            return;
        }
        mCurrentStep = step;
        invalidate();
    }

    public static class XY {
        public float x;
        public float y;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
