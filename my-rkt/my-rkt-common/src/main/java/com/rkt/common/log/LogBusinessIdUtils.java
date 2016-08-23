package com.rkt.common.log;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;

/**
 * 用于设置线程的UUID，用于打印日志
 * @author cdwangyong3
 *
 */
public class LogBusinessIdUtils {
	private static final ThreadLocal<String> businessLocal = new ThreadLocal<String>();
    /**
     * 返回当前线程对应的uuid
     * @return
     */
    public static String getCurBusinessId(){
        String uuid = businessLocal.get();
        if(StringUtils.isEmpty(uuid)){
            uuid = UUID.randomUUID().toString().replace("-", "");
            businessLocal.set(uuid);
        }
        return uuid;
    }


    /**
     * 给当前线程重新设置
     */
    public static void clear(){
        businessLocal.set(null);
    }
}

