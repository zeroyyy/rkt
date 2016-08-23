/*
 * Copyright (c) 2016 www.diligrp.com All rights reserved.
 * 本软件源代码版权归----所有,未经许可不得任意复制与传播.
 */
package com.rkt.web.contorller;
import java.util.Date;
import javax.annotation.Resource;

import com.rkt.common.log.LogHelper;
import com.rkt.common.log.LogTypeEnum;
import com.rkt.common.result.Message;
import com.rkt.common.result.Page;
import com.rkt.common.web.BaseController;
import com.rkt.domain.TMyrktUser;
import com.rkt.domain.base.Result;
import com.rkt.domain.base.TitanSequence;
import com.rkt.service.TMyrktUserService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

/**
 *tMyrktUser controller层
 * @author dev-center
 * @since 2016-07-18
 */
@Controller
@RequestMapping(value = "/tMyrktUser")
public class TMyrktUserController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(TMyrktUserController.class);
	@Resource private TMyrktUserService tMyrktUserService;
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder, WebRequest request) {
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(true));
//	}
	
	/**
	 * 列表展示
	 * @param tMyrktUser 实体对象
	 * @param page 分页对象
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView list(TMyrktUser tMyrktUser,Page<TMyrktUser> page,Integer pageId,Model view) throws Exception{
		Result result=null;
		try {
			result = Result.create();
			if(pageId==null){
				pageId=1;
			}
			page.setCurrentPage(pageId);
			Page<TMyrktUser> tMyrktUserPage = tMyrktUserService.selectPage(tMyrktUser, page);
			result.addResult("tMyrktUser", tMyrktUser);
			result.addResult("page",tMyrktUserPage);
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}
		return toVMSkipLayout("tMyrktUser/list",result.getAllResult());
	}
	
	/**
	 * 响应新增(修改)页面
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="edit/{id}",method={RequestMethod.GET,RequestMethod.POST})
	public String edit(@PathVariable Long id,Model view) throws Exception{
		try {
			LogHelper.info("你所打印的为默认日志");
			LogHelper.debug("你所打印的为默认日志");
			LogHelper.info(LogTypeEnum.MY_RKT,"rkt消息打印,id为{},haha{}",id,22);
			LogHelper.info(LogTypeEnum.RKT_MQ,"mq消息打印,id为{},haha{}",id,22);
			LogHelper.info(LogTypeEnum.EXCEPTION, "异常信息打印", id);
			LogHelper.debug(LogTypeEnum.MY_RKT, "rkt消息打印,id为{},haha{}", id, 22);
			LogHelper.debug(LogTypeEnum.RKT_MQ, "mq消息打印,id为{},haha{}", id, 22);
			LogHelper.debug(LogTypeEnum.EXCEPTION, "异常信息打印", id);
			LogHelper.error(LogTypeEnum.MY_RKT, "rkt消息打印,id为{},haha{}", id, 22);
			LogHelper.error(LogTypeEnum.RKT_MQ, "mq消息打印,id为{},haha{}", id, 22);
			LogHelper.error(LogTypeEnum.EXCEPTION, "异常信息打印", id);
			if(id != null && id > 0) {
				TMyrktUser tMyrktUser = tMyrktUserService.selectEntry(id);
				if(tMyrktUser == null) {
//					return toJSON(Message.failure("您要修改的数据不存在或者已被删除!"));
					return "tMyrktUser/edit";
				}
				view.addAttribute("tMyrktUser",tMyrktUser);
			}			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "tMyrktUser/edit";
	}
	
	/**
	 * 通过编号删除对象
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public @ResponseBody
	Message del(@PathVariable Long id,Model view) throws Exception{
    	Message msg = null;
    	try {
			int res = tMyrktUserService.deleteByKey(id);
			msg  = res > 0 ? Message.success() : Message.failure();
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			msg = Message.failure();
		}finally{
		}

		return msg;
	}
	
	/**
	 * 通过编号查看对象
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="view/{id}",method=RequestMethod.GET)
	public String view(@PathVariable Long id,Model view) throws Exception{
		try {

			TMyrktUser tMyrktUser = tMyrktUserService.selectEntry(id);
			if(tMyrktUser == null) {
				return null;
			}
			view.addAttribute("tMyrktUser",tMyrktUser);
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "tMyrktUser/view";
	}
	
	/**
	 * 保存方法
	 * @param tMyrktUser 实体对象
	 * @return
	 */
	@RequestMapping(value = "/saveUpdate", method = {RequestMethod.POST })
	public ModelAndView save(TMyrktUser tMyrktUser,Model view) throws Exception{
		String out=null;
		Result s = Result.create();
		try {
			TitanSequence sequence = tMyrktUserService.getSequence("my_rkt_id",10000L,1000000L,1);
			tMyrktUser.setId(sequence.getMinValue());
			int res = tMyrktUserService.saveOrUpdate(tMyrktUser);
			out=res > 0 ? "成功" :"失败";
			s.addResult("out",out);
			s.addResult("page",550);
			view.addAttribute("productPopAudits", 220);
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
		}finally{
		}
		return toVMSkipLayout("tMyrktUser/edit",s.getAllResult());
	}
	
}