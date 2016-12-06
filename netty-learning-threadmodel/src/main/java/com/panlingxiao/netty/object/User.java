package com.panlingxiao.netty.object;

import java.io.Serializable;

/**
 * Created by panlingxiao on 2016/12/6.
 */
public class User implements Serializable{

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
