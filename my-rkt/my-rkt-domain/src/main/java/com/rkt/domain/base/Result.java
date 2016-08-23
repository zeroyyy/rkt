package com.rkt.domain.base;

import java.util.Hashtable;
import java.util.Map;

public class Result {
    private static final int INIT_MAP_SIZE = 5;
    /**
     * 默认的处理结果常量,为true
     */
    public static final boolean DEFAULT_STATUS_SUCCESS = true;
    /**
     * 附加值的默认key
     */
    public static final String RESULT_KEY_DEFAULT_VALUE = "model";

    /**
     * 默认消息KEY
     */
    public static final String RESULT_KEY_MSG = "msg";

    /**
     * 处理结果: true成功, false失败,默认为true
     */
    private boolean success;
    private int code;
    private Map<String, Object> result;

    private Result() {
    }

    /**
     * 创建一个Result对象,,默认为成功结果
     * @return Result对象
     * @createTime May 13, 2014 11:05:18 AM
     * @author wujianjun
     */
    public static Result create() {
        return create(DEFAULT_STATUS_SUCCESS);
    }

    /**
     * 创建一个Result对象
     * @param isSuccess 指示是否成功的操作结果
     * @return Result对象
     * @createTime May 13, 2014 11:05:54 AM
     * @author wujianjun
     */
    public static Result create(boolean isSuccess) {
        return create(isSuccess, null);
    }

    /**
     * set value of Result.code
     * @param code the code
     * @return this instance
     * @createTime 2014年5月15日 下午12:11:16
     * @author Wang22
     */
    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * get value of Result.code
     * @return the code
     * @createTime 2014年5月15日 下午12:11:37
     * @author Wang22
     */
    public int getCode() {
        return code;
    }

    /**
     * 创建一个Result对象
     * @param isSuccess 指示是否成功的操作结果
     * @param resultInfo Result对象附加结果
     * @return Result对象
     * @createTime May 13, 2014 11:06:25 AM
     * @author wujianjun
     */
    public static Result create(boolean isSuccess,
                                Map<String, Object> resultInfo) {
        return new Result().setSuccess(isSuccess).addResult(resultInfo);
    }

    /**
     * 创建一个Result对象
     * @param isSuccess 指示是否成功的操作结果
     * @param value Result对象附加结果
     * @return Result对象
     * @createTime May 13, 2014 11:06:25 AM
     * @author wujianjun
     */
    public static Result create(boolean isSuccess, Object value) {
        return new Result().setSuccess(isSuccess).addResult(RESULT_KEY_DEFAULT_VALUE,
                value);
    }

    /**
     * 创建一个Result对象
     * @param value 默认为成功的操作,结果对象
     * @return Result对象
     * @createTime May 13, 2014 11:06:25 AM
     * @author wujianjun
     */
    public static Result create(Object value                ) {
        return new Result().setSuccess(DEFAULT_STATUS_SUCCESS).addResult(
                RESULT_KEY_DEFAULT_VALUE, value);
    }

    /**
     * get value of Result.success
     * @return the success
     * @createTime May 13, 2014 10:30:39 AM
     * @author wujianjun
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * set value of Result.success
     * @param success the success to set
     * @return this result object
     * @createTime May 13, 2014 10:30:39 AM
     * @author wujianjun
     */
    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    /**
     * get value of Result.result
     * @return the result
     * @createTime May 13, 2014 10:30:39 AM
     * @author wujianjun
     */
    public Map<String, Object> getAllResult() {
        return result;
    }

    /**
     * 获取消息
     * @return
     */
    public String getMsg() {
        Object val = getResult(RESULT_KEY_MSG);
        return val == null ? "" : val.toString();
    }

    /**
     * 设置消息
     * @param msg
     * @return this object
     */
    public Result setMsg(String msg) {
        addResult(RESULT_KEY_MSG, msg);
        return this;
    }

    /**
     * add value of Result.result
     * @param resultInfo the value
     * @return this result object
     * @createTime May 13, 2014 10:30:39 AM
     * @author wujianjun
     */
    public Result addResult(Map<String, Object> resultInfo) {
        if (resultInfo == null) {
            return this;
        }
        if (this.result == null) {
            this.result = new Hashtable<String, Object>(5);
        }
        this.result.putAll(resultInfo);
        return this;
    }

    /**
     * add value of Result.result
     * @param key the value of key
     * @param value the object value
     * @return this result object
     * @createTime May 13, 2014 10:30:39 AM
     * @author wujianjun
     */
    public Result addResult(String key, Object value) {
        if (this.result == null) {
            this.result = new Hashtable<String, Object>(INIT_MAP_SIZE);
        }
        if (value != null) {
            this.result.put(key, value);
        }
        return this;
    }

    /**
     * get value by key 
     * @return the value
     * @createTime May 13, 2014 10:33:30 AM
     * @author wujianjun
     */
    public Object getResult() {
        return getResult(RESULT_KEY_DEFAULT_VALUE);
    }

    /**
     * get value by key
     * @param key Need to get the value of the key
     * @return the value
     * @createTime May 13, 2014 10:33:30 AM
     * @author wujianjun
     */
    public Object getResult(String key) {
        return this.result == null ? null : this.result.get(key);
    }


}
