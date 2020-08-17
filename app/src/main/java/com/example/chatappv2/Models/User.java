package com.example.chatappv2.Models;

public class User {

    private String id;
    private String username;
    private String imageURL;
    private String status;
    private int txtSize;
    private boolean notify;

    public User(String id, String username, String imageUrl,String status,int txtSize, boolean notify) {
        this.id = id;
        this.username = username;
        this.imageURL = imageUrl;
        this.status = status;
        this.txtSize = txtSize;
        this.notify = notify;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageURL;
    }

    public void setImageUrl(String imageUrl) {
        this.imageURL = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTxtSize() {
        return txtSize;
    }

    public void setTxtSize(int txtSize) {
        this.txtSize = txtSize;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
