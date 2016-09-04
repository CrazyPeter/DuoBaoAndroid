package com.fozoto.duobao.bean;

import java.util.List;

/**
 * Created by qingyan on 16-8-24.
 */

public class HomeJson {
    private boolean status;
    private List<Home> homes;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Home> getHomes() {
        return homes;
    }

    public void setHomes(List<Home> homes) {
        this.homes = homes;
    }

}
