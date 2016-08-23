package com.rkt.common.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rkt.common.web.exception.WebException;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseController
 * 
 */
public class BaseController extends VelocitySupport {
	protected final static String DEFAULT_CHARTSET = "UTF-8";
	protected final static String DEFAULT_JSON_CONTENT_TYPE = "application/json;charset="
			+ DEFAULT_CHARTSET;

	@Resource
	private HttpServletRequest request;
	
	/**
	 * 输出文本
	 * 
	 * @param txt
	 * @param contextType
	 */
	protected void write(HttpServletResponse response, String txt,
			String contextType) {
		try {
			if (!StringUtils.isEmpty(txt)) {
				return;
			}
			response.setContentType(contextType);
			response.getOutputStream().write(txt.getBytes(DEFAULT_CHARTSET));
		} catch (Exception ex) {
			throw new WebException(ex);
		}
	}

	/**
	 * 输出JSON对象
	 * 
	 * @param model
	 */
	protected void writeJSON(HttpServletResponse response, Object model) {
		write(response, JSON.toJSONString(model), DEFAULT_JSON_CONTENT_TYPE);
	}

	protected Map<String, Object> getDefaultContext() {
		Map<String, Object> context = new HashMap<String, Object>();
		return context;
	}

	protected ViewBuilder toView(String template) {
		ViewBuilder vb = new ViewBuilder(template, this);
		return vb;
	}
	
	/**
	 * java bean to json
	 * 
	 * @param object
	 * @return
	 */
	protected String objectToJSON(Object object) {
		// try {
		// return new ObjectMapper().writeValueAsString(object);
		// } catch (JsonGenerationException e) {
		// e.printStackTrace();
		// } catch (JsonMappingException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return "";
	}

	/**
	 * json to java bean
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @param t
	 * @return
	 */
	protected <T> T JSONToObject(String jsonStr, Class<T> t) {
		// try {
		// return new ObjectMapper().readValue(jsonStr, t);
		// } catch (JsonParseException e) {
		// e.printStackTrace();
		// } catch (JsonMappingException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return null;
	}

	protected void writeStringToResponse(HttpServletResponse response,
			String text) throws IOException {
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(text);
	}
	
	/**
	 * 便利地判断输入参数是否合法，如果输入参数不符合预期，则会抛出 IllegalArgumentException
	 * 
	 * @param expect 期望输入结果
	 * @param msg 错误消息
	 */
	protected void checkParam(boolean expect, String msg) throws IllegalArgumentException{
		if(!expect){
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * 接收前端提交的list类型参数，并把list内部元素转换成响应PO对象
	 * 
	 * @author xiaoyang@diligrp.com 2014年5月19日
	 *
	 * @param paramName 参数名
	 * @param clazz list内部po类型
	 * @return
	 * @throws Exception
	 */
	protected <E> List<E> parseParam(String paramName, Class<E> clazz) throws Exception{
		List<E> result = new ArrayList<E>();
		String param = request.getParameter(paramName);
		if(param == null){
			throw new IllegalArgumentException("指定的参数 ：" + paramName + " 不存在" );
		}
		JSONArray array = JSON.parseArray(param);
		for(Object a : array){
			result.add(JSON.parseObject(a.toString(), clazz));
		}
		
		return result;
	}
	
	protected String generateResultString(JSONObject result, String msg, boolean success){
		result.put("msg", msg);
		result.put("success", success);
		return result.toJSONString();
	}
}
