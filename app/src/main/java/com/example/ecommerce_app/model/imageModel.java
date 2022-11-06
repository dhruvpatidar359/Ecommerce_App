package com.example.ecommerce_app.model;

// Data class

public class imageModel {


    int image ;
    int id ;
    String image_name;

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }




    public imageModel(int image, int id , String image_name) {
        this.image = image;
        this.id = id;
        this.image_name = image_name;
    }

    public imageModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
