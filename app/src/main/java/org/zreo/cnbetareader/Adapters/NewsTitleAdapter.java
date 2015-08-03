package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.zreo.cnbetareader.Entitys.NewsEntity;
import org.zreo.cnbetareader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by guang on 2015/7/24. 实现视图与数据的绑定
 */

public class NewsTitleAdapter extends BaseAdapter{

    private int resourceId;
    private List<NewsEntity> listItem;
    private Context mContext;

    /**构造函数*/
   public NewsTitleAdapter(Context context, int textViewResourcedId, List<NewsEntity> objects) {
        super();
        resourceId = textViewResourcedId;
        listItem = objects;
        mContext = context;
        initImageLoader();   //初始化图片加载器

    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.newsTitle = (TextView) view.findViewById(R.id.news_title);
            viewHolder.newsContent = (TextView) view.findViewById(R.id.news_summary);
            viewHolder.publishTime = (TextView) view.findViewById(R.id.news_publish_time);
            viewHolder.titleImage = (ImageView) view.findViewById(R.id.news_picture);
            viewHolder.commentNumber = (TextView) view.findViewById(R.id.news_comment_number);
            viewHolder.readerNumber = (TextView) view.findViewById(R.id.news_reader_number);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();  //重新获取viewHolder
        }
        viewHolder.newsTitle.setText(listItem.get(position).getTitle());
        //格式化输出新闻简介
        String homeText = listItem.get(position).getHometext().replace("<p>", "").replace("<strong>", "")
                                                            .replace("</p>", "").replace("</strong>", "");
        viewHolder.newsContent.setText(homeText);
        viewHolder.publishTime.setText(listItem.get(position).getInputtime());
        //viewHolder.imageUrl = listItem.get(position).getThumb();  //获取图片地址
        imageLoader.displayImage(listItem.get(position).getThumb(), viewHolder.titleImage, options);
        viewHolder.commentNumber.setText(String.valueOf(listItem.get(position).getComments()));
        viewHolder.readerNumber.setText(String.valueOf(listItem.get(position).getCounter()));
        return view;
    }
    class ViewHolder {
        private TextView newsTitle;     //标题
        private TextView newsContent;   //简介
        private TextView publishTime;   //发表时间
        private ImageView titleImage;    //显示图片
        private TextView commentNumber; //评论数
        private TextView readerNumber;  //阅读数
        //private String imageUrl;      //图片地址
    }

    ImageLoader imageLoader;  //图片加载器对象
    DisplayImageOptions options;  ////显示图片的配置

    /**初始化图片加载器*/
    public void  initImageLoader(){

        imageLoader = ImageLoader.getInstance();  //获取图片加载器对象
        //File cacheDir = StorageUtils.getOwnCacheDirectory(mContext, "imageloader/Cache");   //自定义缓存路径
        //File cacheDir = StorageUtils.getCacheDirectory(mContext);   //缓存文件夹路径

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
                .threadPoolSize(3) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
                //.diskCache(new UnlimitedDiskCache(cacheDir)) // default 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())  // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .build(); //开始构建

        imageLoader.init(config);   //Initialize ImageLoader with configuration.


        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.news_title_default_image) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.news_title_default_image) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.news_title_default_image) // 设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
                .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中  //开启缓存后默认会缓存到外置SD卡如下地址(/sdcard/Android/data/[package_name]/cache).
                .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
                //.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565) // default 设置图片的解码类型
                //.displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
                .build();

        //imageLoader.displayImage(imageUrl, viewHolder.imageView, options);

        /*public void onClearMemoryClick(View view) {
            Toast.makeText(this, 清除内存缓存成功, Toast.LENGTH_SHORT).show();
            ImageLoader.getInstance().clearMemoryCache();  // 清除内存缓存
        }

        public void onClearDiskClick(View view) {
            Toast.makeText(this, 清除本地缓存成功, Toast.LENGTH_SHORT).show();
            ImageLoader.getInstance().clearDiskCache();  // 清除本地缓存
        }*/
    }
}

