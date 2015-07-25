package org.zreo.cnbetareader.Model;

/**
 * Created by guang on 2015/7/24.
 */
public class News {
    private String newsTitle;
    private String newsContent;
    private String publishTime;
    private int imageId;
    private int commentNumber;
    private int readerNumber;

    public News(){
    }

    public News(String newsTitle, String newsContent, String publishTime, int imageId,
                                int commentNumber, int readerNumber) {
        this.newsTitle = newsTitle;
        this.newsContent = newsContent;
        this.publishTime = publishTime;
        this.imageId = imageId;
        this.commentNumber = commentNumber;
        this.readerNumber = readerNumber;
    }

    public String getNewsTitle(){
        return  newsTitle;
    }

    public String getNewsContent(){
        return  newsContent;
    }

    public String getPublishTime(){
        return  publishTime;
    }

    public int getImageId(){
        return imageId;
    }

    public int getCommentNumber(){
        return commentNumber;
    }

    public int getReaderNumber(){
        return readerNumber;
    }

    public void setNewsTitle(String newsTitle){
        this.newsTitle = newsTitle;
    }

    public void setNewsContent(String newsContent){
        this.newsContent = newsContent;
    }

    public void setPublishTime(String publishTime){
        this.publishTime = publishTime;
    }

    public void setImageId(int imageId){
        this.imageId = imageId;
    }

    public void setCommentNumber(int commentNumber){
        this.commentNumber = commentNumber;
    }

    public void setReaderNumber(int readerNumber){
        this.readerNumber = readerNumber;
    }




}
