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

@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class Container {

	/**
	 * 容器格式;默认值：mp4 
	 * <ul>
	 *  <li>视频转码支持flv、mp4、HLS（m3u8+ts）、MPEG-DASH（MPD+fMP4）</li>
	 *  <li>音频转码支持mp3、mp4、ogg、flac、m4a</li>
	 *  <li>图片支持gif、WEBP</li>
	 *  <li>容器格式为gif时，Video Codec设置只能设置为GIF，</li>
	 *  <li>容器格式为webp时，Video Codec设置只能设置为WEBP，</li>
	 *  <li>容器格式为flv时，Video Codec不能设置为H.265。</li>
	 * </ul>
	 */
	@JsonProperty("Format")
    private String format = "mp4";
	
}