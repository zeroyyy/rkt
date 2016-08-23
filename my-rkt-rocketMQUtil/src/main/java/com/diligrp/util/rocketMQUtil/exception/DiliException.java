/*
 * *
 *  * @Description ${DESCRIPTION}
 *  *
 *  * @Company
 *  * @createTime  ${YEAR}.${MONTH}.${DAY} ${TIME}
 *  * @author yangjianjun
 *
 */

package com.diligrp.util.rocketMQUtil.exception;

import java.util.Map;


public class DiliException extends Exception {
    private Map<?, ?> map = null;
    public DiliException(){}
    public DiliException(String msg){
        super(msg);
    }

    public DiliException(String msg, Map<?, ?> map){
        super(msg);
        this.setMap(map);
    }


    public Map<?, ?> getMap() {
        return map;
    }

    public void setMap(Map<?, ?> map) {
        this.map = map;
    }
}