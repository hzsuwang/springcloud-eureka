package com.iterror.springcloud.ribbon.domain;

/**
 * Created by tony.yan on 2017/7/17.
 */
public class UserDO implements java.io.Serializable {

    private long   userId;

    private String name;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
