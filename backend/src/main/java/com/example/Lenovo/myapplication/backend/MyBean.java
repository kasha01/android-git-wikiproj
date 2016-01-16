package com.example.Lenovo.myapplication.backend;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;
    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}