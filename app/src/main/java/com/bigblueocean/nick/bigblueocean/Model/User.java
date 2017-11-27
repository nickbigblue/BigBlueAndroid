package com.bigblueocean.nick.bigblueocean.Model;

/**
 * Created by nick on 11/22/17.
 */

public class User {
    private boolean bigBlue;
    private String company;
    private String name;
    private String phone;
    private String email;

    public User(String email, String UUID){
        this.bigBlue = true;
    }

    public boolean isBigBlue(){
        return bigBlue;
    }
}
