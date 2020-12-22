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
 * TODO
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OutputSimple {
	
	/**
	 * 转码模板ID。支持自定义转码模板与系统预置模板。
	 */
	@JsonProperty("TemplateId")
	private String templateId;

	/**
	 * 如设置则覆盖指定转码模版中的对应参数，参见 Container详情。
	 */
	@JsonProperty("Container")
	public Container container;
	
	/**
	 * 输出的文件名（OSS Object）。
	 * <ul>
	 *  <li>须进行Url Encode，使用UTF-8编码。</li>
	 *  <li> 占位符替换示例：转码输入文件若为a/b/c.flv，若OutputObject设置为%7BObjectPrefix%7D%7BFileName%7Dtest.mp4，那么转码输出文件名：a/b/ctest.mp4</li>
	 *  <li>默认值：6</li>
	 * </ul>
	 *  输出文件名支持占位符替换规则：
	 * <ul>
	 *  <li>工作流支持的占位符：代表输入文件前缀的{ObjectPrefix}、代表输入文件名的{FileName}、代表输入文件扩展名的{ExtName}、代表转码输出文件Md5值 的{DestMd5}、代表转码输出文件平均码率的{DestAvgBitrate}，以及代表媒体工作流执行实例ID的{RunId},代表工作流所处理媒体ID的{MediaId}的动态替换。</li>
	 *  <li>非工作流支持的占位符：{ObjectPrefix}、{FileName}、{ExtName}、{DestMd5}、{DestAvgBitrate}</li>
	 * </ul>
	 *  关于文件扩展名规则：
	 * <ul>
	 *  <li>工作流：根据转码模板容器格式自动在OutputObject后边添加扩展名。</li>
	 *  <li>非工作流：不会自动添加扩展名，但如果容器类型为m3u8，则媒体处理服务会给Playlist自动添加扩展名 .m3u8 ，分片文件名会在Playlist后自动加一个从00001开始的5位</li>
	 *  <li>序列号为后缀并以 - 号相连，文件扩展名为 .ts。</li>
	 *  <li>例如：Playlist文件名为filename.m3u8，则输出第一个ts分片文件为filename-00001.ts。</li>
	 * </ul>
	 */
	@JsonProperty("OutputObject")
	private String outputObject;
	
	/**
	 * 用户自定义数据，最大长度1024个字节。
	 */
	@JsonProperty("UserData")
	private String userData;

	/**
	 * 视频旋转角度。范围：[0，360)，顺时针。
	 */
	@JsonProperty("Rotate")
	public String rotate;

	/**
	 * 模糊处理，JSON对象。参见模糊处理详情。
	 */
	@JsonProperty("DeWatermark")
	private String deWatermark;

	/**
	 * 任务在其对应管道内的转码优先级。
	 * <ul>
	 *  <li>范围：[1-10]，</li>
	 *  <li>最高优先级：10</li>
	 *  <li>默认值：6</li>
	 * </ul>
	 */
	@JsonProperty("Priority")
	private String priority = "6";

	/**
	 * 如设置则覆盖指定转码模版中的对应参数，参见Audio详情。 
	 */
	@JsonProperty("Audio")
	public Audio audio;

	/**
	 * 音频流序号。  格式：0:a:{序号}，序号从0开始，  序号的含义是音频流列表的下标，示例：0:a:0，若不设置，选择默认的音频流。
	 */
	@JsonProperty("AudioStreamMap")
	private String audioStreamMap;
	
	/**
	 * 如设置则覆盖指定转码模版中的对应参数，参见Video详情。
	 */
	@JsonProperty("Video")
	public Video video;

	@JsonProperty("VideoStreamMap")
	private String videoStreamMap;
 
}
