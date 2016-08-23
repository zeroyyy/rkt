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

import java.util.*;


public class DiliUtilException extends DiliException {
    private Map<?, ?> map = null;

    public DiliUtilException(){}

    public DiliUtilException(String msg){
        super(msg);
    }

    public DiliUtilException(String msg, Map<?, ?> map){
        super(msg, map);
    }

}