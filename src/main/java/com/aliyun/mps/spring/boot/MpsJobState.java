package com.aliyun.mps.spring.boot;

import java.util.NoSuchElementException;

/**
 * State of a Media Processing Service job.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
public enum MpsJobState {

	/** The job has been submitted. */
	SUBMITTED("Submitted"),
	/** The transcode job succeeded. */
	TRANSCODE_SUCCESS("TranscodeSuccess"),
	/** The transcode job failed. */
	TRANSCODE_FAIL("TranscodeFail");

	private String state;

	private MpsJobState(String state) {
		this.state = state;
	}

	/**
	 * Returns the raw state string returned by MPS.
	 * @return the state string
	 */
	public String getState() {
		return state;
	}

	/**
	 * Compares this state with another {@link MpsJobState}.
	 * @param state the state to compare with
	 * @return {@code true} when both states are equal
	 */
	public boolean equals(MpsJobState state) {
		return this.compareTo(state) == 0;
	}

	/**
	 * Compares this state with the given state string (case-insensitive).
	 * @param state the state string to compare with
	 * @return {@code true} when both states are equal
	 */
	public boolean equals(String state) {
		return this.compareTo(MpsJobState.valueOfIgnoreCase(state)) == 0;
	}

	/**
	 * Resolves a {@link MpsJobState} by name, ignoring case.
	 * @param key the state name
	 * @return the matching state
	 * @throws NoSuchElementException when no state matches the given key
	 */
	public static MpsJobState valueOfIgnoreCase(String key) {
		for (MpsJobState thirdpartyEnum : MpsJobState.values()) {
			if (thirdpartyEnum.name().equalsIgnoreCase(key)) {
				return thirdpartyEnum;
			}
		}
		throw new NoSuchElementException("Cannot found Thirdparty with key '" + key + "'.");
	}

}