package com.cookandroid.luna_hotel;

// 회원 정보를 받아오기 위한 UserInfo 클래스입니다.

public class UserInfo {

    public String CODE;
    public String ID;
    public String Name;
    public String PW;
    public String Ssn;
    public String Gender;
    public String HP;
    public String Email;

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public String getSsn() {
        return Ssn;
    }

    public void setSsn(String Ssn) {
        this.Ssn = Ssn;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
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
