package com.alg.shopping.contants;

import android.os.Environment;

import org.xutils.x;

import java.io.File;

/**
 * Created by Lenovo on 2017/12/13.
 */

public class Constants {
    public static String SQL_NAME = "alg.db";//数据库名
    public static int SQL_VERSION = 1001;//数据库版本号
    public static String DB_DATA = x.app().getCacheDir() + File.separator + x.app().getPackageName() + File.separator;
    public static String DB_DATA_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + x.app().getPackageName() + File.separator;

    public static final String IMAGE_CACHE_DIR = DB_DATA_PATH + "/image_cache";//Glide缓存路劲
    public static String WX_APP_ID = "wxc8e42a5cfe6397ea";//微信APPid
    public static String QQ_APPKEY_ID = "1106763664";//QQAPPid

    public static String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdfmcTb9YpK/PmXYwYis6ZJjrcFv0D88sGIYiMO3jG71EcMAEpZvPEDNncexJy9/FutxUHWYOHfjfnewI7hxCm5NkwDQE3agmYKupRUYuMn8Z9s5YBKXWLF3APdqqRVPtLobtxUSINpA0SyFLtXNLhR3IdrafyVOnQmscm4tRoMwIDAQAB";
    public static String PRIVATEKEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ1+ZxNv1ikr8+ZdjBiKzpkmOtwW/QPzywYhiIw7eMbvURwwASlm88QM2dx7EnL38W63FQdZg4d+N+d7AjuHEKbk2TANATdqCZgq6lFRi4yfxn2zlgEpdYsXcA92qpFU+0uhu3FRIg2kDRLIUu1c0uFHch2tp/JU6dCaxybi1GgzAgMBAAECgYB/dQKaOlIJc+apMl/kpMFaNBRCdeBByJUv2weU5bRy2s67jyYBia4RpYzS4E8n9/zN6yD7l7fYeY9oqKY/4qjYAGIhu+xZq3l9O5BofXo+JYkR46r/Uer7R9n0lCcRTH9gYmENrpbplsC3ZVKayKMM/8RKicuAFclobBu4NIQnkQJBAOT0PKrHM6USWU2S8ybm/15UyoGXC6UYeSLe36r9VYUsgrA/tHHGGY42ggL7KTPVdu5XOlsPFR0U+pgAR5qTw2cCQQCwGSV5p7fztj7UoD3T+uxN/N3yZ4LIA3YCZ3tQZ11UpUy+PGHBV/6jXbGhjD3utnlTh1zFuVu19MwtIrHBYeFVAkEAj75voGD0obzFaJjMJC5/QgBGK9pjK0IUO7pl/vqSiMwLORCl/5B6VvkN40VA9xQcWUNWlfkALoQPWQND8V64HQJBAI9EtieG23MtN2r3v7Wh9PTyIIRh0JX3st/73uioX9dI8Ono6ENL2wCAbs8W8SAwVv8tJr46srGTqzWhf2rsvk0CQHeMrF4mbdM8HktC0HUPERHsX3N1L+EqeObzZABp0EUDob0w1vDjQTIrvNv6BYRBUlPpZJCL+0f5wymv1SF3uis=";
    public static String registrationId = "";//激光返回的ID
    public static final String USERINFOKEY ="USERINFOKEY";//用户信息KEY

    public final static int REQUEST_CODE_SCAN = 1001;//首页二维码扫描


    public final static String SPACE = "imagexb";//空间名
    public final static String OPERATER = "xbtiangou";//操作员
    public final static String PASSWORD = "xbtiangou";//密码

    public static final String SHAREDPRENCEDATA ="SHAREDPRENCEDATA";//Sharedprence存储key

    public final static int BALANCE_FLAG = 0;
    public final static int PIN_FLAG = 1;
    public final static int VOUCHERS_FLAG = 2;
    public final static int ALIPAY_FLAG = 1004;
    public final static int WXPAY_FLAG = 1005;

    public static final String HTMLADDRESS ="HTMLADDRESS";//所有html地址
    public static final String USERINFO ="USERINFO";//用户账户信息
    public static final String USERBASEINFO ="USERBASEINFO";//用户基本信息

    public static int PAGENUM = 20;//每页数据条数
    public static long CODELONG = 60 * 1000;//获取验证码时间
    public static long countDownInterval = 1 * 1000;//验证码时间间隔


}
