package com.diligrp.util.rocketMQUtil;

import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * <p>Title: MQ消息监听器</p>
 * <p>Description:  〈描述〉</p>
 * <B>Copyright</B> Copyright (c) 2014 www.diligrp.com All rights reserved. <br />
 * 本软件源代码版权归地利集团,未经许可不得任意复制与传播.<br />
 * <B>Company</B> 地利集团
 * <p>CreateTime：2014/9/10</p>
 *
 */
public interface DiliMQListener {

    /**
     * 订阅的主题
     * @return
     */
    public String getTopic();

    /**
     * 订阅的标签
     * 订阅过滤表达式字符串，broker依据此表达式进行过滤。目前只支持或运算<br>
     *            例如: "tag1 || tag2 || tag3"<br>
     *            如果subExpression等于null或者*，则表示全部订阅<br>
     * @return
     */
    public String getTags();

    /**
     * 处理MQ消息
     */
    public void operate(MessageExt msg);

}
