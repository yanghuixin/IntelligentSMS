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
    private static final int CODE_GROUPS_INSERT = 0;

    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final String authority = "com.yhx.intelligentsms";
    {
        //添加匹配规则
        matcher.addURI(authority, "groups/insert", CODE_GROUPS_INSERT);
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
        return null;
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
                long rawId = db.insert("groups", null, values);
                //插入失败
                if (rawId == -1){
                    return null;
                }else {
                    //把返回的行id，拼接在uri后面，然后返回
                    return ContentUris.withAppendedId(uri, rawId);
                }
        }
        return null;
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
