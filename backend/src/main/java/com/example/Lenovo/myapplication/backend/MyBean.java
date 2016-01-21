package com.example.Lenovo.myapplication.backend;

/** The object model for the data we are sending through endpoints */
public class MyBean {


    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    private boolean hasError;
    private String myData;

    public String getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }

    private String myMessage;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}