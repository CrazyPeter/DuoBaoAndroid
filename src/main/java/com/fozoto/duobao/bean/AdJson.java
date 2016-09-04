package com.fozoto.duobao.bean;

import java.util.List;

/**
 * Created by qingyan on 16-8-23.
 */

public class AdJson {
    private boolean status;
    private List<Ad> ads;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }
}
