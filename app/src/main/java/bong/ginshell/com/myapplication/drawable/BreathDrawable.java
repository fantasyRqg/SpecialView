package bong.ginshell.com.myapplication.drawable;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.StateSet;

import java.util.ArrayList;
import java.util.List;

public class BreathDrawable extends Drawable {

    private int mCurrentFrame = 0;

    private boolean isPressed = false;
    private RectF mInBounds = new RectF(), mOutBounds = new RectF();
    private static final int ANIMATION_STEP_COUNT = 50;
    private List<Shader> mShaderList = new ArrayList<>(ANIMATION_STEP_COUNT);
    private int[] mBreathColors = new int[]{Color.TRANSPARENT, Color.WHITE, Color.TRANSPARENT};

    private float mBoundFactor = 0.4f;

    private Paint mCenterPaint, mWavePaint;

    private ObjectAnimator mAnimator;

    public BreathDrawable() {
        mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterPaint.setColor(Color.GREEN);
        mCenterPaint.setStyle(Paint.Style.FILL);

        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        return super.setVisible(visible, restart);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        initBounds(bounds);
    }


    private void initBounds(Rect bounds) {
        mOutBounds.set(bounds);
        mOutBounds.inset(0.5f, 0.5f);
        mInBounds.set(mOutBounds);
        mInBounds.inset(mOutBounds.width() * mBoundFactor, mOutBounds.height() * mBoundFactor);

        initShader(mInBounds, mOutBounds);
    }

    private void initShader(RectF in, RectF out) {
        mShaderList.clear();

        float inRadius = in.width() / 2f;
        float outRadius = out.width() / 2f;

        float diff = outRadius - inRadius;
        diff /= 2f;

        float step = diff / ANIMATION_STEP_COUNT;

        float ra = inRadius - diff;
        float rb = inRadius;
        float rc = ra + diff;
        for (int i = 0; i < ANIMATION_STEP_COUNT; i++) {
            float[] stop = new float[]{ra, rb, rc};
            Shader shader = new RadialGradient(in.centerX(), in.centerY(), outRadius, mBreathColors, stop, Shader.TileMode.REPEAT);

            ra += step;
            rb += step;

            if (rb > rc) {
                rc = rb;
            }

            mShaderList.add(shader);
        }
    }

    public void startAnimation() {
        stopAnimation();
        ObjectAnimator oa = ObjectAnimator.ofFloat(this, "frame", 0, ANIMATION_STEP_COUNT - 1);
        oa.setDuration(2000);
        oa.setRepeatCount(ValueAnimator.INFINITE);

        mAnimator = oa;

        mAnimator.start();
    }

    public void stopAnimation() {
        if (mAnimator != null && mAnimator.isRunning())
            mAnimator.cancel();
    }

    private void setFrame(int frame) {
        mCurrentFrame = frame;
        invalidateSelf();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        if (StateSet.stateSetMatches(state, android.R.attr.state_pressed)) {
            if (!isPressed) {
                isPressed = true;
                startAnimation();
            }
        } else {
            isPressed = false;
            stopAnimation();
        }
        return super.onStateChange(state);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawOval(mInBounds, mCenterPaint);
        mWavePaint.setShader(mShaderList.get(mCurrentFrame));
        canvas.drawOval(mOutBounds, mWavePaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}