package com.example.colearn.components;


public class User {
    private static User user = null;
    private String id;
    private String account;
    private String password;
    private String nickname;
    private String gender;

    public static void setUser(User user) {
        User.user = user;
    }

    public User(){}

    public User(String id, String account, String password, String nickname, String gender) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
    }

    public static User getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
