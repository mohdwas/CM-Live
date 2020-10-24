package com.e.cmlive.models;

public class CheckPhoneModel {
    /*{
        "message": "Phone number already exists",
            "phone_number": "6395584895",
            "status": false
    }*/

    private String message;
    private boolean status;
    private String phone_number;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
