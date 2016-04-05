package bong.ginshell.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.start_service)
    Button mStartService;
    @Bind(R.id.stop_service)
    Button mStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = new Intent("com.ginshell.bong.Service.BeClosed");
        sendBroadcast(intent);
        System.out.println("send broadcast");
    }
}
