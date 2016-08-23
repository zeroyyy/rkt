package com.rkt.common.exception;

public final class TitanError extends Exception {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private int code;
	private String result;

	public TitanError(int code, String result) {
		super();
		this.code = code;
		this.result = result;
	}

	public final static TitanError DEFAULT_ERROR = new TitanError(0, "请求失败");

	// 输入错误
	public final static TitanError JSON_CONVERT_ERROR = new TitanError(10, "JSON转换失败，请检查输入参数");
	public final static TitanError JSON_FORMAT_ERROR = new TitanError(11, "JSON格式错误");
	public final static TitanError NULL_PARAM_ERROR = new TitanError(12, "输入参数为空");
	public final static TitanError INVALID_PARAM_ERROR = new TitanError(13, "输入参数不正确");

	public final static TitanError READ_BODY_ERROR = new TitanError(20, "读取请求内容失败");

	/*** 业务错误 **/
	// 库存错误
	public final static TitanError LOW_STOCKS = new TitanError(30, "库存不足");
	public final static TitanError DUPLICATE_DECRASE = new TitanError(31, "重复扣减库存");
	public final static TitanError DUPLICATE_INCRASE = new TitanError(31, "重复增加库存");

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String getMessage() {
		return this.getResult();
	}

}
