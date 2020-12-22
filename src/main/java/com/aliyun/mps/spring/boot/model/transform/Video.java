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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Video {

	/**
	 * 支持H.264、H.265、GIF、WEBP。默认值：H.264
	 */
	@JsonProperty("Codec")
	private String codec;

	/**
	 */
	@JsonProperty("Profile")
	private String profile;
	
	@JsonProperty("Bitrate")
	private String bitrate;
	
	@JsonProperty("Crf")
	private String crf;

	@JsonProperty("Width")
	private String width;

	@JsonProperty("Height")
	private String height;
	
	@JsonProperty("Fps")
	private String fps;

	@JsonProperty("Gop")
	private String gop;

	@JsonProperty("Preset")
	private String preset;

	@JsonProperty("ScanMode")
	private String scanMode;

	@JsonProperty("Bufsize")
	private String bufsize;

	@JsonProperty("Maxrate")
	private String maxrate;

	@JsonProperty("PixFmt")
	private String pixFmt;

	@JsonProperty("Degrain")
	private String degrain;

	@JsonProperty("Qscale")
	private String qscale;

	@JsonProperty("Crop")
	private String crop;

	@JsonProperty("Pad")
	private String pad;

	@JsonProperty("MaxFps")
	private String maxFps;

	@JsonProperty("ResoPriority")
	private String resoPriority;

	@JsonProperty("BitrateBnd")
	private BitrateBnd bitrateBnd;
	
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public static class BitrateBnd {

		@JsonProperty("Max")
		private String max;

		@JsonProperty("Min")
		private String min;

	}
}