package com.rkt.common.web.exception;

public class WebException extends RuntimeException{
    private static final long serialVersionUID = -4573550295666184287L;

    public WebException(String msg){
        super(msg);
    }
    
    public WebException(Throwable cause){
        super(cause);
    }
    
    public WebException(String msg, Throwable cause){
        super(msg, cause);
    }
}
