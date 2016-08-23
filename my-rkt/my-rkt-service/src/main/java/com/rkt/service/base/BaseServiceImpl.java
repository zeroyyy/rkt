/*
 * Copyright (c) 2014 www.diligrp.com All rights reserved.
 * 本软件源代码版权归----所有,未经许可不得任意复制与传播.
 */
package com.rkt.service.base;
import com.rkt.common.exception.AppException;
import com.rkt.common.result.Page;
import com.rkt.dao.base.BaseDao;
import com.rkt.domain.base.TitanSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * service实现类
 * 
 * @author dev-center
 * @since 2014-05-10
 * @param <T>
 *            实体
 * @param <KEY>
 *            主键
 */
//@DynamicSource
public abstract class BaseServiceImpl<T, KEY extends Serializable> implements BaseService<T, KEY> {
	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	/**
	 * 获取DAO操作类
	 */
	public abstract BaseDao<T, KEY> getDao();

    @Transactional
	public int insertEntry(T... t) {
		return getDao().insertEntry(t);
	}

    public Integer selectEntryListCount(T condtion) {
        return getDao().selectEntryListCount(condtion);
    }

    @Transactional
	public int insertEntryCreateId(T t) {
		return getDao().insertEntryCreateId(t);
	}

    @Transactional
	public int deleteByKey(KEY... key) {
		return getDao().deleteByKey(key);
	}

    @Transactional
	public int deleteByCondtion(T condtion) {
		return getDao().deleteByKey(condtion);
	}

    @Transactional
	public int updateByKey(T condtion) {
		return getDao().updateByKey(condtion);
	}

    @Transactional
	public int saveOrUpdate(T t) {
		Integer id = 0;
		try {
			Class<?> clz = t.getClass();
			id = (Integer) clz.getMethod("getId").invoke(t);
		} catch (Exception e) {
			LOGGER.warn("获取对象主键值失败!");
		}
		if (id != null && id > 0) {
			return this.updateByKey(t);
		}
		return this.insertEntry(t);
	}

	public T selectEntry(KEY key) {
		return getDao().selectEntry(key);
	}

	public List<T> selectEntryList(KEY... key) {
		return getDao().selectEntryList(key);
	}

	public List<T> selectEntryList(T condtion) {
		return getDao().selectEntryList(condtion);
	}

	public List<T> selectList(T condtion) {
		return getDao().selectList(condtion);
	}
	
	/**
	 * list显示
	 */
	public Page<T> selectListPage(T condtion, Page<T> page) {
		try {
			Class<?> clz = condtion.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condtion, page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condtion, page.getEndIndex());
		} catch (Exception e) {
			throw new AppException("设置分页参数失败", e);
		}
		Integer size = getDao().selectListCount(condtion);
		if (size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(this.selectList(condtion));
		return page;
	}

	public Page<T> selectPage(T condtion, Page<T> page) {
		try {
			Class<?> clz = condtion.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condtion, page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condtion, page.getEndIndex());
		} catch (Exception e) {
			throw new AppException("设置分页参数失败", e);
		}
		Integer size = getDao().selectEntryListCount(condtion);
		if (size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(this.selectEntryList(condtion));
		return page;
	}

	/**
	 * 获取ID 当步长step=1时,返回的minValue=maxValue,否则就是ID段.
	 * 
	 * @param sqName
	 * @param minValue
	 * @param maxValue
	 * @param step
	 * @return
	 */
    @Transactional
	public TitanSequence getSequence(String sqName, long minValue, long maxValue, int step) {
		return getDao().getSequence(sqName, minValue, maxValue, step);
	}
}
