package entity;

import java.util.Date;

public class Account {
    private String name;
    private int age;
    private String address;
    private Date birthDay;

    public Account() {
    }

    public Account(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Account(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}