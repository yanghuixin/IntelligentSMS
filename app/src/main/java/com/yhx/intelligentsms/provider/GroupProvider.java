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
    private static final String TABLE_THREAD_GROUP = "thread_group";

    private static final String authority = "com.yhx.intelligentsms";
    public static final Uri BASE_URI = Uri.parse("content://" + authority);

    private static final int CODE_GROUPS_INSERT = 0;
    private static final int CODE_GROUPS_QUERY = 1;
    private static final int CODE_GROUPS_UPDATE = 2;
    private static final int CODE_GROUPS_DELETE = 3;
    private static final int CODE_THREAD_GROUP_INSERT = 4;
    private static final int CODE_THREAD_GROUP_QUERY = 5;
    private static final int CODE_THREAD_GROUP_UPDATE = 6;
    private static final int CODE_THREAD_GROUP_DELETE = 7;

    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    {
        //添加匹配规则
        matcher.addURI(authority, "groups/insert", CODE_GROUPS_INSERT);
        matcher.addURI(authority, "groups/query", CODE_GROUPS_QUERY);
        matcher.addURI(authority, "groups/update", CODE_GROUPS_UPDATE);
        matcher.addURI(authority, "groups/delete", CODE_GROUPS_DELETE);
        matcher.addURI(authority, "thread_group/insert", CODE_THREAD_GROUP_INSERT);
        matcher.addURI(authority, "thread_group/query", CODE_THREAD_GROUP_QUERY);
        matcher.addURI(authority, "thread_group/update", CODE_THREAD_GROUP_UPDATE);
        matcher.addURI(authority, "thread_group/delete", CODE_THREAD_GROUP_DELETE);
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
                //监视uri上数据改变的一个内容观察者
                //只要该uri上的数据改变，内容观察者就会立刻发现，重新查询
                cursor.setNotificationUri(getContext().getContentResolver(), BASE_URI);
                return cursor;
            case CODE_THREAD_GROUP_QUERY:
                cursor = db.query(TABLE_THREAD_GROUP, projection, selection, selectionArgs, null, null, sortOrder);
                //监视uri上数据改变的一个内容观察者
                //只要该uri上的数据改变，内容观察者就会立刻发现，重新查询
                cursor.setNotificationUri(getContext().getContentResolver(), BASE_URI);
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
                    getContext().getContentResolver().notifyChange(BASE_URI, null);
                    //把返回的行id，拼接在uri后面，然后返回
                    return ContentUris.withAppendedId(uri, rawId);
                }
            case CODE_THREAD_GROUP_INSERT:
                rawId = db.insert(TABLE_THREAD_GROUP, null, values);
                //插入失败
                if (rawId == -1){
                    return null;
                }else {
                    getContext().getContentResolver().notifyChange(BASE_URI, null);
                    //把返回的行id，拼接在uri后面，然后返回
                    return ContentUris.withAppendedId(uri, rawId);
                }
            default:
                throw new IllegalArgumentException("未识别的uri:" + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (matcher.match(uri)){
            case CODE_GROUPS_DELETE:
                int number = db.delete(TABLE_GROUPS, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return number;
            case CODE_THREAD_GROUP_DELETE:
                number = db.delete(TABLE_THREAD_GROUP, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return number;
            default:
                throw new IllegalArgumentException("未识别的uri:" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (matcher.match(uri)){
            case CODE_GROUPS_UPDATE:
                int number = db.update(TABLE_GROUPS, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return number;
            case CODE_THREAD_GROUP_UPDATE:
                number = db.update(TABLE_THREAD_GROUP, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return number;
            default:
                throw new IllegalArgumentException("未识别的uri:" + uri);
        }
    }
}
