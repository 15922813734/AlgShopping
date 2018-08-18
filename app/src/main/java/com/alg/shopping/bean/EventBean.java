package com.alg.shopping.bean;

import java.io.Serializable;

/**
 * Created by QiuQiu on 2017/12/17.
 */

public class EventBean implements Serializable{
    String flag = "";
    Object obj;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
