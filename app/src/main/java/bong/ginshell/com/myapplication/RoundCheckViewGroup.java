package bong.ginshell.com.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rqg
 * @date 2/22/16.
 */
public class RoundCheckViewGroup extends View {
    protected float mItemWidth;
    protected Paint mTextPaint, mCheckPaint, mBackgroundPaint, mCheckedTextPaint;

    protected int mSelectedIndex = 0;
    protected float mPadding;
    protected AnimatorSet mAnimatorSet = null;

    protected float mAnimationPos, mAnimationWidth;

    protected List<String> mItems = new ArrayList<>();

    protected OnItemCheckListener mCheckListener;


    public RoundCheckViewGroup(Context context) {
        super(context);
        initPaint();
    }

    public RoundCheckViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public RoundCheckViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    protected void initPaint() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.parseColor("#000000"));
        mTextPaint.setTextSize(30);

        mCheckedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCheckedTextPaint.setTextAlign(Paint.Align.CENTER);
        mCheckedTextPaint.setColor(Color.parseColor("#ffffff"));
        mCheckedTextPaint.setTextSize(30);

        mCheckPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCheckPaint.setStyle(Paint.Style.FILL);
        mCheckPaint.setColor(Color.parseColor("#333333"));

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(Color.parseColor("#e3e3e3"));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initItemLayout();
    }


    protected void initItemLayout() {
        if (getWidth() != 0 && mItems != null && mItems.size() > 0) {
            mItemWidth = getWidth() / mItems.size();
            mPadding = Math.min(mItemWidth, getHeight()) * 0.1f;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float r = Math.min(getWidth(), getHeight()) / 2f;
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), r, r, mBackgroundPaint);

        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            canvas.drawRoundRect(mAnimationPos + mPadding, mPadding, mAnimationPos + mItemWidth - mPadding, getHeight() - mPadding, r, r, mCheckPaint);
        } else {
            float start = mSelectedIndex * mItemWidth;
            canvas.drawRoundRect(start + mPadding, mPadding, start + mItemWidth - mPadding, getHeight() - mPadding, r, r, mCheckPaint);
        }

        drawItem(canvas);
    }

    protected void drawItem(Canvas canvas) {
        float x = mItemWidth / 2f;
        float y = getHeight() / 2f - (mTextPaint.getFontMetrics().top + mTextPaint.getFontMetrics().bottom) / 2f;
        for (int i = 0; i < mItems.size(); i++) {
            if (i == mSelectedIndex) {
                canvas.drawText(mItems.get(i), x, y, mCheckedTextPaint);
            } else {
                canvas.drawText(mItems.get(i), x, y, mTextPaint);
            }

            x += mItemWidth;

        }
    }


    public void check(int index) {
        animatingTo(index);
        if (mCheckListener != null) {
            mCheckListener.onCheck(mSelectedIndex);
        }
    }

    protected void animatingTo(int index) {
        if (index < 0 || mItems == null || index >= mItems.size()) {
            return;
        }
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.end();
        }

//        PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("animationPos", mSelectedIndex * mItemWidth, index * mItemWidth);
//        PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("animationWidth", mItemWidth, Math.min(mItemWidth, getHeight()), mItemWidth);
        ObjectAnimator oa = ObjectAnimator.ofFloat(this, "animationPos", mSelectedIndex * mItemWidth, index * mItemWidth);
        mSelectedIndex = index;


        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(oa);


        mAnimatorSet.setDuration(200);
        mAnimatorSet.start();
    }

    //mAnimationPos, mAnimationWidth;
    public void setAnimationPos(float value) {
        mAnimationPos = value;
        invalidate();
    }

    public void setAnimationWidth(float value) {
        mAnimationWidth = value;
    }

    public List<String> getItems() {
        return mItems;
    }

    public void setItems(List<String> mItems) {
        this.mItems = mItems;
        initItemLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();


        if (action == MotionEvent.ACTION_UP) {
            check((int) (event.getX() / mItemWidth));
        }
        return true;
    }

    public static interface OnItemCheckListener {
        void onCheck(int index);
    }


    public void setCheckListener(OnItemCheckListener mCheckListener) {
        this.mCheckListener = mCheckListener;
    }
}
