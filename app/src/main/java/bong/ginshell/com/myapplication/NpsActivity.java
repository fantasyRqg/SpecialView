package bong.ginshell.com.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.faradaj.blurbehind.BlurBehind;

/**
 * @author rqg
 * @date 3/2/16.
 */
public class NpsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nps);


        BlurBehind.getInstance()
                .withAlpha(80)
                .withFilterColor(Color.parseColor("#000000"))
                .setBackground(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
