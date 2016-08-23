package com.diligrp.util.rocketMQUtil;
/**
 *<p> 对RecketMQ的封装, 实现了发送, 以及消费.
 * <p>说明</p>
 * <pre>
 * 0. RecketMQ需要名称服务器的地址, 以及项目分组信息, 因此需要提供两个Spring配置项:
 *      MQ名称空间地址
 *      mq.namesrvAddr=${conf.mq.namesrvAddr}
 *      MQ生产者组
 *      mq.producerGroup=${conf.mq.producerGroup}
 *
 * 1. 发送MQ消息
 *  1.1 在spring的XML中配置:
 *      <bean id="diliMQProducer" class="com.diligrp.util.rocketMQUtil.impl.DiliMQProducerImpl"/>
 *  1.2 在需要发送MQ的地方直接注入就好.
 *
 * 2. 接收消息.
 *  2.1 在springXML中配置:
 *      <bean id="diliMQProducer" class="com.diligrp.util.rocketMQUtil.DiliMQLauncher"/>
 *      这是客户端启动器, 不用手动触发, 也不需要有其它引用.
 *  2.2 实现DiliMQListener接口, 以便接收MQ消息
 *      大概原理是客户端容器维护一个MQ的链接, 同时监听MQ服务器发送的消息, 然后根据接口实现类提供的主题, 标签来判断将消息给谁来处理.
 *      <b style="color:red">注: 当多个实现接收同一主题下的标签时, 多个实现都会收到该消息</b>
 *</pre>
 */
public interface  packageInfo{}
