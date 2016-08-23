/*
 * Copyright (c) 2014 www.diligrp.com All rights reserved.
 * 本软件源代码版权归----所有,未经许可不得任意复制与传播.
 */
package com.rkt.domain.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;


public class BaseDomain extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;// 编号
	
	private String uuid;// 唯一编号
	
	private String code;// 编码
	
	private String remark;// 备注
	
	private Date createDate;// 创建日期
	
	private String createUser;// 创建者
	
	private Date modifyDate;// 最后修改日期
	
	private String modifyUser;// 最后修改者
	
	private Integer isDel;// 是否删除
	
	private String createUserErp;//创建者erp
	
	private String modifyUserErp;//更新者erp
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JSONField(serialize=false)
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@JSONField(serialize=false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@JSONField(serialize=false)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JSONField(serialize=false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@JSONField(serialize=false)
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@JSONField(serialize=false)
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@JSONField(serialize=false)
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	@JSONField(serialize=false)
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	public String getCreateUserErp() {
		return createUserErp;
	}
	public void setCreateUserErp(String createUserErp) {
		this.createUserErp = createUserErp;
	}
	public String getModifyUserErp() {
		return modifyUserErp;
	}
	public void setModifyUserErp(String modifyUserErp) {
		this.modifyUserErp = modifyUserErp;
	}
}
