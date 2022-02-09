package com.perms.demo.utils.constant;

/**
 * 通用常量信息
 *
 * @author cxw
 * @date 2022/2/8
 */
public class Constants {
    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 是否显示（是）
     */
    public static final String YES_VISIBLE = "1";

    /**
     * 是否显示（否）
     */
    public static final String NO_VISIBLE = "2";

    /**
     * 是否缓存（是）
     */
    public static final String YES_CACHE = "1";

    /**
     * 是否缓存（否）
     */
    public static final String NO_CACHE = "2";

    /**
     * 是否外链（是）
     */
    public static final String YES_FRAME = "1";

    /**
     * 是否外链（否）
     */
    public static final String NO_FRAME = "2";

    /**
     * 菜单类型（目录）
     */
    public static final String TYPE_DIR = "1";

    /**
     * 菜单类型（菜单）
     */
    public static final String TYPE_MENU = "2";

    /**
     * 菜单类型（按钮）
     */
    public static final String TYPE_BUTTON = "3";

    /**
     * Layout组件标识
     */
    public final static String LAYOUT = "Layout";

    /**
     * ParentView组件标识
     */
    public final static String PARENT_VIEW = "ParentView";

    /**
     * InnerLink组件标识
     */
    public final static String INNER_LINK = "InnerLink";
}
