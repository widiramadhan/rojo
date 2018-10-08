package com.rojo.travel.akun;

/**
 * Created by kangyasin on 10/01/2018.
 */

public class LoginDataSet {

    String status, uid, email, password, message;

    public LoginDataSet() {
    }

    public LoginDataSet(String status, String uid, String email, String password, String message) {
        this.status = status;
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
