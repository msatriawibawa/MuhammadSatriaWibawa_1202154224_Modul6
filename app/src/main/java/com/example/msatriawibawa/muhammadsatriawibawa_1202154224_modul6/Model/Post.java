package com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6.Model;

/**
 * Created by M. Satria Wibawa on 01/04/2018.
 */

public class Post {
    String idImage;
    private String idUser;
    private String username;
    private String judulPost;
    private String post;
    private String image;
    private long timestamp;

    public Post() {
    }

    public Post(String idImage, String idUser, String username, String judulPost, String post, String image, long timestamp) {
        this.idImage = idImage;
        this.idUser = idUser;
        this.username = username;
        this.judulPost = judulPost;
        this.post = post;
        this.image = image;
        this.timestamp = timestamp;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJudulPost() {
        return judulPost;
    }

    public void setJudulPost(String judulPost) {
        this.judulPost = judulPost;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
