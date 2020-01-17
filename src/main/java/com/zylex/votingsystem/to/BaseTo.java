package com.zylex.votingsystem.to;

import com.zylex.votingsystem.HasId;

import java.io.Serializable;

public abstract class BaseTo implements HasId, Serializable {

    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}