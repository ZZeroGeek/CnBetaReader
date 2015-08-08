package org.zreo.cnbetareader.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.zreo.cnbetareader.Entitys.NewsEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by guang on 2015/8/8.
 */
public class CollectionDatabase {


    public static final String DB_NAME = "Collection";  //数据库名
    public static final int VERSION = 1;   //数据库版本
    private static CollectionDatabase collectionDatabase;
    private SQLiteDatabase db;

    //将构造方法私有化
    private CollectionDatabase(Context context) {
        NewsTitleOpenHelper dbHelper = new NewsTitleOpenHelper(
                context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    //获取CollectionDatabase的实例
    public synchronized static CollectionDatabase getInstance(Context context) {
        if (collectionDatabase == null) {
            collectionDatabase = new CollectionDatabase(context);
        }
        return collectionDatabase;
    }

    //将NewsEntity实例存储到收藏数据库
    public void saveCollection(NewsEntity newsEntity) {
        if (newsEntity != null) {
            ContentValues values = new ContentValues();
            values.put("sid", newsEntity.getSid());
            values.put("title", newsEntity.getTitle());
            values.put("hometext", newsEntity.getHometext());
            values.put("comments", newsEntity.getComments());
            values.put("counter", newsEntity.getCounter());
            values.put("inputtime", newsEntity.getInputtime());
            values.put("thumb", newsEntity.getThumb());
            db.insert(DB_NAME, null, values);
        }
    }

    //删除收藏
    public void deleteCollection(NewsEntity newsEntity) {
        db.delete(DB_NAME, "sid = ?", new String[]{String.valueOf(newsEntity.getSid())});
    }



    //从数据库读取收藏信息，返回List
    public List<NewsEntity> loadCollection() {
        List<NewsEntity> list = new ArrayList<NewsEntity>();
        Cursor cursor = db.query(DB_NAME, null, null, null, null, null, "id desc");   //查询结果用id排序，降序desc，升序asc
        if (cursor.moveToFirst()) {
            do {
                NewsEntity newsEntity = new NewsEntity();
                newsEntity.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
                newsEntity.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                newsEntity.setHometext(cursor.getString(cursor.getColumnIndex("hometext")));
                newsEntity.setInputtime(cursor.getString(cursor.getColumnIndex("inputtime")));
                newsEntity.setThumb(cursor.getString(cursor.getColumnIndex("thumb")));
                newsEntity.setComments(cursor.getInt(cursor.getColumnIndex("comments")));
                newsEntity.setCounter(cursor.getInt(cursor.getColumnIndex("counter")));
                list.add(newsEntity);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    //从数据库读取收藏信息，返回Map
    public Map<Integer, NewsEntity> loadMapCollection() {

        Map<Integer, NewsEntity> map = new TreeMap<Integer, NewsEntity>(new Comparator<Integer>() {  //将获取到的新闻列表排序
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs.compareTo(lhs);   // 降序排序, 默认为升序
            }
        });
        int sid;  //新闻id
        Cursor cursor = db.query(DB_NAME, null, null, null, null, null, "id desc");   //查询结果用id排序，降序desc，升序asc
        if (cursor.moveToFirst()) {
            do {
                NewsEntity newsEntity = new NewsEntity();
                sid = cursor.getInt(cursor.getColumnIndex("sid"));
                newsEntity.setSid(sid);
                newsEntity.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                newsEntity.setHometext(cursor.getString(cursor.getColumnIndex("hometext")));
                newsEntity.setInputtime(cursor.getString(cursor.getColumnIndex("inputtime")));
                newsEntity.setThumb(cursor.getString(cursor.getColumnIndex("thumb")));
                newsEntity.setComments(cursor.getInt(cursor.getColumnIndex("comments")));
                newsEntity.setCounter(cursor.getInt(cursor.getColumnIndex("counter")));
                map.put(sid, newsEntity);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return map;
    }
}
