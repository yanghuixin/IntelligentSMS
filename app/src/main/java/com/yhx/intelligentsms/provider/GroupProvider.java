package com.yhx.intelligentsms.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yhx.intelligentsms.dao.GroupOpenHelper;

/**
 * Created by yhx on 2017/12/11.
 */

public class GroupProvider extends ContentProvider {

    private GroupOpenHelper helper;
    private SQLiteDatabase db;
    private static final String TABLE_GROUPS = "groups";

    private static final String authority = "com.yhx.intelligentsms";
    public static final Uri BASE_URI = Uri.parse("content://" + authority);

    private static final int CODE_GROUPS_INSERT = 0;
    private static final int CODE_GROUPS_QUERY = 1;

    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    {
        //添加匹配规则
        matcher.addURI(authority, "groups/insert", CODE_GROUPS_INSERT);
        matcher.addURI(authority, "groups/query", CODE_GROUPS_QUERY);
    }
    @Override
    public boolean onCreate() {
        helper = GroupOpenHelper.getInstance(getContext());
        db = helper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (matcher.match(uri)){
            case CODE_GROUPS_QUERY:
                Cursor cursor = db.query(TABLE_GROUPS, projection, selection, selectionArgs, null, null, sortOrder);
                return cursor;
            default:
                throw new IllegalArgumentException("未识别的uri:" + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (matcher.match(uri)){
            case CODE_GROUPS_INSERT:
                long rawId = db.insert(TABLE_GROUPS, null, values);
                //插入失败
                if (rawId == -1){
                    return null;
                }else {
                    //把返回的行id，拼接在uri后面，然后返回
                    return ContentUris.withAppendedId(uri, rawId);
                }
            default:
                throw new IllegalArgumentException("未识别的uri:" + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
