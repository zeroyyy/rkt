/*
 * Copyright (c) 2016 www.diligrp.com All rights reserved.
 * 本软件源代码版权归----所有,未经许可不得任意复制与传播.
 */
package com.rkt.service.impl;

import javax.annotation.Resource;

import com.rkt.dao.TMyrktUserDao;
import com.rkt.dao.base.BaseDao;
import com.rkt.domain.TMyrktUser;
import com.rkt.service.TMyrktUserService;
import com.rkt.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * TMyrktUserService 实现类
 * @author dev-center
 * @since 2016-07-18
 */
@Service("tMyrktUserService")
public class TMyrktUserServiceImpl extends BaseServiceImpl<TMyrktUser,Long> implements TMyrktUserService {
	
	@Resource private TMyrktUserDao tMyrktUserDao;
	
	public BaseDao<TMyrktUser,Long> getDao() {
		return tMyrktUserDao;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int insertEntryCreateId(TMyrktUser tMyrktUser) {
		return super.insertEntryCreateId(tMyrktUser);
	}
}