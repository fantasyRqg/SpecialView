package bong.ginshell.com.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author rqg
 * @date 1/20/16.
 */
public class HeartWaveFast extends LinearLayout {

    public HeartWaveFast(Context context) {
        this(context, null);
    }

    public HeartWaveFast(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartWaveFast(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(LinearLayout.HORIZONTAL);

        ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(p);

//        LinearLayout.LayoutParams params = new LayoutParams();
        IconTextView itv1 = new IconTextView(getContext());
        IconTextView itv2 = new IconTextView(getContext());


    }


}
