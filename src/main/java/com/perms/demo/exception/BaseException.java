package com.perms.demo.exception;

/**
 * 基础异常
 *
 * @author ruoyi
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BaseException(Throwable e) {
        super(e.getMessage(), e);
    }

    public BaseException(String module, Integer code, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String defaultMessage) {
        this(null, null, defaultMessage);
    }

    public BaseException(String module, String defaultMessage) {
        this(module, null, defaultMessage);
    }

    public BaseException(Integer code, String defaultMessage) {
        this(null, code,  defaultMessage);
    }

    @Override
    public String getMessage() {
        return defaultMessage;
    }

    public String getModule() {
        return module;
    }

    public Integer getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}