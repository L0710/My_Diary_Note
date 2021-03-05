package com.example.login_test3;

import android.net.Uri;

public class Diarydata {

    private String diarytext;
    private String time;
    private int keynumber;
    private Uri iconmuri;


    public String getDiarytext() {
        return diarytext;
    }

    public void setDiarytext(String diarytext) {
        this.diarytext = diarytext;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getKeynumber() {
        return keynumber;
    }

    public void setKeynumber(int keynumber) {
        this.keynumber = keynumber;
    }

    public Uri getIconmuri() {
        return iconmuri;
    }

    public void setIconmuri(Uri iconmuri) {
        this.iconmuri = iconmuri;
    }

    public Diarydata(String diarytext, String time, Uri iconmuri) {
        this.diarytext = diarytext;
        this.time = time;
        this.iconmuri = iconmuri;
    }

    public Diarydata(int keynumber) {
        this.keynumber = keynumber;
    }

    public Diarydata() {

    }

}
