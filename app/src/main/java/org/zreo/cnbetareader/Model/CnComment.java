package org.zreo.cnbetareader.Model;


public class CnComment {


    private int imageView1;
    private String userName;
    private String testComment;
    private String supportAgainst;
    private int commentMenu;

    public CnComment() {
    }

    public CnComment(String userName, int imageId, String testComment, String supportAgainst, int commentMenu) {
        this.userName = userName;
        this.imageView1 = imageId;
        this.testComment = testComment;
        this.supportAgainst = supportAgainst;
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

    public void setSupportAgainst(String supportAgainst) {
        this.supportAgainst = supportAgainst;
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

    public String getsupportAgainst() {
        return supportAgainst;
    }

    public int getCommentMenu() {
        return commentMenu;
    }
}

