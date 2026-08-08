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
 * Simple transcode output descriptor (format conversion only).
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OutputSimple {

	/** Transcode template id. Both custom and system-preset templates are supported. */
	@JsonProperty("TemplateId")
	private String templateId;

	/** When set, overrides the matching parameter of the referenced transcode template (see Container). */
	@JsonProperty("Container")
	public Container container;

	/**
	 * Output file name (OSS object key).
	 * <ul>
	 *  <li>Must be URL-encoded using UTF-8.</li>
	 *  <li>Placeholder example: when the input file is {@code a/b/c.flv} and OutputObject is
	 *      {@code %7BObjectPrefix%7D%7BFileName%7Dtest.mp4} the resulting output name is {@code a/b/ctest.mp4}</li>
	 * </ul>
	 *  Output file name placeholders:
	 * <ul>
	 *  <li>Workflow placeholders: {ObjectPrefix} (input file prefix), {FileName} (input file name),
	 *      {ExtName} (input file extension), {DestMd5} (output md5), {DestAvgBitrate} (output average bitrate),
	 *      {RunId} (workflow execution instance id) and {MediaId} (the media id processed by the workflow).</li>
	 *  <li>Non-workflow placeholders: {ObjectPrefix}, {FileName}, {ExtName}, {DestMd5}, {DestAvgBitrate}</li>
	 * </ul>
	 *  File extension rules:
	 * <ul>
	 *  <li>Workflow: appends an extension based on the template container format.</li>
	 *  <li>Non-workflow: no automatic extension; for m3u8 containers the playlist is suffixed with {@code .m3u8}
	 *      and ts segments are named with a 5-digit sequence number starting from {@code 00001} joined by {@code -},
	 *      e.g. playlist {@code filename.m3u8} produces a first segment {@code filename-00001.ts}.</li>
	 * </ul>
	 */
	@JsonProperty("OutputObject")
	private String outputObject;

	/** User-defined data, up to 1024 bytes. */
	@JsonProperty("UserData")
	private String userData;

	/** Video rotation angle. Range [0, 360), clockwise. */
	@JsonProperty("Rotate")
	public String rotate;

	/** De-watermark (blurring) configuration, serialised to a JSON object. */
	@JsonProperty("DeWatermark")
	private String deWatermark;

	/**
	 * Transcode priority of the job within its pipeline.
	 * <ul>
	 *  <li>Range: [1-10]</li>
	 *  <li>Highest priority: 10</li>
	 *  <li>Default: 6</li>
	 * </ul>
	 */
	@JsonProperty("Priority")
	private String priority = "6";

	/** When set, overrides the matching parameter of the referenced transcode template (see Audio). */
	@JsonProperty("Audio")
	public Audio audio;

	/**
	 * Audio stream selector. Format {@code 0:a:{index}} where the index is 0-based and refers to
	 * the audio stream list, e.g. {@code 0:a:0}. When unset the default audio stream is used.
	 */
	@JsonProperty("AudioStreamMap")
	private String audioStreamMap;

	/** When set, overrides the matching parameter of the referenced transcode template (see Video). */
	@JsonProperty("Video")
	public Video video;

	/** Video stream selector. */
	@JsonProperty("VideoStreamMap")
	private String videoStreamMap;

}
