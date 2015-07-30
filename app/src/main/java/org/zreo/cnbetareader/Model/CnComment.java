package org.zreo.cnbetareader.Model;


public class CnComment {


    private int imageView1;
    private String userName;
    private String testComment;
    private String support;
    private String against;
    private int commentMenu;

    public CnComment() {
    }

    public CnComment(String userName, int imageId, String testComment, String support, String against,int commentMenu) {
        this.userName = userName;
        this.imageView1 = imageId;
        this.testComment = testComment;
        this.support= support;
        this.against = against;
        this.commentMenu = commentMenu;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImageId(int imageId) {
        this.imageView1 = imageId;
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
        return imageView1;
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

