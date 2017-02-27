package com.xuan.study.study.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by dengzhaoxuan on 2017/2/27.
 */

public class UserInfoProvider extends ContentProvider{
    private static final String CONTENT = "content://";
    public static final String AUTHORIY = "com.book.jtm.info";

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORIY;

    public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORIY;

    public static final Uri POSTCODE_URI = Uri.parse(CONTENT + AUTHORIY + "/" +
    UserInfoDbHelper.TABLE_USER_INFO);

    public static final Uri COMPANY_URI = Uri.parse(CONTENT + AUTHORIY + "/" +
    UserInfoDbHelper.TABLE_COMPANY);

    private SQLiteDatabase mDatabase;
    static final int USER_INFOS = 1;
    static final int USER_INFO_ITEM = 2;
    static final int COMPANY = 3;
    static final int COMPANY_ITEM = 4;

    static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORIY, "userinfo", USER_INFOS);
        uriMatcher.addURI(AUTHORIY, "userinfo/*", USER_INFO_ITEM);
        uriMatcher.addURI(AUTHORIY, "company", COMPANY);
        uriMatcher.addURI(AUTHORIY, "company/#", COMPANY_ITEM);
    }

    @Override
    public boolean onCreate() {
        mDatabase = new UserInfoDbHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case USER_INFOS:
                cursor = mDatabase.query(UserInfoDbHelper.TABLE_USER_INFO, projection,
                        selection, selectionArgs, null,null,sortOrder);
                break;
            case USER_INFO_ITEM:
                String  tel = uri.getPathSegments().get(1);
                cursor = mDatabase.query(UserInfoDbHelper.TABLE_USER_INFO, projection,
                        "tel_num = ?", new String[]{tel}, null,null,sortOrder);
                break;
            case COMPANY:
                cursor = mDatabase.query(UserInfoDbHelper.TABLE_COMPANY,projection,
                        selection,selectionArgs,null,null,sortOrder);
                break;
            case COMPANY_ITEM:
                String cid = uri.getPathSegments().get(1);
                cursor = mDatabase.query(UserInfoDbHelper.TABLE_COMPANY,projection,
                        "id = ?",new String[]{cid},null,null,sortOrder);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case USER_INFOS:
            case COMPANY:
                return CONTENT_TYPE;
            case USER_INFO_ITEM:
            case COMPANY_ITEM:
                return CONTENT_TYPE_ITEM;
            default:
                throw new RuntimeException("错误的Uri");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long newId = 0;
        Uri newUri = null;
        switch (uriMatcher.match(uri)){
            case USER_INFOS:
                newId = mDatabase.insert(UserInfoDbHelper.TABLE_USER_INFO,null,values);
                newUri = Uri.parse(CONTENT + AUTHORIY + "/"
                        + UserInfoDbHelper.TABLE_USER_INFO + "/" + newId);
                break;
            case COMPANY:
                newId = mDatabase.insert(UserInfoDbHelper.TABLE_COMPANY,null,values);
                newUri = Uri.parse(CONTENT + AUTHORIY + "/"
                        + UserInfoDbHelper.TABLE_COMPANY + "/" + newId);
                break;
        }
        if(newId > 0){
            return newUri;
        }
        throw new IllegalArgumentException("Failed to insert row into"+ uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
