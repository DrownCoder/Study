package com.xuan.study.study.activity;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xuan.study.study.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button dialog = (Button) findViewById(R.id.bt_id_dialog);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DialogActivity.class);
                startActivity(intent);
            }
        });
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
