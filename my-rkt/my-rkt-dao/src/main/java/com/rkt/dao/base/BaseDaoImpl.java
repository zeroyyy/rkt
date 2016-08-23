/*
 * Copyright (c) 2014 www.diligrp.com All rights reserved.
 * 本软件源代码版权归----所有,未经许可不得任意复制与传播.
 */
package com.rkt.dao.base;
import com.rkt.common.exception.AppException;
import com.rkt.domain.base.TitanSequence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * dao实现类
 * 
 * @author dev-center
 * @since 2014-05-10
 * @param <T>
 *            实体
 * @param <KEY>
 *            主键
 */
public abstract class BaseDaoImpl<T, KEY extends Serializable> extends MyBatisSupport implements BaseDao<T, KEY> {
	private static final String DEFAULT_INSERT_KEY = "insertEntry";
	private static final String DEFAULT_INSERT_LAST_SEQUENCE_KEY = "lastSequence";
	private static final String DEFAULT_DELETE_ARRAY_KEY = "deleteByArrayKey";
	private static final String DEFAULT_DELETE_CONDTION = "deleteByCondtion";
	private static final String DEFAULT_UPDATE_KEY = "updateByKey";
	private static final String DEFAULT_SELECT_ARRAY_KEY = "selectEntryArray";
	private static final String DEFAULT_SELECT_CONDTION = "selectEntryList";
	private static final String DEFAULT_SELECT_CONDTION_COUNT = "selectEntryListCount";
	private static final String TITAN_PROCS_SEQUENCE = "com.dili.titan.TitanSequence.getSequence";

	/**
	 * 获取命名空间前缀
	 * 
	 * @param statement
	 * @return
	 */
	public abstract String getNameSpace(String statement);

	public int insertEntry(T... t) {
		int result = 0;
		if (t == null || t.length <= 0) {
			return result;
		}
		for (T o : t) {
			if (o != null) {
				result += this.insert(getNameSpace(DEFAULT_INSERT_KEY), o);
			}
		}
		return result;
	}

	public int insertEntryCreateId(T t) {
		@SuppressWarnings("unchecked")
		int result = this.insertEntry(t);
		if (result > 0) {
			Integer id = (Integer) select(getNameSpace(DEFAULT_INSERT_LAST_SEQUENCE_KEY), null);
			if (id != null && id > 0) {
				try {
					Class<?> clz = t.getClass();
					clz.getMethod("setId", Integer.class).invoke(t, id);// 最后一次插入编号
				} catch (Exception e) {
					throw new AppException("设置新增主键失败", e);
				}
			}
		}
		return result;
	}

	public int deleteByKey(KEY... key) {
		return this.delete(getNameSpace(DEFAULT_DELETE_ARRAY_KEY), key);
	}

	public int deleteByKey(T t) {
		return this.delete(getNameSpace(DEFAULT_DELETE_CONDTION), t);
	}

	public int updateByKey(T t) {
		return this.update(getNameSpace(DEFAULT_UPDATE_KEY), t);
	}

	public T selectEntry(KEY key) {
		@SuppressWarnings("unchecked")
		List<T> list = this.selectEntryList(key);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<T> selectEntryList(KEY... key) {
		if (key == null || key.length <= 0) {
			return null;
		}
		return this.selectList(getNameSpace(DEFAULT_SELECT_ARRAY_KEY), key);
	}

	public List<T> selectEntryList(T t) {
		return this.selectList(getNameSpace(DEFAULT_SELECT_CONDTION), t);
	}

	public Integer selectEntryListCount(T t) {
		return this.select(getNameSpace(DEFAULT_SELECT_CONDTION_COUNT), t);
	}
	
	public List<T> selectList(T t) {
		return this.selectList(getNameSpace("selectList"), t);
	}

	public Integer selectListCount(T t) {
		return this.select(getNameSpace("selectListCount"), t);
	}

	@Override
	public TitanSequence getSequence(String sqName, long minValue, long maxValue, int step) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("_name", sqName);
		map.put("_min", minValue);
		map.put("_max", maxValue);
		map.put("_step", step);
		return this.select(TITAN_PROCS_SEQUENCE, map);
	}
}
