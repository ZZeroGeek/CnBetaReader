package org.zreo.cnbetareader.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.zreo.cnbetareader.Entitys.CommentItemEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hcy on 2015/8/9.
 */
public class CommentDatabase {

    public static final String COMMENT_DB = "CommentItemEntity";
    public static final int VERSION = 1;
    private static CommentDatabase commentDatabase;
    private SQLiteDatabase db;

    //将构造方法私有化
    private CommentDatabase(Context context) {
        NewsTitleOpenHelper commentOpenHelper = new NewsTitleOpenHelper(
                context, COMMENT_DB, null, VERSION);
        db = commentOpenHelper.getWritableDatabase();
    }

    //获取CommentDatabase的实例
    public synchronized static CommentDatabase getInstance(Context context) {
        if (commentDatabase == null) {
            commentDatabase = new CommentDatabase(context);
        }
        return commentDatabase;
    }


    //将CommentItemEntity实例存储到数据库
    public void saveCommentItemEntity(CommentItemEntity commentItemEntity) {
        if (commentItemEntity != null) {
            ContentValues values = new ContentValues();
            values.put("sid", commentItemEntity.getSid());
            values.put("pid", commentItemEntity.getSid());
            values.put("tid", commentItemEntity.getTid());
            values.put("icon", commentItemEntity.getIcon());
            values.put("date", commentItemEntity.getDate());
            values.put("host_name", commentItemEntity.getHost_name());
            values.put("name", commentItemEntity.getName());
            values.put("score", commentItemEntity.getScore());
            values.put("reason", commentItemEntity.getReason());
            values.put("comment", commentItemEntity.getComment());
            values.put("refContent", commentItemEntity.getRefContent());
            db.insert(COMMENT_DB, null, values);
        }
    }



    //从数据库读取评论，返回List
    public List<CommentItemEntity> loadCommentItemEntity() {
        List<CommentItemEntity> list = new ArrayList<CommentItemEntity>();
        Cursor cursor = db.query("CommentItemEntity", null, null, null, null, null, null);
       // Cursor cursor = commentDb.query(COMMENT_DB, null, null, null, null, null, null);   //查询结果用id排序，降序desc，升序asc
        if (cursor.moveToFirst()) {
            do {
                CommentItemEntity commentItemEntity = new CommentItemEntity();
                commentItemEntity.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
                commentItemEntity.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                commentItemEntity.setHost_name(cursor.getString(cursor.getColumnIndex("host_name")));
                commentItemEntity.setName(cursor.getString(cursor.getColumnIndex("name")));
                commentItemEntity.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                commentItemEntity.setReason(cursor.getInt(cursor.getColumnIndex("reason")));
                commentItemEntity.setComment(cursor.getString(cursor.getColumnIndex("comment")));
                list.add(commentItemEntity);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    //从数据库读取评论，返回Map
    public Map<Integer,CommentItemEntity> loadMapCommentItemEntity() {

        Map<Integer,CommentItemEntity> map = new TreeMap<Integer,CommentItemEntity>(new Comparator<Integer>() {  //将获取到的评论列表排序
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs.compareTo(lhs);   // 降序排序, 默认为升序
            }
        });
        int sid;  //新闻id
        String icon;
        String host_name;
        String name;
        int score;
        int reason;
        String comment;
        //Cursor cursor = db.query("NewsEntity", null, null, null, null, null, null);
        Cursor cursor = db.query(COMMENT_DB, null, null, null, null, null, "sid desc");   //查询结果用id排序，降序desc，升序asc
        if (cursor.moveToFirst()) {
            do {
                CommentItemEntity commentItemEntity = new CommentItemEntity();
                sid = cursor.getInt(cursor.getColumnIndex("sid"));
                commentItemEntity.setSid(sid);
                icon = cursor.getString(cursor.getColumnIndex("icon"));
                commentItemEntity.setIcon(icon);
                host_name = cursor.getString(cursor.getColumnIndex("host_name"));
                commentItemEntity.setHost_name(host_name);
                name = cursor.getString(cursor.getColumnIndex("name"));
                commentItemEntity.setName(name);
                score = cursor.getInt(cursor.getColumnIndex("score"));
                commentItemEntity.setScore(score);
                reason = cursor.getInt(cursor.getColumnIndex("reason"));
                commentItemEntity.setReason(reason);
                comment = cursor.getString(cursor.getColumnIndex("comment"));
                commentItemEntity.setComment(comment);
                map.put(sid, commentItemEntity);
//                //map.put(icon, commentItemEntity);
//                //map.put(host_name, commentItemEntity);
//               // map.put(name, commentItemEntity);
//                map.put(score, commentItemEntity);
//                map.put(reason, commentItemEntity);
//               // map.put(comment, commentItemEntity);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return map;
    }
}
