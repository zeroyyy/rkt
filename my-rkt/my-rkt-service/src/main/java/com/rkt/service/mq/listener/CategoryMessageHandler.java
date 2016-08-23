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
//package com.rkt.service.mq.listener;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Component
//public class CategoryMessageHandler implements DiliMQListener {
//	private Logger logger = Logger.getLogger(CategoryMessageHandler.class);
//	@Resource
//	private CategoryHandler categoryHandler;
//
////	@Value("${mq.accountInfoChange.topic}")
//	private String topic="Titan_PLC_Cache_Category";
//
//	@Value("${mq.accountInfoChange.pnr.tag}")
//	private String tags;
//	@Override
//	public String getTags() {
//		// TODO Auto-generated method stub
//		return tags;
//	}
//
//	@Override
//	public String getTopic() {
//		// TODO Auto-generated method stub
//		return topic;
//	}
//
//	@Override
//	public void operate(MessageExt arg0) {
//		try{
//		// TODO Auto-generated method stub
//		byte[] message=arg0.getBody();
//		String strMessage=	new String(message);
//		logger.info("listener CategoryMessageHandler..."+strMessage);
//		categoryHandler.handle(strMessage);
//		}catch (Exception e ){
//			logger.info("listener CategoryMessageHandler error..."+e);
//		}
//	}
//
////	@Override
////	public void onMessage(Message message) {
////		if (message == null) {
////			return;
////		}
////		logger.info("listener CategoryMessageHandler...");
////		try {
////			BytesMessage bytesMessage = (BytesMessage) message;
////			String json = bytesMessage.readUTF();
////			logger.info(json);
////			categoryHandler.handle(json);
////		}
////		catch (JMSException e) {
////			logger.error("JMSException", e);
////		} catch (Exception e) {
////			logger.error("IOException", e);
////		}
////	}
//}
