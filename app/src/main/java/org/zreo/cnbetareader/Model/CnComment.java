package org.zreo.cnbetareader.Model;

import java.security.PublicKey;

/**
 * Created by Administrator on 2015/7/22.
 */
public class CnComment {

    private String userName;
    private int imageId;
    private String testComment;
    private String support;
    private String against;
    private int commentMenu;

    public CnComment() {
    }

    public CnComment(String userName, int imageId, String testComment, String support, String against, int commentmenu) {
        this.userName = userName;
        this.imageId = imageId;
        this.testComment = testComment;
        this.support = support;
        this.against = against;
        this.commentMenu = commentMenu;
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

    public void setSupport(String support) {
        this.support = support;
    }

    public void setAgainst(String against) {
        this.against = against;
    }

    public void setCommentMenu(int commentMenu) {
        this.commentMenu = commentMenu;
    }

    public String getUserName() {
        return userName;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTestComment() {
        return testComment;
    }

    public String getSupport() {
        return support;
    }

    public String getAgainst() {
        return against;
    }

    public int getCommentMenu() {
        return commentMenu;
    }
}

