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
 * Video stream configuration for a Media Processing Service transcode job.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Video {

	/** Video codec. Supports H.264, H.265, GIF, WEBP. Defaults to H.264. */
	@JsonProperty("Codec")
	private String codec;

	/** Video codec profile. */
	@JsonProperty("Profile")
	private String profile;

	/** Video bitrate, in Kbps. */
	@JsonProperty("Bitrate")
	private String bitrate;

	/** Constant rate factor (CRF). */
	@JsonProperty("Crf")
	private String crf;

	/** Output width, in pixels. */
	@JsonProperty("Width")
	private String width;

	/** Output height, in pixels. */
	@JsonProperty("Height")
	private String height;

	/** Frame rate, in fps. */
	@JsonProperty("Fps")
	private String fps;

	/** Group of pictures (GOP) size. */
	@JsonProperty("Gop")
	private String gop;

	/** Encoder preset (speed/quality trade-off). */
	@JsonProperty("Preset")
	private String preset;

	/** Scan mode (interlaced/progressive). */
	@JsonProperty("ScanMode")
	private String scanMode;

	/** Rate control buffer size. */
	@JsonProperty("Bufsize")
	private String bufsize;

	/** Maximum bitrate. */
	@JsonProperty("Maxrate")
	private String maxrate;

	/** Pixel format. */
	@JsonProperty("PixFmt")
	private String pixFmt;

	/** De-grain strength. */
	@JsonProperty("Degrain")
	private String degrain;

	/** Quality scale. */
	@JsonProperty("Qscale")
	private String qscale;

	/** Crop region. */
	@JsonProperty("Crop")
	private String crop;

	/** Padding region. */
	@JsonProperty("Pad")
	private String pad;

	/** Maximum allowed frame rate. */
	@JsonProperty("MaxFps")
	private String maxFps;

	/** Resolution priority. */
	@JsonProperty("ResoPriority")
	private String resoPriority;

	/** Bitrate bounds. */
	@JsonProperty("BitrateBnd")
	private BitrateBnd bitrateBnd;

	/** Bitrate bounds applied during rate control. */
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public static class BitrateBnd {

		/** Maximum bitrate. */
		@JsonProperty("Max")
		private String max;

		/** Minimum bitrate. */
		@JsonProperty("Min")
		private String min;

	}
}