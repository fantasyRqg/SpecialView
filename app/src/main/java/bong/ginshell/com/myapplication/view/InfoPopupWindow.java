package bong.ginshell.com.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * *Created by rqg on 4/5/16.
 */
public class InfoPopupWindow extends PopupWindow {
    private static final String TAG = "InfoPopupWindow";
    private int mLocationX, mLocationY;
    private View contentView;

    public InfoPopupWindow(Context context, @LayoutRes int layout) {
        super(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(false);
        setFocusable(true);

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        contentView = View.inflate(context, layout, null);

        contentView.setBackground(new ColorDrawable(Color.RED));

        contentView.refreshDrawableState();

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setContentView(contentView);

        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {


                int contentX = mLocationX - contentView.getWidth() / 2;
                int contentY = mLocationY - contentView.getHeight();
                int windowWidth = getContentView().getResources().getDisplayMetrics().widthPixels;
                int diff = 0;

                Log.d(TAG, "onGlobalLayout: cx = " + contentX + " cvw = " + contentView.getWidth() + " diff = " + diff + " wd = " + windowWidth);

                if (contentX < 0) {
                    diff = contentX;
                } else if (contentX + contentView.getWidth() > windowWidth) {
                    diff = -windowWidth + contentX + contentView.getWidth();
                }

                Log.d(TAG, "onGlobalLayout: cx = " + contentX + " cvw = " + contentView.getWidth() + " diff = " + diff + " wd = " + windowWidth);

                contentX -= diff;

                contentView.setBackground(new ShapeDrawable(new TriangleShape(diff)));

                update(contentX, contentY, -1, -1);
                contentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }

    public void showAbove(View parent, View view, final Activity activity) {
        if (parent == null || view == null || activity == null || activity.isFinishing() || activity.isDestroyed())
            return;

        // 计算出发点的位置
        int[] location = new int[2];
        // 获得位置
        view.getLocationOnScreen(location);

        mLocationX = location[0] + view.getWidth() / 2;
        mLocationY = location[1];
        // 显示
        showAtLocation(parent, Gravity.NO_GRAVITY, mLocationX, mLocationY);

        update();

        setWindowAlpha(activity, 0.4f);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(activity, 1f);
            }
        });


    }


    private void setWindowAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
        layoutParams.alpha = alpha;
        activity.getWindow().setAttributes(layoutParams);
    }


    public static class TriangleShape extends Shape {
        private RectF mBound = new RectF();
        private Path mTriangle = new Path();
        private CornerPathEffect mCpe = new CornerPathEffect(10);
        private float mOffset = 0;


        public TriangleShape(float mOffset) {
            Log.d(TAG, "TriangleShape() called with: " + "mOffset = [" + mOffset + "]");
            this.mOffset = mOffset;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {


            paint.setColor(Color.BLUE);
            canvas.drawRoundRect(mBound, 10, 10, paint);

//            canvas.save();

//            canvas.translate(mOffset, 0);
            PathEffect cpe = paint.getPathEffect();
            paint.setPathEffect(mCpe);
            canvas.drawPath(mTriangle, paint);
            paint.setPathEffect(cpe);

//            canvas.restore();
        }

        @Override
        protected void onResize(float width, float height) {
            float tHeight = height * 0.2f;
            mBound.set(0, 0, width, height - tHeight);

            mTriangle.reset();

            float halfWidth = width / 2f;
            float bottom = mBound.bottom - 1;
            mTriangle.moveTo(halfWidth - tHeight * 2, bottom);
            mTriangle.lineTo(halfWidth - tHeight, bottom);
            mTriangle.lineTo(halfWidth, height);
            mTriangle.lineTo(halfWidth + tHeight, bottom);
            mTriangle.lineTo(halfWidth + tHeight * 2, bottom);

            mTriangle.offset(mOffset, 0);
        }
    }
}