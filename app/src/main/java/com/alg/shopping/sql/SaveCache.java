package com.alg.shopping.sql;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * sql缓存数据
 * Description :数据库表
 */
@Table(name = "SaveCache")
public class SaveCache {
    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "keyStr")
    private String keyStr;
    @Column(name = "valueStr")
    private String valueStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}
