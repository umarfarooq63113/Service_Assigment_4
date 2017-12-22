package com.example.umar.projectabc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnStop;
    EditText limit;
    TextView tvCount, abc;
    static int a = 1;

    MyMainReceiver myMainReceiver;
    Intent myIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnStart = (Button) findViewById(R.id.startservice);
        btnStop = (Button) findViewById(R.id.stopservice);

        limit = (EditText) findViewById(R.id.limit);
        tvCount = (TextView) findViewById(R.id.tvCount);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abc = findViewById(R.id.limit);
                String val = (limit.getText().toString());
                a = Integer.parseInt(val);
                startService();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });
    }

    private void startService() {
        myIntent = new Intent(MainActivity.this, MyService.class);
        startService(myIntent);
    }

    private void stopService() {
        if (myIntent != null) {
            stopService(myIntent);
        }
        myIntent = null;
    }

    @Override
    protected void onStart() {
        myMainReceiver = new MyMainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_UPDATE_CNT);
        registerReceiver(myMainReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myMainReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService();
    }

    private class MyMainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MyService.ACTION_UPDATE_CNT)) {
                int int_from_service = intent.getIntExtra(MyService.KEY_INT_FROM_SERVICE, 0);
                float d=int_from_service;
                d=d/a*100;
                int k=Math.round(d);
                //tvCount.setText((show)+"%");
                tvCount.setText(k+"%");
            }
        }
    }
}