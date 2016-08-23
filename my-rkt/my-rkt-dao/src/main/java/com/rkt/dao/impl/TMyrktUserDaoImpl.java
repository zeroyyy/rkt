/*
 * Copyright (c) 2016 www.diligrp.com All rights reserved.
 * 本软件源代码版权归----所有,未经许可不得任意复制与传播.
 */
package com.rkt.dao.impl;

import com.rkt.dao.TMyrktUserDao;
import com.rkt.dao.base.BaseDaoImpl;
import com.rkt.domain.TMyrktUser;
import org.springframework.stereotype.Repository;


/**
 * TMyrktUserDao 实现类
 * @author dev-center
 * @since 2016-07-18
 */
@Repository("tMyrktUserDao")
public class TMyrktUserDaoImpl extends BaseDaoImpl<TMyrktUser,Long> implements TMyrktUserDao {
	private final static String NAMESPACE = "com.rkt.dao.TMyrktUserDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
		
}