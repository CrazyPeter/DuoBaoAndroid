package com.fozoto.duobao.bean;

/**
 * Created by qingyan on 16-8-24.
 */

public class Home {

    private String intro;   // 商品介绍
    private String image;   // 商品的图片
    private String trait;   // 商品额外描述图片地址
    private float degree;     // 商品进度
    private int goodsId;    // 哪个商品
    private int issueId;    // 哪一期

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    @Override
    public String toString() {
        return "Home{" +
                "intro='" + intro + '\'' +
                ", image='" + image + '\'' +
                ", trait='" + trait + '\'' +
                ", degree=" + degree +
                ", goodsId=" + goodsId +
                ", issueId=" + issueId +
                '}';
    }
}
