package com.diligrp.util.rocketMQUtil;
import com.alibaba.rocketmq.common.message.Message;
import com.diligrp.util.rocketMQUtil.exception.DiliUtilException;

/**
 * <p>Title: 地利MQ生产者</p>
 * <p>Description:  〈描述〉</p>
 * <B>Copyright</B> Copyright (c) 2014 www.diligrp.com All rights reserved. <br />
 * 本软件源代码版权归地利集团,未经许可不得任意复制与传播.<br />
 * <B>Company</B> 地利集团
 * <p>CreateTime：2014/9/15</p>

 */
public interface DiliMQProducer {
    /**
     * 发送消息
     * @param msg
     * @throws DiliUtilException
     */
    public void sendMsg(Message msg) throws DiliUtilException;
}
