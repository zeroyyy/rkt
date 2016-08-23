package com.diligrp.util.rocketMQUtil.impl;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.diligrp.util.rocketMQUtil.DiliMQProducer;
import com.diligrp.util.rocketMQUtil.exception.DiliUtilException;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Value;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Title:  〈标题〉</p>
 * <p>Description:  〈描述〉</p>
 * <B>Copyright</B> Copyright (c) 2014 www.diligrp.com All rights reserved. <br />
 * 本软件源代码版权归地利集团,未经许可不得任意复制与传播.<br />
 * <B>Company</B> 地利集团
 * <p>CreateTime：2014/9/15</p>
 *
 */
public class DiliMQProducerImpl implements DiliMQProducer {
    private static Logger log = Logger.getLogger(DiliMQProducerImpl.class);
    //MQ生产者
    private static DefaultMQProducer producer;

    private static DiliMQProducerImpl SIN;

    private static Lock lock = new ReentrantLock();

    @Value("${mq.namesrvAddr}")
    private String namesrvAddr = "";

    @Value("${mq.producerGroup}")
    private String producerGroup = "";

    public  DiliMQProducerImpl() {SIN = this;}

    private static DiliMQProducerImpl me() {
        return SIN;
    }

    public static DefaultMQProducer producer() throws DiliUtilException {
        lock.lock();
        if (producer == null) {
            if (Strings.isBlank(me().producerGroup)) {
                producer = new DefaultMQProducer();
            } else {
                producer = new DefaultMQProducer(me().producerGroup);
            }
            try {
                if (Strings.isBlank(me().namesrvAddr)) {
                    throw new DiliUtilException("MQ服务器地址为空!");
                }
                producer.setNamesrvAddr(me().namesrvAddr);
                producer.start();
            } catch (MQClientException e) {
                lock.unlock();
                log.error("启动MQ出错:" + e.getMessage(), e);
                throw new DiliUtilException("启动MQ出错!");
            }
        }
        lock.unlock();
        return producer;
    }



    @Override
    public void sendMsg(Message msg) throws DiliUtilException {
        try {
            producer().send(msg);
        } catch (DiliUtilException e) {
        throw e;
        } catch (Exception e) {
        log.error(e.getMessage(), e);
        throw new DiliUtilException("操作MQ出错!");
        }
        }
        }
