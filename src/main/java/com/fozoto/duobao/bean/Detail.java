package com.fozoto.duobao.bean;

import java.util.List;

/**
 * Created by qingyan on 16-8-25.
 */

public class Detail {
    private String explains;
    private List<Image> images;

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
