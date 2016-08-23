package com.rkt.common.result;

import com.rkt.common.exception.TitanError;

public class BaseOutput<T> {

    private int code;
    private String result;
    private T data;

    public BaseOutput() {
        this.code = 200;
        this.result = "成功";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseOutput<T> failure(int code, String result) {
        this.code = code;
        this.result = result;
        return this;
    }

    public BaseOutput<T> failure(int code) {
        this.code = code;
        return this;
    }

    public BaseOutput<T> failure() {
        this.code = 0;
        this.result = "调用失败";
        return this;
    }

    public BaseOutput<T> failure(TitanError error) {
        this.code = error.getCode();
        this.result = error.getResult();
        return this;
    }
}
