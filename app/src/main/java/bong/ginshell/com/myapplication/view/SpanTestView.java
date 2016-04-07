package bong.ginshell.com.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * *Created by rqg on 3/16/16.
 */
public class SpanTestView extends View {
    private static final String TAG = "SpanTestView";
    private Layout mLayout;
    Rect rectF = new Rect();
    Paint backPaint = new Paint();

    public SpanTestView(Context context) {
        super(context);
        initSpanLayout();
    }

    public SpanTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSpanLayout();
    }

    public SpanTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSpanLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void initSpanLayout() {

        SpannableStringBuilder ssb = new SpannableStringBuilder();

        backPaint.setColor(Color.GRAY);

        int len;
        String s = "hello";

        len = ssb.length();


        ssb.append(s);
        ssb.setSpan(new AbsoluteSizeSpan(69), len, len + s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String s2 = "   makjfaa   world~!";
        ssb.append(s2);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setTextSize(45);

        CharSequence mText = ssb;
        TextPaint mTextPaint = new TextPaint(paint);
        int wantWidth = 500;
        Layout.Alignment alignment = Layout.Alignment.ALIGN_CENTER;
        float mTextDir = 0f;
        float mSpacingMult = 1f;
        float mSpacingAdd = 0f;
        boolean mIncludePad = false;
        mLayout = new DynamicLayout(mText, mTextPaint, wantWidth,
                alignment, mSpacingMult, mSpacingAdd, mIncludePad);


        Log.d(TAG, "initSpanLayout: " + mLayout.getWidth() + "," + mLayout.getHeight() + "," + mLayout.getLineRight(0) + "," + mLayout.getLineWidth(0));

        rectF.set((int) mLayout.getLineLeft(0), 0, (int) mLayout.getLineRight(0), mLayout.getLineBottom(0));


        Log.d(TAG, "CharSequence: " + paint.measureText(ssb, 0, ssb.length()));
        Log.d(TAG, "CharSequence: " + paint.measureText(s + s2));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);


        canvas.drawRect(rectF, backPaint);
        mLayout.draw(canvas);
    }
}
