package com.bigblueocean.nick.bigblueocean.model;

/**
 * Created by nick on 11/22/17.
 */

public class User {

    private int verified;
    private String company;
    private String name;
    private String phone;
    private String email;
    private String uid;
    private String clientNmr;
    private int type;

    public User(){

    }

    public User(int verified, String company, String name, String phone, String email, String uid, String clientNmr, int type) {
        this.verified = verified;
        this.company = company;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.uid = uid;
        this.clientNmr = clientNmr;
        this.type = type;
    }

    public int isVerified() {
        return verified;
    }

    public String getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public String getClientNmr() {
        return clientNmr;
    }

    public int getType() {
        return type;
    }
}
