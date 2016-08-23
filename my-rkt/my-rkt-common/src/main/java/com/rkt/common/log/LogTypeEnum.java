package com.rkt.common.log;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

/**
 *
 *
 * @author dingjun
 */
public enum LogTypeEnum {
	DEFAULT("DEFAULT", "默认"),
	MY_RKT("MY_RKT", "RKT消息"),
	RKT_MQ("RKT_MQ", "MQ消息"),
    EXCEPTION("EXCEPTION", "运行错误");

	private String key;
	private String value;

	LogTypeEnum() {
	}

	LogTypeEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static String getValue(String key) {
		for (LogTypeEnum ft : LogTypeEnum.values()) {
			if (ft.getKey().equals(key)) {
				return ft.getValue();
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public Log getLog() {
		return LogHelper.getLog(this);
	}

}
