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
//import com.dili.titan.domain.Category;
//import com.dili.titan.domain.CategoryAttr;
//import com.dili.titan.domain.CategoryScope;
//import com.dili.titan.mq.sender.TopicProducer;
//import com.dili.titan.redis.JedisClient;
//import com.dili.titan.service.impl.CategoryService;
//import org.apache.log4j.Logger;
//import org.codehaus.jackson.JsonNode;
//import org.codehaus.jackson.map.DeserializationConfig;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.List;
//
//@Component
//public class CategoryHandler implements BaseHandler {
//	private Logger logger = Logger.getLogger(CategoryHandler.class);
//
//	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//	@Resource(name = "categoryService")
//	private CategoryService categoryService;
//
//	@Resource(name = "jedisClient")
//	private JedisClient jedisClient;
//
//	@Resource(name="topicProducer")
//	private  TopicProducer topicProducer;
//
//	@Override
//	public void handle(String message) {
//		try {
//			JsonNode node = mapper.readTree(message);
//			int cateId = node.get("cateId").getIntValue();
//			int operType = node.get("oper").getIntValue();
//           // logger.info("类目mq 信息为 [cateId=" + cateId + "operType=" +operType+"]");
//			switch (operType) {
//			case 1:
////				topicProducer.sendMQCategory(cateId, operType);
//				logger.info("add 类目mq 发送成功，信息为 [cateId=" + cateId + "operType=" +operType+"]");
//				break;
//			case 2:
////				topicProducer.sendMQCategory(cateId, operType);
//				logger.info("update 类目mq 发送成功，信息为 [cateId=" + cateId + "operType=" +operType+"]");
//				break;
//			case 3:
////				topicProducer.sendMQCategory(cateId, operType);
//				logger.info("3 update 类目mq 发送成功，信息为 [cateId=" + cateId + "operType=" +operType+"]");
//				break;
//			default:
//				logger.error("Category operType error");
//				break;
//			}
//		} catch (Exception e) {
//			logger.error("handle error", e);
//		}
//	}
//
//
//}
