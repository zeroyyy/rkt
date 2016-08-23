package com.rkt.domain.base;

import java.io.Serializable;

public class TitanSequence implements Serializable {
	private static final long serialVersionUID = -8792012282149682726L;

	private Long minValue;
	private Long maxValue;

	public Long getMinValue() {
		return minValue;
	}

	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}

	public Long getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}

}
