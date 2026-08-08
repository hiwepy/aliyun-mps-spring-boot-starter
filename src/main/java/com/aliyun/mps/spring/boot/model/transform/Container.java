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
 * Output container configuration for a Media Processing Service transcode job.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class Container {

	/**
	 * Container format; defaults to {@code mp4}.
	 * <ul>
	 *  <li>Video transcode supports flv, mp4, HLS (m3u8+ts), MPEG-DASH (MPD+fMP4)</li>
	 *  <li>Audio transcode supports mp3, mp4, ogg, flac, m4a</li>
	 *  <li>Image supports gif, WEBP</li>
	 *  <li>When the container is gif the video codec must be GIF</li>
	 *  <li>When the container is webp the video codec must be WEBP</li>
	 *  <li>When the container is flv the video codec cannot be H.265</li>
	 * </ul>
	 */
	@JsonProperty("Format")
    private String format = "mp4";

}