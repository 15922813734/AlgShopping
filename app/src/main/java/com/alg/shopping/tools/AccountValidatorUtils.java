package com.alg.shopping.tools;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lenovo on 2017/12/14.
 */

public class AccountValidatorUtils {

    public static final String EMPTY_STR = "^[^ ]+$";
    //银行卡号
    public static final String REGEX_BANK = "^([1-9]{1})(\\d{14}|\\d{18})$";

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";
    /**
     * 正则表达式：验证验证码
     */
    public static final String REGEX_VERIFICATION = "^[0-9]*$";
    /**
     * 正Double正则表达式 >=0
     * 任意正整数，正小数（小数位不超过2位）
     */
    public static final String  DOUBLE_NEGATIVE ="^(([1-9][0-9]*)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))$";
    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[A-Za-z0-9]{6,16}$";
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[0-9]{10}$";
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\\u4e00-\\u9fa5]{1,16}$";
    /**
     * 正则表达式：验证是否存在汉字
     */
    public static final String REGEX_HAVE_CHINESE = "[\\u4e00-\\u9fa5]";
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * 正则表达式：验证数字
     */
    public static final String REGEX_NUMBER = "^[0-9]*$";

    public static final String XINYONG_CODE = "[1-9A-GY]{1}[1239]{1}[1-5]{1}[0-9]{5}[0-9A-Z]{10}";

    public static boolean isEmpty(String str){
        return TextUtils.isEmpty(str);
    }

    public static boolean isXinYong(String xinyongStr){
        return !isEmpty(xinyongStr) && Pattern.matches(XINYONG_CODE, xinyongStr);
    }
    public static boolean isBANK(String bank){
        return !isEmpty(bank) && Pattern.matches(REGEX_BANK, bank);
    }

    public static boolean isNumber(String num){
        return !isEmpty(num) && Pattern.matches(REGEX_NUMBER, num);
    }

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isDOUBLENEGATIVE(String username) {
        return !isEmpty(username) && Pattern.matches(DOUBLE_NEGATIVE, username);
    }
    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return !isEmpty(username) && Pattern.matches(REGEX_USERNAME, username);
    }
    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return !isEmpty(password) && Pattern.matches(REGEX_PASSWORD, password);
    }
    /**
     * 校验验证码
     *
     * @param inviteCode
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isVerificationCode(String inviteCode) {
        return !isEmpty(inviteCode) && Pattern.matches(REGEX_VERIFICATION, inviteCode);
    }
    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return !TextUtils.isEmpty(mobile);
    }
    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return !isEmpty(email) && Pattern.matches(REGEX_EMAIL, email);
    }
    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return !isEmpty(chinese) && Pattern.matches(REGEX_CHINESE, chinese);
    }
    /**
     * 校验是否存在汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isHaveChinese(String chinese) {
        boolean isHave = false;
        if(!isEmpty(chinese)){
            Pattern p = Pattern.compile(REGEX_HAVE_CHINESE);
            Matcher m = p.matcher(chinese);
            if (m.find()) {
                isHave = true;
            }
        }
        return  isHave;
    }
    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return !isEmpty(idCard) && Pattern.matches(REGEX_ID_CARD, idCard);
    }
    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return !isEmpty(url) && Pattern.matches(REGEX_URL, url);
    }
    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return !isEmpty(ipAddr) && Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
}
