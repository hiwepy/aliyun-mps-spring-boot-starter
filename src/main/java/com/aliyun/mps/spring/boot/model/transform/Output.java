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

import java.util.List;

import com.aliyun.mps.spring.boot.model.transform.Output.Audio;
import com.aliyun.mps.spring.boot.model.transform.Output.Container;
import com.aliyun.mps.spring.boot.model.transform.Output.TransConfig;
import com.aliyun.mps.spring.boot.model.transform.Output.Video;
import com.aliyun.mps.spring.boot.model.transform.Output.WaterMark;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * TODO
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Output {
	
	private String templateId;

	private String userData;

	/**
	 * 视频旋转角度。范围：[0，360)，顺时针。
	 */
	@JsonProperty("rotate")
	public String rotate;

	private String videoStreamMap;

	private String audioStreamMap;

	private String deWatermark;

	private String priority;

	private String waterMarkConfigUrl;

	private String mergeConfigUrl;

	/**
	 * JSON数组，水印列表，参见 转码水印参数详情。
	 *	 水印数组大小上限为4，既同一路输出最多支持4个水印。
	 * 	示例：[{"InputFile":{"Bucket":"example-bucket","Location":"oss-cn-hangzhou","Object":"example-logo.png"},"WaterMarkTemplateId":"88c6ca184c0e47098a5b665e2a126797"}]
	 */
	@JsonProperty("WaterMarks")
	private List<WaterMark> waterMarks;

	private List<Merge> mergeList;

	private List<Opening> openingList;

	private List<TailSlate> tailSlateList;

	private List<OutSubtitle> outSubtitleList;

	private OutputFile outputFile;

	private M3U8NonStandardSupport m3U8NonStandardSupport;

	private Properties properties;

	private Clip clip;

	private SuperReso superReso;

	private SubtitleConfig subtitleConfig;

	/**
	 * 音频流序号。  格式：0:a:{序号}，序号从0开始，  序号的含义是音频流列表的下标，示例：0:a:0，若不设置，选择默认的音频流。
	 */
	@JsonProperty("TransConfig")
	private TransConfig transConfig;

	private MuxConfig muxConfig;

	/**
	 * 如设置则覆盖指定转码模版中的对应参数，参见Audio详情。 
	 */
	@JsonProperty("Audio")
	public Audio audio;
	
	/**
	 * 如设置则覆盖指定转码模版中的对应参数，参见Video详情。
	 */
	@JsonProperty("Video")
	public Video video;
	

	/**
	 * 如设置则覆盖指定转码模版中的对应参数，参见 Container详情。
	 */
	@JsonProperty("Container")
	public Container container;

	private Encryption encryption;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class WaterMark {

		private String waterMarkTemplateId;

		private String width;

		private String height;

		private String dx;

		private String dy;

		private String referPos;

		private String type;

		private InputFile inputFile;

		public String getWaterMarkTemplateId() {
			return this.waterMarkTemplateId;
		}

		public void setWaterMarkTemplateId(String waterMarkTemplateId) {
			this.waterMarkTemplateId = waterMarkTemplateId;
		}

		public String getWidth() {
			return this.width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public String getHeight() {
			return this.height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public String getDx() {
			return this.dx;
		}

		public void setDx(String dx) {
			this.dx = dx;
		}

		public String getDy() {
			return this.dy;
		}

		public void setDy(String dy) {
			this.dy = dy;
		}

		public String getReferPos() {
			return this.referPos;
		}

		public void setReferPos(String referPos) {
			this.referPos = referPos;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public InputFile getInputFile() {
			return this.inputFile;
		}

		public void setInputFile(InputFile inputFile) {
			this.inputFile = inputFile;
		}

		public static class InputFile {

			private String bucket;

			private String location;

			private String object;

			public String getBucket() {
				return this.bucket;
			}

			public void setBucket(String bucket) {
				this.bucket = bucket;
			}

			public String getLocation() {
				return this.location;
			}

			public void setLocation(String location) {
				this.location = location;
			}

			public String getObject() {
				return this.object;
			}

			public void setObject(String object) {
				this.object = object;
			}
		}
	}

	public static class Merge {

		private String mergeURL;

		private String start;

		private String duration;

		private String roleArn;

		public String getMergeURL() {
			return this.mergeURL;
		}

		public void setMergeURL(String mergeURL) {
			this.mergeURL = mergeURL;
		}

		public String getStart() {
			return this.start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getDuration() {
			return this.duration;
		}

		public void setDuration(String duration) {
			this.duration = duration;
		}

		public String getRoleArn() {
			return this.roleArn;
		}

		public void setRoleArn(String roleArn) {
			this.roleArn = roleArn;
		}
	}

	public static class Opening {

		private String openUrl;

		private String start;

		private String width;

		private String height;

		public String getOpenUrl() {
			return this.openUrl;
		}

		public void setOpenUrl(String openUrl) {
			this.openUrl = openUrl;
		}

		public String getStart() {
			return this.start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getWidth() {
			return this.width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public String getHeight() {
			return this.height;
		}

		public void setHeight(String height) {
			this.height = height;
		}
	}

	public static class TailSlate {

		private String tailUrl;

		private String start;

		private String blendDuration;

		private String width;

		private String height;

		private Boolean isMergeAudio;

		private String bgColor;

		public String getTailUrl() {
			return this.tailUrl;
		}

		public void setTailUrl(String tailUrl) {
			this.tailUrl = tailUrl;
		}

		public String getStart() {
			return this.start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getBlendDuration() {
			return this.blendDuration;
		}

		public void setBlendDuration(String blendDuration) {
			this.blendDuration = blendDuration;
		}

		public String getWidth() {
			return this.width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public String getHeight() {
			return this.height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public Boolean getIsMergeAudio() {
			return this.isMergeAudio;
		}

		public void setIsMergeAudio(Boolean isMergeAudio) {
			this.isMergeAudio = isMergeAudio;
		}

		public String getBgColor() {
			return this.bgColor;
		}

		public void setBgColor(String bgColor) {
			this.bgColor = bgColor;
		}
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class OutSubtitle {

		private String map;

		private Boolean success;

		private String message;

		private OutSubtitleFile outSubtitleFile;

		public String getMap() {
			return this.map;
		}

		public void setMap(String map) {
			this.map = map;
		}

		public Boolean getSuccess() {
			return this.success;
		}

		public void setSuccess(Boolean success) {
			this.success = success;
		}

		public String getMessage() {
			return this.message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public OutSubtitleFile getOutSubtitleFile() {
			return this.outSubtitleFile;
		}

		public void setOutSubtitleFile(OutSubtitleFile outSubtitleFile) {
			this.outSubtitleFile = outSubtitleFile;
		}

		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class OutSubtitleFile {

			private String bucket;

			private String location;

			private String object;

			private String roleArn;

			public String getBucket() {
				return this.bucket;
			}

			public void setBucket(String bucket) {
				this.bucket = bucket;
			}

			public String getLocation() {
				return this.location;
			}

			public void setLocation(String location) {
				this.location = location;
			}

			public String getObject() {
				return this.object;
			}

			public void setObject(String object) {
				this.object = object;
			}

			public String getRoleArn() {
				return this.roleArn;
			}

			public void setRoleArn(String roleArn) {
				this.roleArn = roleArn;
			}
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class OutputFile {

		private String bucket;

		private String location;

		private String object;

		private String roleArn;
 
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class M3U8NonStandardSupport {

		private TS tS;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class TS {

			private Boolean md5Support;

			private Boolean sizeSupport;
 
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Properties {

		private String width;

		private String height;

		private String bitrate;

		private String duration;

		private String fps;

		private String fileSize;

		private String fileFormat;

		private Streams streams;

		private Format format;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Streams {

			private List<VideoStream> videoStreamList;

			private List<AudioStream> audioStreamList;

			private List<SubtitleStream> subtitleStreamList;
			
			@JsonInclude(JsonInclude.Include.NON_NULL)
			@Data
			public static class VideoStream {

				private String index;

				private String codecName;

				private String codecLongName;

				private String profile;

				private String codecTimeBase;

				private String codecTagString;

				private String codecTag;

				private String width;

				private String height;

				private String hasBFrames;

				private String sar;

				private String dar;

				private String pixFmt;

				private String level;

				private String fps;

				private String avgFPS;

				private String timebase;

				private String startTime;

				private String duration;

				private String bitrate;

				private String numFrames;

				private String lang;

				private NetworkCost networkCost;

				@JsonInclude(JsonInclude.Include.NON_NULL)
				@Data
				public static class NetworkCost {

					private String preloadTime;

					private String costBandwidth;

					private String avgBitrate;

					public String getPreloadTime() {
						return this.preloadTime;
					}

					public void setPreloadTime(String preloadTime) {
						this.preloadTime = preloadTime;
					}

					public String getCostBandwidth() {
						return this.costBandwidth;
					}

					public void setCostBandwidth(String costBandwidth) {
						this.costBandwidth = costBandwidth;
					}

					public String getAvgBitrate() {
						return this.avgBitrate;
					}

					public void setAvgBitrate(String avgBitrate) {
						this.avgBitrate = avgBitrate;
					}
				}
			}

			@JsonInclude(JsonInclude.Include.NON_NULL)
			@Data
			public static class AudioStream {

				private String index;

				private String codecName;

				private String codecTimeBase;

				private String codecLongName;

				private String codecTagString;

				private String codecTag;

				private String sampleFmt;

				private String samplerate;

				private String channels;

				private String channelLayout;

				private String timebase;

				private String startTime;

				private String duration;

				private String bitrate;

				private String numFrames;

				private String lang;
				 
			}
			
			@JsonInclude(JsonInclude.Include.NON_NULL)
			@Data
			public static class SubtitleStream {

				private String index;

				private String lang;

			}
		}
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Format {

			private String numStreams;

			private String numPrograms;

			private String formatName;

			private String formatLongName;

			private String startTime;

			private String duration;

			private String size;

			private String bitrate;
			 
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Clip {

		private TimeSpan timeSpan;
		 
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class TimeSpan {

			private String seek;

			private String duration;

			public String getSeek() {
				return this.seek;
			}

			public void setSeek(String seek) {
				this.seek = seek;
			}

			public String getDuration() {
				return this.duration;
			}

			public void setDuration(String duration) {
				this.duration = duration;
			}
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class SuperReso {

		private String isHalfSample;

	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class SubtitleConfig {

		private List<Subtitle> subtitleList;

		private List<ExtSubtitle> extSubtitleList;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Subtitle {

			private String map;

			public String getMap() {
				return this.map;
			}

			public void setMap(String map) {
				this.map = map;
			}
		}
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class ExtSubtitle {

			private String fontName;

			private String charEnc;

			private Input1 input1;
			 
			@JsonInclude(JsonInclude.Include.NON_NULL)
			@Data
			public static class Input1 {

				private String bucket;

				private String location;

				private String object;

				 
			}
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class TransConfig {

		private String transMode;

		private String isCheckReso;

		private String isCheckResoFail;

		private String isCheckVideoBitrate;

		private String isCheckAudioBitrate;

		private String adjDarMethod;

		private String isCheckVideoBitrateFail;

		private String isCheckAudioBitrateFail;
 
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class MuxConfig {

		private Segment segment;

		private Gif gif;

		private Webp webp;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Segment {

			private String duration;
		
		}
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Gif {

			private String loop;

			private String finalDelay;

			private String isCustomPalette;

			private String ditherMode;
			 
		}
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Webp {

			private String loop;
 
		}
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Audio {

		private String codec;

		private String profile;

		private String samplerate;

		private String bitrate;

		private String channels;

		private String qscale;

		private Volume volume;
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Volume {

			private String level;

			private String method;
			 
		}
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Video {

		private String codec;

		private String profile;

		private String bitrate;

		private String crf;

		private String width;

		private String height;

		private String fps;

		private String gop;

		private String preset;

		private String scanMode;

		private String bufsize;

		private String maxrate;

		private String pixFmt;

		private String degrain;

		private String qscale;

		private String crop;

		private String pad;

		private String maxFps;

		private String resoPriority;

		private BitrateBnd bitrateBnd;
		 
		@JsonInclude( JsonInclude.Include.NON_NULL)
		@Data
		public static class BitrateBnd {

			private String max;

			private String min;

		}
	}
	
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public static class Container {

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

	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public static class Encryption {

		private String type;

		private String id;

		private String key;

		private String keyUri;

		private String keyType;

		private String skipCnt;
 
	}
}
