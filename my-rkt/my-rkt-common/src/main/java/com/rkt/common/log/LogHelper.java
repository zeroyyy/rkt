package com.rkt.common.log;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangjanjun
 * @Description 日志打印工具
 * @Copyright 本软件源代码版权归地利集团, 未经许可不得任意复制与传播
 * @Company 地利集团
 * @createTime 2016/7/21 17:45
 */
public class LogHelper {

    /**
     */
    private static Map<LogTypeEnum, Log> logMap = new HashMap<LogTypeEnum, Log>();

    static {
        for (LogTypeEnum ft : LogTypeEnum.values()) {
            logMap.put(ft, LogFactory.getLog(ft.getKey()));
        }
    }

    /**
     *
     * @param str
     * @return
     */
    public static String addLogbusinessId(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        } else {
            return "业务标示ID[" + LogBusinessIdUtils.getCurBusinessId() + "],"
                    + str;
        }
    }

    /**
     * 使用默认的文件记录debug信息
     * 
     * @param str 日志信息
     */
    public static void debug(String str, Object... args) {
        debug(LogTypeEnum.DEFAULT, str, args);
    }

    /**
     * 使用默认的文件记录info信息
     * 
     * @param str 日志信息
     */
    public static void info(String str, Object... args) {
        info(LogTypeEnum.DEFAULT, str, args);
    }

    /**
     * 使用默认的文件记录warn信息
     * 
     * @param str 日志信息
     */
    public static void warn(String str, Object... args) {
        warn(LogTypeEnum.DEFAULT, str, args);
    }

    /**
     * 使用默认的文件记录error信息
     * 
     * @param str 日志信息
     */
    public static void error(Throwable e, String str, Object... args) {
        error(LogTypeEnum.DEFAULT, e, str, args);
    }

    /**
     * 使用默认的文件记录fatal信息
     * 
     * @param str 日志信息
     */
    public static void fatal(Throwable e, String str, Object... args) {
        fatal(LogTypeEnum.DEFAULT, e, str, args);
    }

    /**
     * 记录运行期错误信息
     * 
     * @param e
     */
    public static void execption(Throwable e) {
        error(LogTypeEnum.EXCEPTION, getExceptionTrace(e));
    }

    /**
     * 打印错误信息
     * 
     * @param e
     * @return
     */
    public static String getExceptionTrace(Throwable e) {
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } else
            return null;
    }

    public static Log getLog(LogTypeEnum type) {
        if (logMap.get(type) == null) {
            return LogFactory.getLog(LogHelper.class);
        } else {
            return logMap.get(type);
        }
    }

    /**
     * 日志消息占位符形式的替换，按照传入参数依次替换"{}"
     * 
     * @param str 带占位符的日志消息
     * @param args 需要被替换的参数，除了字符串和基本类型的对象形式外，其他类型需要自己实现toString方法
     * @return
     */
    public static <T extends Object> String replace(String str, T... args) {
        if (str == null) {
            return str;
        }
        if (args == null || args.length < 1) {
            return addLogbusinessId(str.replaceAll("\\{\\}", " null"));
        }
        StringBuilder builder = new StringBuilder(str);
        int start = builder.indexOf("{");
        int end = builder.indexOf("}", start);
        for (Object arg : args) {
            if (start == -1) {
                break;
            }
            if (arg == null) {
                arg = "null";
            }
            builder.replace(start, end + 1, arg.toString());
            start = start + arg.toString().length();
            start = builder.indexOf("{", start);
            end = builder.indexOf("}", start);
        }
        return addLogbusinessId(builder.toString());
    }

    /**
     * 记录DEBUG信息
     * 
     * @param type 日志业务类型
     * @param str
     */
    public static void debug(LogTypeEnum type, String str, Object... args) {
        Log log = getLog(type);
        if (log.isDebugEnabled()) {
            String msg = replace(str, args);
            log.debug(msg);
        }
    }

    /**
     * 记录INFO信息
     * 
     * @param type 日志业务类型
     * @param str
     */
    public static void info(LogTypeEnum type, String str, Object... args) {
        Log log = getLog(type);
        if (log.isInfoEnabled()) {
            String msg = replace(str, args);
            log.info(msg);
        }
    }

    /**
     * 记录warn信息
     * 
     * @param type 日志业务类型
     * @param str
     */
    public static void warn(LogTypeEnum type, String str, Object... args) {
        Log log = getLog(type);
        if (log.isWarnEnabled()) {
            String msg = replace(str, args);
            log.warn(msg);
        }
    }

    /**
     * 记录error信息
     * 
     * @param type 日志业务类型
     * @param str
     */
    public static void error(LogTypeEnum type, String str, Object... args) {
        Log log = getLog(type);
        if (log.isErrorEnabled()) {
            String msg = replace(str, args);
            log.error(msg);
        }
    }

    /**
     * 记录fatal信息
     * 
     * @param type 日志业务类型
     * @param str 日志信息
     */
    public static void fatal(LogTypeEnum type, String str, Object... args) {
        Log log = getLog(type);
        if (log.isFatalEnabled()) {
            String msg = replace(str, args);
            log.fatal(msg);
        }
    }

    /**
     * 记录error信息
     * 
     * @param type 日志业务类型
     * @param str
     */
    public static void error(LogTypeEnum type, Throwable e, String str,
            Object... args) {
        Log log = getLog(type);
        if (log.isErrorEnabled()) {
            String msg = replace(str, args);
            log.error(msg, e);
        }
    }

    /**
     * 记录fatal信息
     * 
     * @param type 日志业务类型
     * @param str 日志信息
     */
    public static void fatal(LogTypeEnum type, Throwable e, String str,
            Object... args) {
        Log log = getLog(type);
        if (log.isFatalEnabled()) {
            String msg = replace(str, args);
            log.fatal(msg, e);
        }
    }

    public static void main(String[] args) {
        LogHelper.debug(LogTypeEnum.DEFAULT, "aaaa");


       Map<LogTypeEnum, Log> logMap = new HashMap<LogTypeEnum, Log>();
        for (LogTypeEnum ft : LogTypeEnum.values()) {
            System.out.println(ft.getKey());
            System.out.println(ft+"===>"+LogFactory.getLog(ft.getKey()));
        }
    }
}
