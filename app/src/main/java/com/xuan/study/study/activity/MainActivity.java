package com.xuan.study.study.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xuan.study.study.R;
import com.xuan.study.study.service.WeatherService;

public class MainActivity extends AppCompatActivity {
    private static final String BROADCAST_ACTION = "XUAN";
    /**
     * 广播接收器
     */

    public BroadcastReceiver oneBroadcastReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BROADCAST_ACTION))
                Toast.makeText(context,"This is One! "+getResultData(),Toast.LENGTH_SHORT).show();
            //丢弃广播
            //abortBroadcast();
            //setResultData("One is　your first");
        }
    };
    public BroadcastReceiver twoBroadcastReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BROADCAST_ACTION))
                Toast.makeText(context,"This is Two! "+getResultData(),Toast.LENGTH_SHORT).show();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 弹一个DialogAcitivity
         */
        Button dialog = (Button) findViewById(R.id.bt_id_dialog);
        dialog.setOnClickListener(onClickListener);
        /**
         * 发送一条广播
         */
        Button send = (Button) findViewById(R.id.bt_id_send);
        send.setOnClickListener(onClickListener);
        /**
         * 发送一条本地广播
         */
        Button sendlocal = (Button) findViewById(R.id.bt_id_sendlocal);
        sendlocal.setOnClickListener(onClickListener);
        /**
         * ContentProvider
         */
        Button contentprovider = (Button) findViewById(R.id.bt_id_contentprovider);
        contentprovider.setOnClickListener(onClickListener);

        /**
         * 注册广播接收器
         */
        IntentFilter filterHight = new IntentFilter();
        filterHight.setPriority(100);
        filterHight.addAction(BROADCAST_ACTION);
        registerReceiver(twoBroadcastReciver, filterHight);
        IntentFilter filterLow = new IntentFilter();
        filterLow.setPriority(110);
        filterLow.addAction(BROADCAST_ACTION);
        registerReceiver(oneBroadcastReciver, filterLow);
        /**
         * 注册本地广播接收器
         */
        LocalBroadcastManager.getInstance(this).registerReceiver(oneBroadcastReciver,filterLow);
        LocalBroadcastManager.getInstance(this).registerReceiver(twoBroadcastReciver,filterHight);

        /**
         * 自定义Scroll Activity
         */
        Button scroll = (Button) findViewById(R.id.bt_id_customview);
        scroll.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.activity_dialog:
                    Intent dialogIntent = new Intent(MainActivity.this,DialogActivity.class);
                    startActivity(dialogIntent);
                    break;
                case R.id.bt_id_send:
                    //发送普通广播
                    /*Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(BROADCAST_ACTION);
                    sendBroadcast(broadcastIntent);*/
                    //发送有序广播
                    Intent serviceIntent = new Intent(MainActivity.this,WeatherService.class);
                    startService(serviceIntent);
                    break;
                case R.id.bt_id_sendlocal:
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(BROADCAST_ACTION);
                    LocalBroadcastManager.getInstance(MainActivity.this)
                            .sendBroadcast(broadcastIntent);
                    break;
                case R.id.bt_id_contentprovider:
                    Intent providerIntent = new Intent(MainActivity.this,ProviderActivity.class);
                    startActivity(providerIntent);
                    break;
                case R.id.bt_id_customview:
                    Intent scrollIntent = new Intent(MainActivity.this,ScrollActivity.class);
                    startActivity(scrollIntent);
                    break;
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("Activity","onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onStart() {
        Log.i("Activity","onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("Activity","onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("Activity","onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("Activity","onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("Activity","onDestroy");
        unregisterReceiver(oneBroadcastReciver);
        unregisterReceiver(twoBroadcastReciver);
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i("Activity","onRestart");
        super.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.i("Activity","onSaveInstanceState");
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
