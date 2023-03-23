package com.cookandroid.luna_hotel;

// 리스트 뷰 각각의 아이템에 값을 넣어주는 클래스입니다.
public class Luna_Reservation_ItemData {

    public String strList_Code;
    public String strList_Name;
    public String strList_Hotel;
    public String strList_Room;
    public String strList_reserveDate;
    public String strList_Price;

    public Luna_Reservation_ItemData(String strList_Code, String strList_Name, String strList_Hotel, String strList_Room, String strList_reserveDate, String strList_Price) {

        this.strList_Code = strList_Code;
        this.strList_Name = strList_Name;
        this.strList_Hotel = strList_Hotel;
        this.strList_Room = strList_Room;
        this.strList_reserveDate = strList_reserveDate;
        this.strList_Price = strList_Price;

    }

    public String getStrList_Code() {
        return this.strList_Code;
    }

    public String getStrList_Name() {
        return this.strList_Name;
    }

    public String getStrList_Hotel() {
        return this.strList_Hotel;
    }

    public String getStrList_Room() {
        return this.strList_Room;
    }

    public String getStrList_reserveDate() {
        return this.strList_reserveDate;
    }

    public String getStrList_Price() {
        return this.strList_Price;
    }


}
