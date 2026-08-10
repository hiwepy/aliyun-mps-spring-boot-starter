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
 * Complex transcode output descriptor (format conversion, merging, clipping).
 * <p>See <a href="https://help.aliyun.com/document_detail/29212.html">the Output parameter reference</a>.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OutputComplex {

	/** Transcode template id. Both custom and system-preset templates are supported. */
	@JsonProperty("TemplateId")
	private String templateId;

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

	/** URL of an external watermark configuration file. */
	private String waterMarkConfigUrl;

	/**
	 * Watermark list, serialised to a JSON array (see the watermark parameter details).
	 * Up to 4 watermarks per output.
	 * Example: {@code [{"InputFile":{"Bucket":"example-bucket","Location":"oss-cn-hangzhou","Object":"example-logo.png"},"WaterMarkTemplateId":"88c6ca184c0e47098a5b665e2a126797"}]}
	 */
	@JsonProperty("WaterMarks")
	private List<WaterMark> waterMarks;

	/**
	 * Merge (concatenation) settings. Up to 4 MergeURL entries (see the merge parameter details).
	 * Single-clip example: {@code [{"MergeURL":"http://jvm.oss-cn-hangzhou.aliyuncs.com/tail_comm_01.mp4"}]}.
	 * Two-clip example: {@code [{"MergeURL":".../tail_comm_01.mp4","Start":"1","Duration":"20"},{"MergeURL":".../tail_comm_02.mp4","Start":"5.4","Duration":"10.2"}]}.
	 */
	@JsonProperty("MergeList")
	private List<Merge> mergeList;

	/**
	 * Mutually exclusive with {@link #mergeList}.
	 * <ul>
	 *  <li>The configuration file referenced by MergeConfigUrl supports up to 50 merge clips.</li>
	 *  <li>MergeConfigUrl is the URL of the merge configuration file.</li>
	 *  <li>Example: {@code http://jvm.oss-cn-hangzhou.aliyuncs.com/mergeConfigfile}</li>
	 *  <li>Only OSS-hosted configuration files are supported and must be authorised for MPS access.</li>
	 *  <li>Example file content: {@code {"MergeList":[{"MergeURL":"http://jvm.oss-cn-hangzhou.aliyuncs.com/tail_comm.mp4"}]}}</li>
	 * </ul>
	 */
	@JsonProperty("MergeConfigUrl")
	private String mergeConfigUrl;

	/**
	 * Opening list, serialised to a JSON array (see the opening parameter details).
	 * Example: {@code [{"OpenUrl":"http://test-bucket.oss-cn-hangzhou.aliyuncs.com/opening.flv","Start":"1","Width":"1920","Height":"1080"}]}
	 */
	@JsonProperty("OpeningList")
	private List<Opening> openingList;

	/**
	 * Tail-slate list, serialised to a JSON array (see the tail-slate parameter details).
	 * Example: {@code [{"TailUrl":".../tail.flv","Start":"1","BlendDuration":"2","Width":"1920","Height":"1080","IsMergeAudio":false,"BgColor":"White"}]}
	 */
	@JsonProperty("TailSlateList")
	private List<TailSlate> tailSlateList;

	/** External subtitle outputs. */
	private List<OutSubtitle> outSubtitleList;

	/** Output file location. */
	private OutputFile outputFile;

	/**
	 * M3U8 non-standard support, serialised to a JSON object.
	 * Example: {@code {"TS":{"Md5Support":true,"SizeSupport":true}}}
	 */
	@JsonProperty("M3U8NonStandardSupport")
	private M3U8NonStandardSupport m3U8NonStandardSupport;

	/** Parsed properties of the input media (ignored when serialising). */
	@JsonIgnore
	private Properties properties;

	/** Clip (segment) configuration, serialised to a JSON object. */
	@JsonProperty("Clip")
	private Clip clip;

	/** Super-resolution configuration; overrides the matching template parameter when set. */
	@JsonProperty("SuperReso")
	private SuperReso superReso;

	/**
	 * Subtitle configuration, serialised to a JSON object.
	 * Example: {@code {"ExtSubtitleList":[{"Input":{"Bucket":"example-bucket","Location":"oss-cn-hangzhou","Object":"example.srt"},"CharEnc":"UTF-8"}]}}
	 */
	@JsonProperty("SubtitleConfig")
	private SubtitleConfig subtitleConfig;

	/** Transcode flow configuration; overrides the matching template parameter when set. */
	@JsonProperty("TransConfig")
	private TransConfig transConfig;

	/** Muxing configuration; overrides the matching template parameter when set. */
	@JsonProperty("MuxConfig")
	private MuxConfig muxConfig;

	/**
	 * Audio mixing (e.g. adding background music or merging two audio tracks of the same video).
	 * Use together with {@link #audioStreamMap} to select the input audio track.
	 * Example: {@code [{"AmixURL":"http://test-bucket.oss-cn-hangzhou.aliyuncs.com/audio.mp3","Map":"0:a:0","MixDurMode":"longest"}]}
	 */
	@JsonProperty("Amix")
	private Amix amix;

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
	@JsonProperty("Rotate")
	private String videoStreamMap;

	/** When set, overrides the matching parameter of the referenced transcode template (see Container). */
	@JsonProperty("Container")
	public Container container;

	/**
	 * Data encryption; only supported for m3u8 output (see the Encryption parameter details).
	 * Example: {@code {"Type":"hls-aes-128","Key":"ZW5jcnlwdGlvbmtleTEyMw","KeyType":"Base64","KeyUri":"aHR0cDovL2FsaXl1bi5jb20vZG9jdW1lbnQvaGxzMTI4LmtleQ=="}}
	 */
	@JsonProperty("Encryption")
	private Encryption encryption;

	/** Audio mixing parameters. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Amix {

		/**
		 * Background audio to mix in. Either an OSS URL or the string {@code "input"}
		 * (used to merge two audio tracks of the same video).
		 */
		@JsonProperty("AmixURL")
		private String amixURL;

		/** Target audio track within AmixURL, e.g. {@code 0:a:0}. */
		@JsonProperty("Map")
		private String map;

		/**
		 * Output duration mode. Defaults to {@code longest}.
		 * <ul>
		 *  <li>{@code first}: output duration matches the input media</li>
		 *  <li>{@code longest}: output duration matches the longer of the two media</li>
		 * </ul>
		 */
		@JsonProperty("MixDurMode")
		private String mixDurMode;

	}

	/** Watermark parameters. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class WaterMark {

		/**
		 * Watermark template id. When unset the template defaults apply:
		 * <ul>
		 *  <li>Position: TopRight</li>
		 *  <li>Offset Dx/Dy: 0</li>
		 *  <li>Width: 0.12 of the output width</li>
		 *  <li>Height: proportional to the width</li>
		 * </ul>
		 */
		@JsonProperty("WaterMarkTemplateId")
		private String waterMarkTemplateId;

		/**
		 * When set, overrides the template watermark width.
		 * Two forms are accepted:
		 * <ul>
		 *  <li>Integer: pixel width, range [8, 4096]</li>
		 *  <li>Decimal: ratio of the output width, range (0, 1) with up to 4 decimals (extra digits are dropped)</li>
		 * </ul>
		 */
		@JsonProperty("Width")
		private String width;

		/**
		 * When set, overrides the template watermark height.
		 * Two forms are accepted:
		 * <ul>
		 *  <li>Integer: pixel height, range [8, 4096]</li>
		 *  <li>Decimal: ratio of the output height, range (0, 1) with up to 4 decimals (extra digits are dropped)</li>
		 * </ul>
		 */
		@JsonProperty("Height")
		private String height;

		/**
		 * When set, overrides the template horizontal offset of the watermark. Defaults to 0.
		 * Two forms are accepted:
		 * <ul>
		 *  <li>Integer: pixel offset, range [8, 4096]</li>
		 *  <li>Decimal: ratio of the output width, range (0, 1) with up to 4 decimals (extra digits are dropped)</li>
		 * </ul>
		 */
		@JsonProperty("Dx")
		private float dx;

		/**
		 * When set, overrides the template vertical offset of the watermark. Defaults to 0.
		 * Two forms are accepted:
		 * <ul>
		 *  <li>Integer: pixel offset, range [8, 4096]</li>
		 *  <li>Decimal: ratio of the output height, range (0, 1) with up to 4 decimals (extra digits are dropped)</li>
		 * </ul>
		 */
		@JsonProperty("Dy")
		private float dy;

		/** When set, overrides the template watermark position: TopRight, TopLeft, BottomRight, BottomLeft. */
		@JsonProperty("ReferPos")
		private String referPos;

		/** When set, overrides the template watermark type: Image (default) or Text. */
		@JsonProperty("Type")
		private String type;

		/** Dynamic watermark timing. */
		@JsonProperty("Timeline")
		private Timeline timeline;

		/** Watermark input file. Supports png images and mov files. */
		@JsonProperty("InputFile")
		private InputFile inputFile;

		/** Dynamic watermark timing parameters. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Timeline {

			/** Time the watermark starts to appear, in seconds. Defaults to 0. */
			@JsonProperty("Start")
			private long start;

			/** Watermark duration. Range: a number or {@code ToEND}. Defaults to {@code ToEND}. */
			@JsonProperty("Duration")
			private String duration;

		}

		/** Watermark input file descriptor. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class InputFile {

			/** OSS location (region id) of the watermark input bucket, e.g. {@code oss-cn-hangzhou}. */
			@JsonProperty("Location")
			private String location;

			/** OSS bucket holding the watermark input object, e.g. {@code example-bucket}. */
			@JsonProperty("Bucket")
			private String bucket;

			/** Watermark input file name (OSS object key). */
			@JsonProperty("Object")
			private String object;

		}
	}

	/** Merge (concatenation) clip descriptor. */
	public static class Merge {

		/** URL of the clip to merge. */
		private String mergeURL;

		/** Start time of the clip. */
		private String start;

		/** Duration of the clip. */
		private String duration;

		/** ARN of the role used to access the clip. */
		private String roleArn;

	}

	/** Opening clip descriptor. */
	public static class Opening {

		/** URL of the opening clip. */
		private String openUrl;

		/** Start time of the opening. */
		private String start;

		/** Opening width, in pixels. */
		private String width;

		/** Opening height, in pixels. */
		private String height;

	}

	/** Tail-slate clip descriptor. */
	public static class TailSlate {

		/** URL of the tail clip. */
		private String tailUrl;

		/** Start time of the tail. */
		private String start;

		/** Blend duration between the tail and the main video. */
		private String blendDuration;

		/** Tail width, in pixels. */
		private String width;

		/** Tail height, in pixels. */
		private String height;

		/** Whether to merge the tail audio. */
		private Boolean isMergeAudio;

		/** Background colour of the tail. */
		private String bgColor;

	}

	/** External subtitle output descriptor. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class OutSubtitle {

		/** Subtitle stream selector. */
		private String map;

		/** Whether the subtitle was extracted successfully. */
		private Boolean success;

		/** Status message. */
		private String message;

		/** Extracted subtitle file. */
		private OutSubtitleFile outSubtitleFile;

		/** Extracted subtitle file descriptor. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class OutSubtitleFile {

			/** OSS bucket of the subtitle file. */
			private String bucket;

			/** OSS location (region id) of the subtitle file. */
			private String location;

			/** Subtitle file name (OSS object key). */
			private String object;

			/** ARN of the role used to access the subtitle file. */
			private String roleArn;

		}
	}


	/** M3U8 non-standard support configuration. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class M3U8NonStandardSupport {

		/** TS-segment non-standard support. */
		@JsonProperty("TS")
		private TS tS;

		/** TS-segment non-standard support parameters. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class TS {

			/** Whether to include the md5 of TS segments. */
			private Boolean md5Support;

			/** Whether to include the size of TS segments. */
			private Boolean sizeSupport;

		}
	}

	/** Parsed properties of the input media. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Properties {

		/** Media width, in pixels. */
		private String width;

		/** Media height, in pixels. */
		private String height;

		/** Media bitrate. */
		private String bitrate;

		/** Media duration. */
		private String duration;

		/** Media frame rate. */
		private String fps;

		/** Media file size. */
		private String fileSize;

		/** Media file format. */
		private String fileFormat;

		/** Stream-level metadata. */
		private Streams streams;

		/** Container-level metadata. */
		private Format format;

		/** Stream-level metadata. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Streams {

			/** Video streams. */
			private List<VideoStream> videoStreamList;

			/** Audio streams. */
			private List<AudioStream> audioStreamList;

			/** Subtitle streams. */
			private List<SubtitleStream> subtitleStreamList;

			/** Video stream metadata. */
			@JsonInclude(JsonInclude.Include.NON_NULL)
			@Data
			public static class VideoStream {

				/** Stream index. */
				private String index;

				/** Codec name. */
				private String codecName;

				/** Long codec name. */
				private String codecLongName;

				/** Codec profile. */
				private String profile;

				/** Codec time base. */
				private String codecTimeBase;

				/** Codec tag string. */
				private String codecTagString;

				/** Codec tag. */
				private String codecTag;

				/** Width, in pixels. */
				private String width;

				/** Height, in pixels. */
				private String height;

				/** Whether the stream has B-frames. */
				private String hasBFrames;

				/** Sample aspect ratio. */
				private String sar;

				/** Display aspect ratio. */
				private String dar;

				/** Pixel format. */
				private String pixFmt;

				/** Codec level. */
				private String level;

				/** Frame rate. */
				private String fps;

				/** Average frame rate. */
				private String avgFPS;

				/** Time base. */
				private String timebase;

				/** Start time. */
				private String startTime;

				/** Duration. */
				private String duration;

				/** Bitrate. */
				private String bitrate;

				/** Number of frames. */
				private String numFrames;

				/** Language. */
				private String lang;

				/** Network cost metadata. */
				private NetworkCost networkCost;

				/** Network cost metadata for the video stream. */
				@JsonInclude(JsonInclude.Include.NON_NULL)
				@Data
				public static class NetworkCost {

					/** Preload time. */
					private String preloadTime;

					/** Cost bandwidth. */
					private String costBandwidth;

					/** Average bitrate. */
					private String avgBitrate;

					/** @return the preload time. */
					public String getPreloadTime() {
						return this.preloadTime;
					}

					/** @param preloadTime the preload time to set. */
					public void setPreloadTime(String preloadTime) {
						this.preloadTime = preloadTime;
					}

					/** @return the cost bandwidth. */
					public String getCostBandwidth() {
						return this.costBandwidth;
					}

					/** @param costBandwidth the cost bandwidth to set. */
					public void setCostBandwidth(String costBandwidth) {
						this.costBandwidth = costBandwidth;
					}

					/** @return the average bitrate. */
					public String getAvgBitrate() {
						return this.avgBitrate;
					}

					/** @param avgBitrate the average bitrate to set. */
					public void setAvgBitrate(String avgBitrate) {
						this.avgBitrate = avgBitrate;
					}
				}
			}

			/** Audio stream metadata. */
			@JsonInclude(JsonInclude.Include.NON_NULL)
			@Data
			public static class AudioStream {

				/** Stream index. */
				private String index;

				/** Codec name. */
				private String codecName;

				/** Codec time base. */
				private String codecTimeBase;

				/** Long codec name. */
				private String codecLongName;

				/** Codec tag string. */
				private String codecTagString;

				/** Codec tag. */
				private String codecTag;

				/** Sample format. */
				private String sampleFmt;

				/** Sample rate. */
				private String samplerate;

				/** Number of channels. */
				private String channels;

				/** Channel layout. */
				private String channelLayout;

				/** Time base. */
				private String timebase;

				/** Start time. */
				private String startTime;

				/** Duration. */
				private String duration;

				/** Bitrate. */
				private String bitrate;

				/** Number of frames. */
				private String numFrames;

				/** Language. */
				private String lang;

			}

			/** Subtitle stream metadata. */
			@JsonInclude(JsonInclude.Include.NON_NULL)
			@Data
			public static class SubtitleStream {

				/** Stream index. */
				private String index;

				/** Language. */
				private String lang;

			}
		}

		/** Container-level metadata. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Format {

			/** Number of streams. */
			private String numStreams;

			/** Number of programs. */
			private String numPrograms;

			/** Format name. */
			private String formatName;

			/** Long format name. */
			private String formatLongName;

			/** Start time. */
			private String startTime;

			/** Duration. */
			private String duration;

			/** Size. */
			private String size;

			/** Bitrate. */
			private String bitrate;

		}
	}

	/** Clip (segment) parameters. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Clip {

		/** Clip time span. */
		@JsonProperty("TimeSpan")
		private TimeSpan timeSpan;

		/**
		 * Whether to clip the first part.
		 * {@code false} (default): clip after merging; {@code true}: clip the first part before merging.
		 */
		@JsonProperty("ConfigToClipFirstPart")
		private boolean configToClipFirstPart;

		/** Time span parameters. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class TimeSpan {

			/**
			 * Start time of the clip.
			 * <ul>
			 *  <li>Format {@code hh:mm:ss[.SSS]}, range [00:00:00.000, 23:59:59.999]</li>
			 *  <li>Format {@code sssss[.SSS]}, range [0.000, 86399.999]</li>
			 *  <li>Example: {@code 01:59:59.999} or {@code 32000.23}</li>
			 * </ul>
			 */
			@JsonProperty("Seek")
			private String seek;

			/**
			 * Duration of the clip.
			 * <ul>
			 *  <li>Format {@code hh:mm:ss[.SSS]}, range [00:00:00.000, 23:59:59.999]</li>
			 *  <li>Format {@code sssss[.SSS]}, range [0.000, 86399.999]</li>
			 *  <li>Example: {@code 01:00:59.999} or {@code 32000.23}</li>
			 * </ul>
			 */
			@JsonProperty("Duration")
			private String duration;

			/**
			 * Tail-trim duration. When set, {@link #duration} is ignored.
			 * <ul>
			 *  <li>Format {@code hh:mm:ss[.SSS]}, range [00:00:00.000, 23:59:59.999]</li>
			 *  <li>Format {@code sssss[.SSS]}, range [0.000, 86399.999]</li>
			 *  <li>Example: {@code 01:00:59.999} or {@code 32000.23}</li>
			 * </ul>
			 */
			@JsonProperty("End")
			private String end;

		}
	}

	/** Super-resolution parameters. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class SuperReso {

		/** Whether to use half-sample mode. */
		private String isHalfSample;

	}

	/** Subtitle configuration. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class SubtitleConfig {

		/** Embedded subtitle list. */
		private List<Subtitle> subtitleList;

		/** External subtitle list. */
		private List<ExtSubtitle> extSubtitleList;

		/** Embedded subtitle selector. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Subtitle {

			/** Subtitle stream selector. */
			private String map;

			/** @return the subtitle stream selector. */
			public String getMap() {
				return this.map;
			}

			/** @param map the subtitle stream selector to set. */
			public void setMap(String map) {
				this.map = map;
			}
		}

		/** External subtitle descriptor. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class ExtSubtitle {

			/** Subtitle font name. */
			private String fontName;

			/** Character encoding of the subtitle file. */
			private String charEnc;

			/** External subtitle input file. */
			private Input1 input1;

			/** External subtitle input file descriptor. */
			@JsonInclude(JsonInclude.Include.NON_NULL)
			@Data
			public static class Input1 {

				/** OSS bucket of the subtitle file. */
				private String bucket;

				/** OSS location (region id) of the subtitle file. */
				private String location;

				/** Subtitle file name (OSS object key). */
				private String object;

			}
		}
	}

	/** Transcode flow configuration. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class TransConfig {

		/** Transcode mode (one-pass / two-pass). */
		private String transMode;

		/** Whether to check the resolution. */
		private String isCheckReso;

		/** Whether to fail the job on resolution check failure. */
		private String isCheckResoFail;

		/** Whether to check the video bitrate. */
		private String isCheckVideoBitrate;

		/** Whether to check the audio bitrate. */
		private String isCheckAudioBitrate;

		/** Display aspect ratio adjustment method. */
		private String adjDarMethod;

		/** Whether to fail the job on video bitrate check failure. */
		private String isCheckVideoBitrateFail;

		/** Whether to fail the job on audio bitrate check failure. */
		private String isCheckAudioBitrateFail;

	}

	/** Muxing configuration. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class MuxConfig {

		/** Segmentation configuration (for HLS/DASH). */
		private Segment segment;

		/** GIF muxing configuration. */
		private Gif gif;

		/** WebP muxing configuration. */
		private Webp webp;

		/** Segmentation parameters. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Segment {

			/** Segment duration, in seconds. */
			private String duration;

		}

		/** GIF muxing parameters. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Gif {

			/** Number of loops. */
			private String loop;

			/** Final frame delay, in milliseconds. */
			private String finalDelay;

			/** Whether to use a custom palette. */
			private String isCustomPalette;

			/** Dither mode. */
			private String ditherMode;

		}

		/** WebP muxing parameters. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Webp {

			/** Number of loops. */
			private String loop;

		}
	}

	/** Audio stream configuration (overrides the matching template parameter when set). */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Audio {

		/** Audio codec. */
		private String codec;

		/** Audio codec profile. */
		private String profile;

		/** Audio sample rate, in Hz. */
		private String samplerate;

		/** Audio bitrate, in Kbps. */
		private String bitrate;

		/** Number of audio channels. */
		private String channels;

		/** Quality scale. */
		private String qscale;

		/** Volume control configuration. */
		private Volume volume;

		/** Audio volume control parameters. */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Data
		public static class Volume {

			/** Target volume level. */
			private String level;

			/** Volume adjustment method. */
			private String method;

		}
	}

	/** Video stream configuration (overrides the matching template parameter when set). */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Video {

		/** Video codec. Supports H.264, H.265, GIF, WEBP. Defaults to H.264. */
		@JsonProperty("Codec")
		private String codec;

		/** Video codec profile. */
		private String profile;


		/** Video bitrate, in Kbps. */
		private String bitrate;

		/** Constant rate factor (CRF). */
		private String crf;

		/** Output width, in pixels. */
		private String width;

		/** Output height, in pixels. */
		private String height;

		/** Frame rate, in fps. */
		private String fps;

		/** Group of pictures (GOP) size. */
		private String gop;

		/** Encoder preset (speed/quality trade-off). */
		private String preset;

		/** Scan mode (interlaced/progressive). */
		private String scanMode;

		/** Rate control buffer size. */
		private String bufsize;

		/** Maximum bitrate. */
		private String maxrate;

		/** Pixel format. */
		private String pixFmt;

		/** De-grain strength. */
		private String degrain;

		/** Quality scale. */
		private String qscale;

		/** Crop region. */
		private String crop;

		/** Padding region. */
		private String pad;

		/** Maximum allowed frame rate. */
		private String maxFps;

		/** Resolution priority. */
		private String resoPriority;

		/** Bitrate bounds. */
		private BitrateBnd bitrateBnd;

		/** Bitrate bounds applied during rate control. */
		@JsonInclude( JsonInclude.Include.NON_NULL)
		@Data
		public static class BitrateBnd {

			/** Maximum bitrate. */
			private String max;

			/** Minimum bitrate. */
			private String min;

		}
	}

	/** Container configuration (overrides the matching template parameter when set). */
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public static class Container {

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

	/** Data encryption parameters (m3u8 output only). */
	@JsonInclude( JsonInclude.Include.NON_NULL)
	@Data
	public static class Encryption {

		/** Encryption type, e.g. {@code hls-aes-128}. */
		@JsonProperty("Type")
		private String type;

		/**
		 * Encryption key. Must be encoded according to {@link #keyType}.
		 * For example, for key {@code encryptionkey128} use {@code Base64("encryptionkey128")}
		 * or {@code KMS(Base64("encryptionkey128"))}.
		 */
		@JsonProperty("Key")
		private String key;

		/** BASE64-encoded URL used to retrieve the key. */
		@JsonProperty("KeyUri")
		private String keyUri;

		/**
		 * How the key is encrypted before being sent to MPS: {@code Base64} or {@code KMS}.
		 */
		@JsonProperty("KeyType")
		private String keyType;

	}
}
