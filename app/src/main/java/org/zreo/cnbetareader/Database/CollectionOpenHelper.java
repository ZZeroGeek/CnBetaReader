package org.zreo.cnbetareader.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guang on 2015/8/8.
 */
public class CollectionOpenHelper extends SQLiteOpenHelper {

    //新闻标题数据库表建表语句
    public static final String CREATE_COLLECTION = "create table Collection ("
            + "id integer primary key autoincrement, "
            + "sid integer, "   /*帖子id*/
            + "catid integer, "
            + "topic integer, "
            + "aid text, "
            + "user_id text, "  /*用户ID*/
            + "title text, "    /*标题*/
            + "hometext text, "  /*简介*/
            + "comments text, "  /*评论数*/
            + "counter text, "   /*查看数*/
            + "inputtime text, "  /*发布时间*/
            + "thumb text )";   /*图片地址*/

    public CollectionOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COLLECTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
