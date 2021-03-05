package com.example.login_test3;

public class Memodata {

    private String memoStr;
    private String timeStr;
    private int keynumber;

    public String getMemoStr() {
        return memoStr;
    }

    public void setMemoStr(String memoStr) {
        this.memoStr = memoStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public int getKeynumber() {
        return keynumber;
    }

    public void setKeynumber(int keynumber) {
        this.keynumber = keynumber;
    }

    public Memodata(int keynumber) {
        this.keynumber = keynumber;
    }

    public Memodata() {

    }

    public Memodata(String memoStr, String timeStr) {
        this.memoStr = memoStr;
        this.timeStr = timeStr;
    }
}
