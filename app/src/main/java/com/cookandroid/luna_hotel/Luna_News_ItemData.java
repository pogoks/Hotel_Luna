package com.cookandroid.luna_hotel;

// 공지사항 목록을 받아오기 위한 Luna_News_ItemData 클래스입니다.

public class Luna_News_ItemData {

    public String strList_Code;
    public String strList_Title;
    public String strList_Main;
    public String strList_Writer;
    public String strList_Date;

    public Luna_News_ItemData(String strList_Code, String strList_Title, String strList_Main, String strList_Writer, String strList_Date) {

        this.strList_Code = strList_Code;
        this.strList_Title = strList_Title;
        this.strList_Main = strList_Main;
        this.strList_Writer = strList_Writer;
        this.strList_Date = strList_Date;

    }

    public String getStrList_Code() {
        return this.strList_Code;
    }

    public String getStrList_Title() {
        return this.strList_Title;
    }

    public String getStrList_Main() {
        return this.strList_Main;
    }

    public String getStrList_Writer() {
        return this.strList_Writer;
    }

    public String getStrList_Date() {
        return this.strList_Date;
    }

}
