package com.example.login_test3;



public class Profile_info {

    private String nick;
    private String message;
    private String photoUri;

    //초기화 만들어줌
    public Profile_info(String nick, String message, String photoUri) {
        this.nick = nick;
        this.message = message;
        this.photoUri = photoUri;
    }


    public Profile_info(String nick, String message) {
        this.nick = nick;
        this.message = message;
    }

    //get, set 만들어주기
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
