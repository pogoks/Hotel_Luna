package com.cookandroid.luna_hotel;

// 공지사항 목록을 받아오기 위한 Luna_News_ItemData 클래스입니다.

public class NewsInfo {

    public String NewsCODE;
    public String NewsTitle;
    public String NewsMain;
    public String NewsWriter;
    public String NewsDate;

    public String getNewsCODE() {
        return NewsCODE;
    }

    public void setNewsCODE(String NewsCODE) {
        this.NewsCODE = NewsCODE;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String NewsTitle) {
        this.NewsTitle = NewsTitle;
    }

    public String getNewsMain() {
        return NewsMain;
    }

    public void setNewsMain(String NewsMain) {
        this.NewsMain = NewsMain;
    }

    public String getNewsWriter() {
        return NewsWriter;
    }

    public void setNewsWriter(String NewsWriter) {
        this.NewsWriter = NewsWriter;
    }

    public String getNewsDate() {
        return NewsDate;
    }

    public void setNewsDate(String NewsDate) {
        this.NewsDate = NewsDate;
    }

}
