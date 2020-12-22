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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * https://help.aliyun.com/document_detail/29212.html?spm=a2c4g.11174283.6.645.3b01556e9weKGZ
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OutputComplex {
	
	/**
	 * 转码模板ID。支持自定义转码模板与系统预置模板。
	 */
	@JsonProperty("TemplateId")
	private String templateId;
	
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

	private String waterMarkConfigUrl;

	/**
	 * JSON数组，水印列表，参见 转码水印参数详情。
	 *	 水印数组大小上限为4，既同一路输出最多支持4个水印。
	 * 	示例：[{"InputFile":{"Bucket":"example-bucket","Location":"oss-cn-hangzhou","Object":"example-logo.png"},"WaterMarkTemplateId":"88c6ca184c0e47098a5b665e2a126797"}]
	 */
	@JsonProperty("WaterMarks")
	private List<WaterMark> waterMarks;
	
	/**
	 * 拼接设置。最多支持4个MergeURL，参见拼接参数详情。
	 * a、单个拼接片段示例：[{"MergeURL":"http://jvm.oss-cn-hangzhou.aliyuncs.com/tail_comm_01.mp4"}]
	 * b、2个拼接片段示例：[{"MergeURL":"http://jvm.oss-cn-hangzhou.aliyuncs.com/tail_comm_01.mp4","Start":"1","Duration":"20"},{"MergeURL":"http://jvm.oss-cn-hangzhou.aliyuncs.com/tail_comm_02.mp4","Start":"5.4","Duration":"10.2"}]
	 */
	@JsonProperty("MergeList")
	private List<Merge> mergeList;
	
	/**
	 * MergeList与MergeConfigUrl两个参数只支持二选一。
	 * <ul>
	 *  <li>MergeConfigUrl指定的配置文件允许50个拼接片段上限，</li>
	 *  <li>MergeConfigUrl是拼接配置文件URL地址。</li>
	 *  <li>示例：http://jvm.oss-cn-hangzhou.aliyuncs.com/mergeConfigfile</li>
	 *  <li>只支持存放在OSS上的配置文件，且需要保证有授权给MPS可访问权限，文件内部内容参见拼接参数详情。</li>
	 *  <li>mergeConfigfile文件内部内容示例：{"MergeList":[{"MergeURL":"http://jvm.oss-cn-hangzhou.aliyuncs.com/tail_comm.mp4"}]}</li>
	 * </ul>
	 */
	@JsonProperty("MergeConfigUrl")
	private String mergeConfigUrl;
	
	/**
	 * 开板列表。JSON列表。参见开板详情。
	 *示例：[{"OpenUrl":"http://test-bucket.oss-cn-hangzhou.aliyuncs.com/opening.flv","Start":"1","Width":"1920","Height":"1080"}]
	 */
	@JsonProperty("OpeningList")
	private List<Opening> openingList;

	/**
	 * 尾板列表，JSON列表，参见尾板详情。
	 * 示例:[{"TailUrl":"http://test-bucket.oss-cn-hangzhou.aliyuncs.com/tail.flv","Start":"1","BlendDuration":"2","Width":"1920","Height":"1080","IsMergeAudio":false,"BgColor":"White"}]
	 */
	@JsonProperty("TailSlateList")
	private List<TailSlate> tailSlateList;

	private List<OutSubtitle> outSubtitleList;

	private OutputFile outputFile;

	/**
	 * M3u8非标准支持，JSON对象，参见 M3u8非标参数详情。示例:{"TS":{"Md5Support":true,"SizeSupport":true}}
	 */
	@JsonProperty("M3U8NonStandardSupport")
	private M3U8NonStandardSupport m3U8NonStandardSupport;

	@JsonIgnore
	private Properties properties;

	/**
	 * JSON对象，剪辑片段，参见 Clip详情。 
	 */
	@JsonProperty("Clip")
	private Clip clip;

	/**
	 * 转码流程配置。如设置则覆盖指定转码模版中的对应参数，参见TransConfig详情。
	 */
	@JsonProperty("SuperReso")
	private SuperReso superReso;

	/**
	 * JSON对象，字幕配置。  参见SubtitleConfig详情。
	 * 示例：{“ExtSubtitleList”:[{“Input”:{“Bucket”:”example-bucket”,“Location”:”oss-cn-hangzhou”,“Object”:”example.srt”},“CharEnc”:”UTF-8”}]}
	 */
	@JsonProperty("SubtitleConfig")
	private SubtitleConfig subtitleConfig;
	
	/**
	 * 转码流程配置。如设置则覆盖指定转码模版中的对应参数，参见TransConfig详情。
	 */
	@JsonProperty("TransConfig")
	private TransConfig transConfig;

	/**
	 * 如设置则覆盖指定转码模版中的对应参数，参见MuxConfig详情。
	 */
	@JsonProperty("MuxConfig")
	private MuxConfig muxConfig;

	/**
	   * 混音。场景如加背景音乐；同一视频，两音轨合并等。
	   * 配合AudioStreamMap参数以选择输入视频的音轨做混音。
     *JSON列表，示例：[{“AmixURL”:“http://test-bucket.oss-cn-hangzhou.aliyuncs.com/audio.mp3",“Map”:“0:a:0”,“MixDurMode”:"longest”}] 
	 */
	@JsonProperty("Amix")
	private Amix amix;
	
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

	@JsonProperty("Rotate")
	private String videoStreamMap;

	/**
	 * 如设置则覆盖指定转码模版中的对应参数，参见 Container详情。
	 */
	@JsonProperty("Container")
	public Container container;
	
	/**
	 * 数据加密，只支持m3u8格式的输出， 参见Encryption参数详情
	 * 示例：{"Type":"hls-aes-128","Key":"ZW5jcnlwdGlvbmtleTEyMw","KeyType":"Base64","KeyUri":"aHR0cDovL2FsaXl1bi5jb20vZG9jdW1lbnQvaGxzMTI4LmtleQ=="}
	 */
	@JsonProperty("Encryption")
	private Encryption encryption;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Amix {
		
		/**
		 * 需要被混音的背景音轨媒体。取值为：OSS地址或字符串"input"。input场景：同一视频两路音轨合并。
		 */
		@JsonProperty("AmixURL")
		private String amixURL;
		
		/**
		 * 在AmixURL中选取目标音轨，取值为：0:a:{audio_index}, 如0:a:0。
		 */
		@JsonProperty("Map")
		private String map;
		
		/**
		 * 取值：first、longest。   默认为：longest
		 * <ul>
		 *  <li>first：意为输出媒体的时长以输入媒体的时长为准 </li>
		 *  <li>longest：意为输出媒体的时长以两个媒体中时长最长的为准</li>
		 * </ul>
		 */
		@JsonProperty("MixDurMode")
		private String mixDurMode;

	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class WaterMark {

		/**
		 * 水印模板ID。若不设置，则使用水印模板的默认配置：
		 * <ul>
		 *  <li>水印位置TopRight </li>
		 *  <li>偏移量Dx、Dy取0</li>
		 *  <li>水印宽为输出分辨率宽的0.12倍</li>
		 *  <li>水印高为相对水印宽的等比缩放</li>
		 * </ul>  
		 */
		@JsonProperty("WaterMarkTemplateId")
		private String waterMarkTemplateId;

		/**
		 * 若设置，则此值覆盖水印模板对应水印图片宽，
		 * 值有两种形式：
		 * <ul>
		 *  <li>整数型代水印图片宽的像素值，范围：[8，4096]， 单位：px</li>
		 *  <li>小数型代表相对输出视频分辨率宽的比率，范围：(0，1)，支持4位小数，如0.9999，超出部分系统自动丢弃</li>
		 * </ul> 
		 */
		@JsonProperty("Width")
		private String width;

		/**
		 * 若设置，则此值覆盖水印模板对应水印图片高，
		 * 值有两种形式：
		 * <ul>
		 *  <li>整数型代表水印图片高的像素值，范围：[8，4096]， 单位：px</li>
		 *  <li>小数型代表相对输出视频分辨率高的比率，范围：(0，1)，支持4位小数，如0.9999，超出部分系统自动丢弃</li>
		 * </ul> 
		 */
		@JsonProperty("Height")
		private String height;

		/**
		 * 若设置，则此值覆盖水印模板对应参数，水印图片相对输出视频的水平偏移量。默认值：0
		 * 值有两种形式：
		 * <ul>
		 *  <li>整数型代表偏移像素，范围：[8，4096]，单位：px</li>
		 *  <li>小数型代表水平偏移量与输出分辨率宽的比率，范围：(0，1)，支持4位小数，如0.9999，超出部分系统自动丢弃</li>
		 * </ul> 
		 */
		@JsonProperty("Dx")
		private float dx;

		/**
		 * 若设置，则此值覆盖水印模板对应参数，水印图片相对输出视频的垂直偏移量。 默认值：0
		 * 值有两种形式：
		 * <ul>
		 *  <li>整数型代表偏移像素，范围：[8，4096]，单位：px</li>
		 *  <li>小数型代表垂直偏移量与输出分辨率高的比率，范围：(0，1)，支持4位小数，如0.9999，超出部分系统自动丢弃</li>
		 * </ul> 
		 */
		@JsonProperty("Dy")
		private float dy;

		/**
		 * 若设置，则此值覆盖水印模板对应参数，水印的位置。值范围：TopRight、TopLeft、BottomRight、BottomLeft。
		 */
		@JsonProperty("ReferPos")
		private String referPos;

		/**
		 * 若设置，则此值覆盖水印模板对应参数。水印类型，Image、Text。默认值：Image，Image：图片水印，Text：文字水印，
		 */
		@JsonProperty("Type")
		private String type;
		
		/**
		 * 动态水印。参见Timeline详情。
		 */
		@JsonProperty("Timeline")
		private Timeline timeline;
		
		/**
		 * 水印输入文件。参见InputFile详情，目前支持png图片、mov文件作为输入。
		 */
		@JsonProperty("InputFile")
		private InputFile inputFile;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Timeline {

			/**
			 * 水印开始出现时间。  单位：秒， 取值范围：数字，默认值：0
			 */
			@JsonProperty("Start")
			private long start;

			/**
			 * 水印持续时间。 取值范围：[数字，ToEND];默认值：ToEND
			 */
			@JsonProperty("Duration")
			private String duration;

		}
		
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class InputFile {

			/**
			 * 水印输入文件 OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
			 */
			@JsonProperty("Location")
			private String location;
			
			/**
			 * 水印输入文件所在OSS Bucket， 例：example-bucket
			 */
			@JsonProperty("Bucket")
			private String bucket;
			
			/**
			 * 水印输入文件的 文件名（OSS Object）
			 */
			@JsonProperty("Object")
			private String object;
			
		}
	}

	public static class Merge {

		private String mergeURL;

		private String start;

		private String duration;

		private String roleArn;

	}

	public static class Opening {

		private String openUrl;

		private String start;

		private String width;

		private String height;

	}

	public static class TailSlate {

		private String tailUrl;

		private String start;

		private String blendDuration;

		private String width;

		private String height;

		private Boolean isMergeAudio;

		private String bgColor;

	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class OutSubtitle {

		private String map;

		private Boolean success;

		private String message;

		private OutSubtitleFile outSubtitleFile;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class OutSubtitleFile {

			private String bucket;

			private String location;

			private String object;

			private String roleArn;
 
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

		@JsonProperty("TS")
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

		/**
		 * 剪辑时间区间。参见TimeSpan详情。
		 */
		@JsonProperty("TimeSpan")
		private TimeSpan timeSpan;
		
		/**
		 * 是否剪辑第一片，   false：拼接完后剪辑，默认;true：先剪辑第一片后拼接
		 */
		@JsonProperty("ConfigToClipFirstPart")
		private boolean configToClipFirstPart;
		 
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class TimeSpan {

			/**
			 * 开始时间点。
			 * <ul>
			 *  <li>格式：hh:mm:ss[.SSS]， 取值范围 ：[00:00:00.000,23:59:59.999]</li>
			 *  <li>格式：sssss[.SSS]，取值范围［0.000，86399.999］</li>
			 *  <li>示例：01:59:59.999或者32000.23</li>
			 * </ul>
			 */
			@JsonProperty("Seek")
			private String seek;

			/**
			 * 持续时长。
			 * <ul>
			 *  <li>格式：hh:mm:ss[.SSS]，取值范围 ：[00:00:00.000,23:59:59.999] </li>
			 *  <li>格式：sssss[.SSS]，取值范围［0.000，86399.999］</li>
			 *  <li>示例：01:00:59.999或者32000.23</li>
			 * </ul>
			 */
			@JsonProperty("Duration")
			private String duration;
			
			/**
			 * 截尾时长，表示切掉尾部的若干时长。设置此值时，参数Duration失效。
			 * <ul>
			 *  <li>格式：hh:mm:ss[.SSS]，取值范围 ：[00:00:00.000,23:59:59.999] </li>
			 *  <li>格式：sssss[.SSS]，取值范围［0.000，86399.999］</li>
			 *  <li>示例：01:00:59.999或者32000.23</li>
			 * </ul>
			 */
			@JsonProperty("End")
			private String end;
 
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

		/**
		 * 支持H.264、H.265、GIF、WEBP。默认值：H.264
		 */
		@JsonProperty("Codec")
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

		/**
		 * 取值：hls-aes-128
		 */
		@JsonProperty("Type")
		private String type;

		/**
		 * 加密视频的密钥。 需加密，方式见KeyType。如密钥为”encryptionkey128”, 则Base64(“encryptionkey128”)， 或 KMS(Base64(“encryptionkey128”)
		 */
		@JsonProperty("Key")
		private String key;

		/**
		 * 密钥的访问url，使用BASE64进行编码。
		 */
		@JsonProperty("KeyUri")
		private String keyUri;

		/**
		 * 密钥Key不能明文传输给MPS，需要加密，方式为 Base64 或 KMS 如使用KMS。 
		 */
		@JsonProperty("KeyType")
		private String keyType;
 
	}
}
