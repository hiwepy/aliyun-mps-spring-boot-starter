/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aliyun.mps.spring.boot.model.transform;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Audio stream configuration for a Media Processing Service transcode job.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Audio {

	/** Audio codec, e.g. {@code aac}, {@code mp3}. */
	@JsonProperty("Codec")
	private String codec;

	/** Audio codec profile. */
	@JsonProperty("Profile")
	private String profile;

	/** Audio sample rate, in Hz. */
	@JsonProperty("Samplerate")
	private String samplerate;

	/** Audio bitrate, in Kbps. */
	@JsonProperty("Bitrate")
	private String bitrate;

	/** Number of audio channels. */
	@JsonProperty("Channels")
	private String channels;

	/** Quality scale. */
	@JsonProperty("Qscale")
	private String qscale;

	/** Volume control configuration. */
	@JsonProperty("Volume")
	private Volume volume;

	/** Audio volume control parameters. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Volume {

		/** Target volume level. */
		@JsonProperty("Level")
		private String level;

		/** Volume adjustment method. */
		@JsonProperty("Method")
		private String method;

	}
}