package com.rojo.travel.inbox;

/**
 * Created by limadawai on 19/04/18.
 */

public class InboxDataSet {

    String subject, message;

    public InboxDataSet(){}

    public InboxDataSet(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
