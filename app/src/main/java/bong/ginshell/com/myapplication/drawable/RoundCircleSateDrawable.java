package bong.ginshell.com.myapplication.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;
import android.util.StateSet;

/**
 * *Created by rqg on 4/6/16.
 */
public class RoundCircleSateDrawable extends StateListDrawable {
    private static final String TAG = "RoundCircleSateDrawable";

    public RoundCircleSateDrawable() {
        super();
        addState(new int[]{android.R.attr.state_pressed}, new ShapeDrawable(new PressedShape()));
        addState(StateSet.WILD_CARD, new ShapeDrawable(new UnpressedShape()));
    }

    public static class PressedShape extends Shape {
        private RectF mBound = new RectF();
        private int mColor = Color.parseColor("#F58723");
        private Shader mShader;

        public PressedShape() {
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            Log.d(TAG, "draw() called with: " + "canvas = [" + canvas + "], paint = [" + paint + "]");
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            paint.setShader(mShader);
            canvas.drawOval(mBound, paint);
        }

        @Override
        protected void onResize(float width, float height) {
            mBound.set(0, 0, width, height);
            mBound.inset(0.5f, 0.5f);
            float hw = width / 2f;
            float hh = height / 2f;
            mShader = new RadialGradient(hw, hh, Math.min(hw, hh), new int[]{mColor, Color.parseColor("#00FFFFFF")}, new float[]{0.8f, 1f}, Shader.TileMode.REPEAT);
        }
    }


    public static class UnpressedShape extends Shape {

        private RectF mOutBound = new RectF();
        private RectF mInBound = new RectF();
        private float mStrokeWidth = 10;
        private int mInColor = Color.parseColor("#F58723");
        private int mOutColor = Color.parseColor("#7CF58723");


        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(mOutColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(mStrokeWidth);
            canvas.drawOval(mOutBound, paint);


            paint.setColor(mInColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawOval(mInBound, paint);
        }

        @Override
        protected void onResize(float width, float height) {
            mStrokeWidth = Math.min(width, height) * 0.04f;
            float adjustHalfSW = mStrokeWidth / 2f + 0.5f;
            mOutBound.set(adjustHalfSW, adjustHalfSW, width - adjustHalfSW, height - adjustHalfSW);

            mInBound.set(mOutBound);

            mInBound.inset(mStrokeWidth * 2, mStrokeWidth * 2);
        }
    }
}
