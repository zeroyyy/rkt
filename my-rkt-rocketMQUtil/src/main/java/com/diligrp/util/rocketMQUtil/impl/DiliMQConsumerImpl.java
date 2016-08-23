package com.diligrp.util.rocketMQUtil.impl;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.diligrp.util.rocketMQUtil.DiliMQConsumer;
import com.diligrp.util.rocketMQUtil.DiliMQListener;
import com.diligrp.util.rocketMQUtil.exception.DiliUtilException;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:  〈标题〉</p>
 * <p>Description:  〈描述〉</p>
 * <B>Copyright</B> Copyright (c) 2014 www.diligrp.com All rights reserved. <br />
 * 本软件源代码版权归地利集团,未经许可不得任意复制与传播.<br />
 * <B>Company</B> 地利集团
 * <p>CreateTime：2014/9/15</p>
 *
 */
public class DiliMQConsumerImpl implements DiliMQConsumer {
    private Logger log = Logger.getLogger(DiliMQConsumerImpl.class);
     /**
     * MQ消费者
     */
    private static DefaultMQPushConsumer consumer;

    /**
     * 是否启动
     */
    private Boolean started = false;

    /**
     * 单例
     */
    private static DiliMQConsumerImpl SIN;

    /**
     * 监听器列表, 按topic->tag->MQListener的层次来组织
     */
    private Map<String, Map<String, List<DiliMQListener>>> listenerMap;

    @Value("${mq.namesrvAddr}")
    private String namesrvAddr = "";

    @Value("${mq.producerGroup}")
    private String producerGroup = "";

    @Autowired
    private List<DiliMQListener> mqListeners;

    public  DiliMQConsumerImpl() {
        listenerMap = new HashMap<String, Map<String, List<DiliMQListener>>>();
        SIN = this;
    }

    public static DiliMQConsumerImpl me() {
        return SIN;
    }


    public static DefaultMQPushConsumer consumer() throws DiliUtilException {
        if (consumer == null) {
            if (Strings.isBlank(me().producerGroup)) {
                consumer = new DefaultMQPushConsumer();
            } else {
                consumer = new DefaultMQPushConsumer(me().producerGroup);
            }
            if (Strings.isBlank(me().namesrvAddr)) {
                throw new DiliUtilException("MQ服务器地址为空!");
            }
            consumer.setNamesrvAddr(me().namesrvAddr);
        }
        return consumer;
    }
    /**
     * 启动MQ消息监控
     *
     * @throws
     */
    public void startListener() throws DiliUtilException {
        if (started) {
            return;
        }
        started = true;

        if (mqListeners == null || mqListeners.size() <= 0) {
            if (log.isInfoEnabled()) {
                log.info("没有监听器, 不启动MQ消息监听!");
            }
            return;
        }
        try {
            if (log.isInfoEnabled()) {
                log.info("开始启动MQ客户端!");
            }
            initListenerMap();
            //注册需要监听的数据
            subscribe();
            //执行MQ消息
            registerListener();
            consumer().start();
            if (log.isInfoEnabled()) {
                log.info("MQ Client 启动成功!");
            }
        } catch (MQClientException e) {
            log.error("MQ Client启动失败!" + e.getMessage(), e);
        }
    }

    public void stopListener() throws DiliUtilException {
        if (log.isInfoEnabled()) {
            log.info("开始停止MQ客户端!");
        }
        consumer().shutdown();
        if(log.isInfoEnabled()) {
            log.info("MQ Client 停止成功!");
        }
    }

    /**
     * 订阅消息
     *
     * @throws
     * @throws MQClientException
     */
    private void subscribe() throws DiliUtilException, MQClientException {
        //注册需要监听的数据
        for (DiliMQListener listener : mqListeners) {
            String topic = listener.getTopic();
            String tags = listener.getTags();
            if (log.isInfoEnabled()) {
                log.info("订阅: topic->" + topic + "   tags->" + tags);
            }
            consumer().subscribe(topic, tags);
        }
    }

    /**
     * 初始化监听列表
     */
    private void initListenerMap() {
        for (DiliMQListener listener : mqListeners) {
            Map<String, List<DiliMQListener>> topic = listenerMap.get(listener.getTopic());
            if (topic == null) {
                topic = new HashMap<String, List<DiliMQListener>>();
                listenerMap.put(listener.getTopic(), topic);
            }
            List<String> selfTag = splitMsg(listener.getTags(), "\\|\\|");
            for (String tag : selfTag) {
                List<DiliMQListener> items = topic.get(tag);
                if (items == null) {
                    items = new ArrayList<DiliMQListener>();
                    topic.put(tag, items);
                }
                if (items.contains(listener)) {
                    continue;
                }
                items.add(listener);
            }
        }
    }

    /**
     * 根据主题, 标签获取监听列表
     * @param topic
     * @param tags
     * @return
     */
    private List<DiliMQListener> fetchListener(String topic, String tags) {
        List<DiliMQListener> list = new ArrayList<DiliMQListener>();
        if (!listenerMap.containsKey(topic)) {
            return list;
        }
        Map<String, List<DiliMQListener>> tagMap = listenerMap.get(topic);
        List<String> selfTag = splitMsg(tags, "\\|\\|");
        for (String tag : selfTag) {
            List<DiliMQListener> listeners = tagMap.get(tag);
            for (DiliMQListener mql : listeners) {
                if (!list.contains(mql)) {
                    list.add(mql);
                }
            }
        }
        return list;
    }

    /**
     * 注册监听器, 并构建执行逻辑
     *
     * @throws
     */
    private void registerListener() throws DiliUtilException {
        //执行MQ消息
        consumer().registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    List<DiliMQListener> listeners = fetchListener(msg.getTopic(), msg.getTags());
                    for (DiliMQListener mql : listeners) {
                        mql.operate(msg);
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }

    /**
     * 判断是否支持当前的消息
     *
     * @param msgTags
     * @param tags
     * @return
     */
    private Boolean equalsTag(List<String> msgTags, String tags) {
        if (Strings.isBlank(tags)) {
            return true;
        }
        if (tags.equals("*")) {
            return true;
        }
        List<String> selfTag = splitMsg(tags, "\\|\\|");
        for (String key : selfTag) {
            if (msgTags.contains(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据正则分割字符串为列表
     *
     * @param tags
     * @param regex
     * @return
     */
    private List<String> splitMsg(String tags, String regex) {
        String[] arr = tags.split(regex);
        List<String> list = new ArrayList<String>();
        for (String t : arr) {
            list.add(t.trim());
        }
        return list;
    }

}
