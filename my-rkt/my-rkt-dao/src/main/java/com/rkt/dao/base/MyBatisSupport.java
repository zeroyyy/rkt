/*
 * Copyright (c) 2014 www.diligrp.com All rights reserved.
 * 本软件源代码版权归----所有,未经许可不得任意复制与传播.
 */
package com.rkt.dao.base;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import com.rkt.common.exception.AppException;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对mybatis的支持<br/>
 * spring配置文件需定义sqlTemplate与batchSqlTemplate
 * 
 * @author dev-center
 * @since 2014-05-10
 */
abstract class MyBatisSupport {
	protected static final Logger LOGGER = LoggerFactory.getLogger(MyBatisSupport.class);
	@Resource
	private SqlSessionTemplate sqlTemplate;
	@Resource
	private SqlSessionTemplate batchSqlTemplate;

	/**
	 * SqlSessionTemplate
	 * 
	 * @param batch
	 *            是否批处理
	 * @param readonly
	 *            是否只读
	 * @return
	 */
	protected SqlSessionTemplate getSqlTemplate(boolean batch, boolean readonly) {

		if (readonly) {
		}

		if (batch) {
			return batchSqlTemplate;
		}
		return sqlTemplate;
	}

	/**
	 * 新增对象
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected int insert(String statement, Object parameter) {
		int res = 0;
		try {
			if (parameter != null) {
				res = getSqlTemplate(false, false).insert(statement, parameter);
			}
		} catch (Exception ex) {
			throw new AppException("Mybatis执行新增异常", ex);
		}
		return res;
	}

	/**
	 * 删除对象
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected int delete(String statement, Object parameter) {
		int res = 0;
		try {
			res = getSqlTemplate(false, false).delete(statement, parameter);
		} catch (Exception ex) {
			throw new AppException("Mybatis执行删除异常", ex);
		}
		return res;
	}

	/**
	 * 更新对象
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected int update(String statement, Object parameter) {
		int res = 0;
		try {
			if (parameter != null) {
				res = getSqlTemplate(false, false).update(statement, parameter);
			}
		} catch (Exception ex) {
			throw new AppException("Mybatis执行更新异常", ex);
		}
		return res;
	}

	/**
	 * 查询一条记录
	 * 
	 * @param <T>
	 * @param statement
	 * @param parameter
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T select(String statement, Object parameter) {
		T obj = null;
		try {
			obj = (T) getSqlTemplate(false, true).selectOne(statement, parameter);
		} catch (Exception ex) {
			throw new AppException("Mybatis执行单条查询异常", ex);
		}
		return obj;
	}

	/**
	 * 查询列表
	 * 
	 * @param <T>
	 * @param statement
	 * @param parameter
	 * @param
	 * @return
	 */
	protected <T> List<T> selectList(String statement, Object parameter) {
		List<T> list = null;
		try {
			list = getSqlTemplate(false, true).selectList(statement, parameter);
		} catch (Exception ex) {
			throw new AppException("Mybatis执行列表查询异常", ex);
		}
		return list;
	}

	/**
	 * 查询Map
	 * 
	 * @param <K>
	 * @param <V>
	 * @param statement
	 * @param parameter
	 * @param mapKey
	 * @return
	 */
	protected <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		Map<K, V> map = null;
		try {
			map = getSqlTemplate(false, true).selectMap(statement, parameter, mapKey);
		} catch (Exception ex) {
			throw new AppException("Mybatis执行Map查询异常", ex);
		}
		return map;
	}
}
