package bong.ginshell.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bong.ginshell.com.myapplication.view.InfoPopupWindow;
import bong.ginshell.com.myapplication.view.StateIndicator;

public class Main2Activity extends AppCompatActivity {
    public final static String TAG = Main2Activity.class.getSimpleName();

    private InfoPopupWindow mHelpWindow;
    private View mParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mParent = findViewById(R.id.parent);
    }

    public void left(View view) {
        mHelpWindow = new InfoPopupWindow(this, "butten 0 ,lafjlaksdjfa");
        mHelpWindow.showAbove(mParent, view, this);
    }

    public void center(View view) {
        mHelpWindow = new InfoPopupWindow(this, "butten 1 ,lafjlaksdjffasdfjakdsflaldjfkajdlsjfkasfa");
        mHelpWindow.showAbove(mParent, view, this);
    }

    public void right(View view) {
        mHelpWindow = new InfoPopupWindow(this, "butten 2 ,\nlafjlaksdjfaasdfasdfalksdfjlaksdfjalsdfjkasdalf");
        mHelpWindow.showAbove(mParent, view, this);

    }

    public void onSateClick(View view) {
        StateIndicator si = (StateIndicator) view;
        si.setState(!si.isOpen());
    }
}
