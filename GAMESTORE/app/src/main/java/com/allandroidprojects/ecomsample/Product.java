package com.allandroidprojects.ecomsample;

/**
 * Created by eduxh on 30/06/2017.
 */

public class Product{

    String name;
    String desc;
    String price;
    String img;
    String shortDesc;
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
