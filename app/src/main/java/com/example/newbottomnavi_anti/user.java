package com.example.newbottomnavi_anti;

public class user {
    String name; //이름
    String gender; //성별

    public user(){} // 생성자 메서드


    //getter, setter 설정
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }




    //값을 추가할때 쓰는 함수, MainActivity에서 addanimal함수에서 사용할 것임.
    public user(String name, String gender){
        this.name = name;
        this.gender = gender;
    }
}
