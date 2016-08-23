package com.rkt.common.web;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class ViewBuilder {

	private BaseController baseController;
	private String template;
	private Map<String, Object> params = new HashMap<String, Object>();

	public ViewBuilder(String template, BaseController bc) {
		this.template = template;
		this.baseController = bc;
	}

	public ModelAndView build() {
		return baseController.toVM(template, params);
	}

	public ViewBuilder param(String key, Object value) {
		params.put(key, value);
		return this;
	}

}
