package com.fozoto.duobao.entity;

import java.io.Serializable;

/**
 * Created by qingyan on 16-8-2.
 */

public class Goods implements Serializable{

    private int id;         // 主键

    private int per;        // 每份多少人次

    private int total;      // 本期商品总需人次

    private int price;      // 商品价格 price = per * total

    private String trait;   // 本期商品的额外描述,如十元商品、百元商品、海购商品，这里是图片地址等

    private String intro;   // 文字介绍

    private String remind;  // 额外的红字提醒

    private String image;   // 商品的图片

    private String explains; // 重要说明

    private String available;    // 该商品是否参与夺宝(new/old)

    private String time;    // 商品创建时间

    private String retime;  // 商品重新参与夺宝的时间

    private String cate;     // 商品分类

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRetime() {
        return retime;
    }

    public void setRetime(String retime) {
        this.retime = retime;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", per=" + per +
                ", total=" + total +
                ", price=" + price +
                ", trait='" + trait + '\'' +
                ", intro='" + intro + '\'' +
                ", remind='" + remind + '\'' +
                ", image='" + image + '\'' +
                ", explains='" + explains + '\'' +
                ", available='" + available + '\'' +
                ", time='" + time + '\'' +
                ", retime='" + retime + '\'' +
                ", cate='" + cate + '\'' +
                '}';
    }
}
