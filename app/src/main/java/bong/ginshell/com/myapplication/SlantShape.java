package bong.ginshell.com.myapplication;

import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.NonNull;

/**
 * @author rqg
 * @date 3/2/16.
 */
public class SlantShape extends Shape {
    private Path mPath = new Path();
    private RectF mRect = new RectF();
    private int mRightMarginTop = 100;

    public SlantShape() {
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawPath(mPath, paint);
    }

    @Override
    public void getOutline(@NonNull Outline outline) {
        outline.setRect((int) Math.ceil(mRect.left), (int) Math.ceil(mRect.top + mRightMarginTop),
                (int) Math.floor(mRect.right), (int) Math.floor(mRect.bottom));
    }

    @Override
    protected void onResize(float width, float height) {
        mRect.set(0, 0, width, height);
        mPath.reset();
        mPath.moveTo(mRect.left, mRect.top);
        mPath.lineTo(mRect.right, mRect.top + mRightMarginTop);
        mPath.lineTo(mRect.right, mRect.bottom);
        mPath.lineTo(mRect.left, mRect.bottom);
        mPath.close();
    }


    /**
     * Returns the RectF that defines this rectangle's bounds.
     */
    protected final RectF rect() {
        return mRect;
    }

    @Override
    public SlantShape clone() throws CloneNotSupportedException {
        final SlantShape shape = (SlantShape) super.clone();
        shape.mPath = mPath;
        shape.mRect = mRect;

        return shape;
    }
}
