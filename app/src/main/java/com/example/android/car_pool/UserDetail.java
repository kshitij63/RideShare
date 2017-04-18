package com.example.android.car_pool;

/**
 * Created by pussyhunter on 11/03/2017.
 */

public class UserDetail {
    private String first_name;
    private String last_name;
    private String email;
    private String mobile;
    private int login_flag;
    private int Signup_flag;
    private String password;

    UserDetail(String first_name,String last_name,String email,String mobile,String password,int login_flag,int Signup_flag )
    {
        this.first_name=first_name;
        this.last_name=last_name;
        this.email=email;
        this.mobile=mobile;
        this.login_flag=login_flag;
        this.Signup_flag=Signup_flag;
        this.password=password;

    }

    UserDetail(){}


    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public int getLogin_flag() {
        return login_flag;
    }
    public void setLogin_flag(int login_flag){
        this.login_flag=login_flag;
    }
    public int getSignup_flag(){return Signup_flag; }


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }
    public String getPassword(){return  password;}
}
