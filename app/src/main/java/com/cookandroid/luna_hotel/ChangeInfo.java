package com.cookandroid.luna_hotel;

// 회원 정보를 변경할 때 변경한 값을 가져오게 해 주는 클래스입니다.

public class ChangeInfo {

    public String Name;
    public String HP;
    public String Email;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getHP() {
        return HP;
    }

    public void setHP(String HP) {
        this.HP = HP;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

}