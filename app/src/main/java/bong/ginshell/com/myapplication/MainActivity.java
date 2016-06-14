package bong.ginshell.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bong.ginshell.com.myapplication.drawable.BreathDrawable;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setBackground(new BreathDrawable());

    }


    public void buttonClick(View view) {
        LoadingDialog dialog = new LoadingDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }
}
