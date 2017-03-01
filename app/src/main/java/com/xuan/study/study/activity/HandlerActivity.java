package com.xuan.study.study.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.xuan.study.study.R;


public class HandlerActivity extends AppCompatActivity {
    private static Object sLockObject = new Object();
    public static final int MSG_HANDLER = 0x111;
    Handler handlera = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MSG_HANDLER){
                Toast.makeText(HandlerActivity.this, "this is from handlera", Toast.LENGTH_SHORT).show();
            }
        }
    };
    Handler handlerb = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MSG_HANDLER){
                Toast.makeText(HandlerActivity.this, "this is from handlerb", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        /*Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                handlera.sendEmptyMessage(MSG_HANDLER);
            }
        }) {
            @Override
            public void run() {
                super.run();
                handlerb.sendEmptyMessage(MSG_HANDLER);
            }
        };
        thread1.start();*/
        Thread thread = new WaitThread();
        thread.start();
        waitAndNotifyAll();
    }
    void waitAndNotifyAll(){
        Log.i("WaitText", "主线程运行");
        long starttime = System.currentTimeMillis();
        synchronized (sLockObject){
            try {
                Log.i("WaitText","主线程等待获得锁");
                sLockObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long timeMs = (System.currentTimeMillis() - starttime);
        Log.i("WaitText", "主线程继续---->等待耗时：" + timeMs + "ms");
    }
    class WaitThread extends Thread{
        @Override
        public void run() {
            try {
                synchronized (sLockObject){
                    Log.i("WaitText","其他线程获得锁");
                    Thread.sleep(3000);
                    sLockObject.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
