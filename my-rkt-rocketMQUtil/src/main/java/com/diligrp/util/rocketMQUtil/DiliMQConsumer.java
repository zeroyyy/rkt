package com.diligrp.util.rocketMQUtil;


import com.diligrp.util.rocketMQUtil.exception.DiliUtilException;

/**
 * <p>Title:  〈标题〉</p>
 * <p>Description:  〈描述〉</p>
 * <B>Copyright</B> Copyright (c) 2014 www.diligrp.com All rights reserved. <br />
 * 本软件源代码版权归地利集团,未经许可不得任意复制与传播.<br />
 * <B>Company</B> 地利集团
 * <p>CreateTime：2014/9/15</p>
 *
 */
public interface DiliMQConsumer {
    /**
     * 启动MQ消息监控
     *
     * @throws
     */
    public void startListener() throws DiliUtilException;

    /**
     * 停止MQ消息
     * @throws DiliUtilException
     */
    public void stopListener() throws DiliUtilException;
}
