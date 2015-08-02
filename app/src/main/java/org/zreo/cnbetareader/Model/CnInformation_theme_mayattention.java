package org.zreo.cnbetareader.Model;

/**
 * Created by Admin on 2015/7/30.
 */
public class CnInformation_theme_mayattention {
    String themetype;
    String content;
    String firstword;
    public CnInformation_theme_mayattention(){}
    public CnInformation_theme_mayattention(String themrtype, String firstword, String content){
        this.themetype=themrtype;
        this.firstword=firstword;
        this.content=content;
    }

    public String getThemetype() {
        return themetype;
    }

    public void setThemetype(String themetype) {
        this.themetype = themetype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstword() {
        return firstword;
    }

    public void setFirstword(String firstword) {
        this.firstword = firstword;
    }
}
