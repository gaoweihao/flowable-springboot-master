package com.flowable.springboot.bean;

import java.io.Serializable;

public class BaseEntity implements Serializable {
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
