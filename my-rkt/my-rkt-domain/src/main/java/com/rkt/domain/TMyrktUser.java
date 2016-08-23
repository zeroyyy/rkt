/*
 * Copyright (c) 2016 www.diligrp.com All rights reserved.
 * 本软件源代码版权归----所有,未经许可不得任意复制与传播.
 */
package com.rkt.domain;

import com.rkt.domain.base.BaseDomain;

import java.util.Date;

/**
 * tMyrktUser
 * @author dev-center
 * @since 2016-07-18
 */
public class TMyrktUser extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer age;
	private Date ctime;
	private String utime;

	public TMyrktUser(){
		//默认无参构造方法
	}

	/**
	 * 获取 name
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 设置 name
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * 获取 age
	 * @return
	 */
	public Integer getAge(){
		return age;
	}
	
	/**
	 * 设置 age
	 * @param age
	 */
	public void setAge(Integer age){
		this.age = age;
	}

	/**
	 * 获取 ctime
	 * @return
	 */
	public Date getCtime(){
		return ctime;
	}
	
	/**
	 * 设置 ctime
	 * @param ctime
	 */
	public void setCtime(Date ctime){
		this.ctime = ctime;
	}

	/**
	 * 获取 utime
	 * @return
	 */
	public String getUtime(){
		return utime;
	}
	
	/**
	 * 设置 utime
	 * @param utime
	 */
	public void setUtime(String utime){
		this.utime = utime;
	}
}