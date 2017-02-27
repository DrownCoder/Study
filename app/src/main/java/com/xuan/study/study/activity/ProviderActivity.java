package com.xuan.study.study.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xuan.study.study.R;
import com.xuan.study.study.contentprovider.UserInfoDbHelper;
import com.xuan.study.study.contentprovider.UserInfoProvider;

public class ProviderActivity extends AppCompatActivity {
    EditText mUserDescEdittext;
    EditText mUserTelEdittext;
    EditText mUserCompIdEdittext;
    Button mSubmitBtn;

    EditText mCompIdEdittext;
    EditText mCompBusinessEdittext;
    EditText mCompAddrEdittext;
    Button mCompSubmitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        initViews();
    }

    private void initViews() {
        //用户信息相关的View
        mUserDescEdittext = (EditText) findViewById(R.id.et_id_userinfo);
        mUserTelEdittext = (EditText) findViewById(R.id.et_id_usercall);
        mUserCompIdEdittext = (EditText) findViewById(R.id.et_id_usercompany);

        mSubmitBtn = (Button) findViewById(R.id.bt_id_submituser);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfoRecord();
                mSubmitBtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        queryPostCode();
                    }
                },1000);
            }
        });

        //公司相关的View
        mCompAddrEdittext = (EditText) findViewById(R.id.et_id_companypath);
        mCompIdEdittext = (EditText) findViewById(R.id.et_id_companyid);
        mCompBusinessEdittext = (EditText) findViewById(R.id.et_id_companybus);

        mCompSubmitBtn = (Button) findViewById(R.id.bt_id_submitcom);
        mCompSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCompanyRecord();
            }
        });
    }
    private void saveUserInfoRecord(){
        ContentValues newRecord = new ContentValues();
        newRecord.put(UserInfoDbHelper.DESC_COLUMN,
                mUserDescEdittext.getText().toString());
        newRecord.put(UserInfoDbHelper.TEL_COLUMN,
                mUserTelEdittext.getText().toString());
        newRecord.put(UserInfoDbHelper.COMP_ID_COLUMN,
                mCompIdEdittext.getText().toString());
        getContentResolver().insert(UserInfoProvider.POSTCODE_URI,newRecord);
    }

    private void saveCompanyRecord(){
        ContentValues newRecord = new ContentValues();
        newRecord.put(UserInfoDbHelper.ADDR_COLUMN,
                mCompAddrEdittext.getText().toString());
        newRecord.put(UserInfoDbHelper.BUSINESS_COLUMN,
                mCompBusinessEdittext.getText().toString());
        newRecord.put(UserInfoDbHelper.ID_COLUMN,mCompIdEdittext.getText().toString());
        getContentResolver().insert(UserInfoProvider.COMPANY_URI,newRecord);
    }

    private void queryPostCode(){
        Uri queryUri = Uri.parse("content://com.book.jtm.info/userinfo/123456");
        Cursor cursor = getContentResolver().query(queryUri,null,null,null,null);
        if(cursor.moveToFirst()){
            Toast.makeText(this,"电话来自："
            + cursor.getString(2), Toast.LENGTH_SHORT).show();
        }
    }
}
