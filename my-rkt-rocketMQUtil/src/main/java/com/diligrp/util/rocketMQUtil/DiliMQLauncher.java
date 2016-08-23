package com.diligrp.util.rocketMQUtil;

import com.diligrp.util.rocketMQUtil.exception.DiliUtilException;
import com.diligrp.util.rocketMQUtil.impl.DiliMQConsumerImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * <p>Title: MQ启动器</p>
 * <p>Description:  〈描述〉</p>
 * <B>Copyright</B> Copyright (c) 2014 www.diligrp.com All rights reserved. <br />
 * 本软件源代码版权归地利集团,未经许可不得任意复制与传播.<br />
 * <B>Company</B> 地利集团
 * <p>CreateTime：2014/9/15</p>
 *
 */
public class DiliMQLauncher implements ApplicationListener{
    private Logger log = Logger.getLogger(DiliMQLauncher.class);


    private DiliMQConsumer diliMQConsumer;

    /**
     * 接收Spring容器事件
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            start((ContextRefreshedEvent) event);
            return;
        }
        if (event instanceof ContextStoppedEvent) {
            stop((ContextStoppedEvent) event);
        }
    }

    /**
     * 启动MQ接收
     * @param event
     */
    public void start(ContextRefreshedEvent event) {
        try {
            if (diliMQConsumer == null) {
                diliMQConsumer = new DiliMQConsumerImpl();
                //注入参数
                event.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(diliMQConsumer);
                diliMQConsumer.startListener();
            }
        } catch (DiliUtilException e) {
            log.error(e.getMessage(), e);
        }
    }
    /**
     * 停止MQ
     *
     * @param event
     */
    public void stop(ContextStoppedEvent event) {
        try {
            if (diliMQConsumer != null) {
                diliMQConsumer.stopListener();
            }
        } catch (DiliUtilException e) {
            log.error(e.getMessage(), e);
        }
    }
}
