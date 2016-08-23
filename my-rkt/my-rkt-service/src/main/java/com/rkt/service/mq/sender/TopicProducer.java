///*
// * *
// *  * @Description ${DESCRIPTION}
// *  *
// *  * @Company
// *  * @createTime  ${YEAR}.${MONTH}.${DAY} ${TIME}
// *  * @author yangjianjun
// *
// */
//
//package com.rkt.service.mq.sender;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.rocketmq.common.message.Message;
//import com.diligrp.util.exception.DiliUtilException;
//import com.diligrp.util.rocketMQUtil.DiliMQProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component("topicProducer")
//public class TopicProducer {
//	/**
//	 * MQ队列添加操作
//	 */
//	public static final int ADD_TOPIC=1;
//	/**
//	 *  MQ队列更新操作
//	 */
//	public static final int MODIFY_TOPIC=2;
//	/**
//	 * MQ队列删除操作
//	 */
//	public static final int DEL_TOPIC=3;
//    @Autowired
//    private DiliMQProducer diliMQProducer;
//
//
//	/**
//	 * 发送消息
//	 *
//	 * @param destination
//	 * @param message
//	 */
//    public void send(final String destination, final String message) {
//        Message mesg = new Message(destination,"plc",message.getBytes());
//        try {
//            diliMQProducer.sendMsg(mesg);
//        } catch (DiliUtilException e) {
//            e.printStackTrace();
//        }
//    }
//
//	 /**
//     * this method is 发送属性mq消息
//     * @param attrId
//     * @param oper
//     * @createTime 2014年7月10日 下午8:12:21
//     * @author yangjianjun
//     */
//    public  void sendMQAttribute(Object attrId,int oper){
//    	 Map<String,Object> mapMQ=new HashMap<String,Object>();
//		 mapMQ.put("attrId", attrId);
//		 mapMQ.put("oper", oper);
//		 this.send("Titan_PLC_Attribute", JSONArray.toJSON(mapMQ).toString());
//	 }
//    /**
//     * this method is 发送类目mq消息
//     * @param cateId
//     * @param oper
//     * @createTime 2014年7月10日 下午8:12:29
//     * @author yangjianjun
//     */
//    public  void sendMQCategory(Object cateId,int oper){
//    	 Map<String,Object> mapMQ=new HashMap<String,Object>();
//		 mapMQ.put("cateId", cateId);
//		 mapMQ.put("oper", oper);
//		 this.send("Titan_PLC_Category", JSONArray.toJSON(mapMQ).toString());
//	 }
//     /**
//     * this method is 发送产品基础信息mq消息
//     * @param pid
//     * @param oper
//     * @createTime 2014年11月3日 下午8:13:11
//     * @author chenzhiwei
//     */
//    public  void sendMQProductBase(Long pid,int oper){
//    	 Map<String,Object> mapMQ=new HashMap<String,Object>();
//		 mapMQ.put("pid", pid);
//		 mapMQ.put("oper", oper);
//		 this.send("Titan_PLC_ProductBase", JSONArray.toJSON(mapMQ).toString());
//	 }
//
//    /**
//     * this method is 发送产品mq消息
//     * @param pid
//     * @param oper
//     * @createTime 2014年11月3日 下午8:13:11
//     * @author chenzhiwei
//     */
//    public  void sendMQProduct(Long pid,Long marketId,int oper){
//        Map<String,Object> mapMQ=new HashMap<String,Object>();
//        mapMQ.put("pid", pid);
//        mapMQ.put("marketId", marketId);
//        mapMQ.put("oper", oper);
//        this.send("Titan_PLC_Product", JSONArray.toJSON(mapMQ).toString());
//    }
//
//    /**
//     * this method is 发送产品最低价mq消息
//     * @param pid
//     * @createTime 2014年11月3日 下午8:13:11
//     * @author chenzhiwei
//     */
//    public  void sendMQProductMinPrice(Long pid,Long marketId,Long minPrice){
//        Map<String,Object> mapMQ=new HashMap<String,Object>();
//        mapMQ.put("pid", pid);
//        mapMQ.put("marketId", marketId);
//        mapMQ.put("minPrice", minPrice);
//        this.send("Titan_PLC_Product_Price", JSONArray.toJSON(mapMQ).toString());
//    }
//
//    /**
//     * this method is 发送产品总库存mq消息
//     * @param pid
//     * @createTime 2014年11月3日 下午8:13:11
//     * @author chenzhiwei
//     */
//    public  void sendMQProductTotalStock(Long pid,Long marketId,Long totalStock){
//        Map<String,Object> mapMQ=new HashMap<String,Object>();
//        mapMQ.put("pid", pid);
//        mapMQ.put("marketId", marketId);
//        mapMQ.put("totalStock", totalStock);
//        this.send("Titan_PLC_Product_Stock", JSONArray.toJSON(mapMQ).toString());
//    }
//}
