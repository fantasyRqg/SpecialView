package bong.ginshell.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.faradaj.blurbehind.BlurBehind;
import com.faradaj.blurbehind.OnBlurCompleteListener;

/**
 * @author rqg
 * @date 3/2/16.
 */
public class PopupWindowNps extends PopupWindow {
//    public static final String TAG = PopupWindowNps.class.getSimpleName();

    private Activity mActivity;

    public PopupWindowNps(Activity context) {
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);

        mActivity = context;
        View view = LayoutInflater.from(context).inflate(R.layout.poupwindow_nps, null);
        ShapeDrawable d = new ShapeDrawable(new SlantShape());
        d.getPaint().setColor(Color.WHITE);
        setBackgroundDrawable(d);

        setContentView(view);

        setAnimationStyle(R.style.PopupWindowAnimation);


        TextView textView = (TextView) view.findViewById(R.id.next_step);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNps();
                dismiss();
            }
        });
    }


    private void showNps() {

        BlurBehind.getInstance().execute(mActivity, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(mActivity, NpsActivity.class);
                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }
}
