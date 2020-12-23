package com.aliyun.mps.spring.boot;

import java.util.NoSuchElementException;

/**
 * 媒体处理任务状态
 */
public enum MpsJobState {
	
	/**
	 * 任务已经提交
	 */
	SUBMITTED("Submitted"),
	/**
	 * 转换任务执行成功
	 */
	TRANSCODE_SUCCESS("TranscodeSuccess"),
	/**
	 * 转换任务执行失败
	 */
	TRANSCODE_FAIL("TranscodeFail");

	private String state;

	private MpsJobState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public boolean equals(MpsJobState state) {
		return this.compareTo(state) == 0;
	}

	public boolean equals(String state) {
		return this.compareTo(MpsJobState.valueOfIgnoreCase(state)) == 0;
	}

	public static MpsJobState valueOfIgnoreCase(String key) {
		for (MpsJobState thirdpartyEnum : MpsJobState.values()) {
			if (thirdpartyEnum.name().equalsIgnoreCase(key)) {
				return thirdpartyEnum;
			}
		}
		throw new NoSuchElementException("Cannot found Thirdparty with key '" + key + "'.");
	}
	 
}