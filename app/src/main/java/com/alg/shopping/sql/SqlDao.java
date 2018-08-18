package com.alg.shopping.sql;

import android.text.TextUtils;

import com.alg.shopping.contants.Constants;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;

/**
 * 数据库操作
 *
 * @author 邱强 创建于 2016/3/29 10:35
 */
public class SqlDao {
    private static SqlDao intance = null;
    private static DbManager mDbManager;

    public static SqlDao getInance() {
        if (intance == null) {
            intance = new SqlDao();
        }
        if (mDbManager == null) {
            mDbManager = x.getDb(getDaoConfig());
        }
        return intance;
    }

    private static DbManager.DaoConfig daoConfig;

    private static DbManager.DaoConfig getDaoConfig() {
        File file = new File(Constants.DB_DATA);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    .setDbName(Constants.SQL_NAME)
                    .setDbVersion(Constants.SQL_VERSION)
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            // 开启WAL, 对写入加速提升巨大
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    })
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            // TODO: ...
                            // db.dropTable(...);
                        }
                    });
        }
        if (file.exists()) {
            daoConfig.setDbDir(file);
        }
        return daoConfig;
    }
    /**
     * 查询数据
     *
     * @param keyStr 方法名
     * @return
     */
    public String query(String keyStr) {
        try {
            if (mDbManager == null) {
                mDbManager = x.getDb(getDaoConfig());
            }
            SaveCache persons = mDbManager.selector(SaveCache.class).where("keyStr", "=", keyStr).findFirst();
            if (persons != null) {
                return persons.getValueStr();
            } else {
                return "";
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void insert(String keyStr, String valueStr) {
        String key = query(keyStr);
        if (TextUtils.isEmpty(key)) {
            try {
                SaveCache person = new SaveCache();
                person.setValueStr(valueStr);
                person.setKeyStr(keyStr);
                if (mDbManager == null) {
                    mDbManager = x.getDb(getDaoConfig());
                }
                mDbManager.save(person);
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            update(keyStr, valueStr);
        }
    }
    public void delete(String keyStr){
        WhereBuilder wb = WhereBuilder.b("keyStr", "=", keyStr);
        try {
            if (mDbManager == null) {
                mDbManager = x.getDb(getDaoConfig());
            }
            mDbManager.delete(SaveCache.class,wb);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    private void update(String keyStr, String valueStr) {
        try {
            WhereBuilder wb = WhereBuilder.b("keyStr", "=", keyStr);
            KeyValue kv = new KeyValue("valueStr", valueStr);
            if (mDbManager == null) {
                mDbManager = x.getDb(getDaoConfig());
            }
            mDbManager.update(SaveCache.class, wb, kv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
