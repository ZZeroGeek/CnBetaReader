package org.zreo.cnbetareader.Model;

/**
 * Created by Administrator on 2015/7/22.
 */
public class CnComment {

    private String userName;
    private int imageId;
    private String testComment;
    private int support;
    private int against;
    private int commentmenu;

    public String getUserName() {
        return userName;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTestComment() {
        return testComment;
    }

    public int getSupport() {
        return support;
    }

    public int getAgainst() {
        return against;
    }

    public int getCommentmenu() {
        return commentmenu;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setTestComment(String testComment) {
        this.testComment = testComment;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public void setAgainst(int against) {
        this.against = against;
    }

    public void setCommentmenu(int commentmenu) {
        this.commentmenu = commentmenu;
    }
}