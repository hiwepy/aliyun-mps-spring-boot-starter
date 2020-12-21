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
package com.aliyun.mps.spring.boot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.mps.spring.boot.model.SnapshotRequest;
import com.aliyun.mps.spring.boot.model.TransformRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.mts.model.v20140618.SubmitJobsResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 媒体处理
 * https://help.aliyun.com/document_detail/29196.html?spm=a2c4g.11186623.6.542.59ae7b70EKNHjP
 */
@Slf4j
public class AliyunMpsTransformOperations extends AliyunMpsOperations {

	public AliyunMpsTransformOperations(AliyunMpsTemplate mpsTemplate) {
		super(mpsTemplate);
	}
	
	/**
	 * 
	 * 1、提交转码作业
	 * API：
	 * https://help.aliyun.com/document_detail/29226.html?spm=a2c4g.11186623.6.659.2dab3dbfqBWDro
	 * https://help.aliyun.com/document_detail/67662.html?spm=a2c4g.11186623.6.757.6be33f00ikEQcR
	 * @param pipelineId
	 * @param templateId
	 * @param inputLocation 输入OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param inputBucket 输入文件所在OSS Bucket， 例：example-bucket
	 * @param inputObject 输入的文件名（OSS Object）
	 * @param outputLocation 输出OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param outputBucket 输出的文件名（OSS Object）
	 * @param outputFormat 输出的文件名格式（ 默认值：mp4），
	 * <ul>
	 *  <li>视频转码支持flv、mp4、HLS（m3u8+ts）、MPEG-DASH（MPD+fMP4）</li>
	 *  <li>音频转码支持mp3、mp4、ogg、flac、m4a</li>
	 *  <li>图片支持gif、WEBP</li>
	 *  <li>容器格式为gif时，Video Codec设置只能设置为GIF</li>
	 *  <li>容器格式为webp时，Video Codec设置只能设置为WEBP</li>
	 *  <li>容器格式为flv时，Video Codec不能设置为H.265。</li>
	 * </ul>
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public SubmitJobsResponse submitJob(String pipelineId, 
			String templateId,
			String inputLocation,
			String inputBucket,
			String inputObject,
			String outputLocation,
			String outputBucket,
			String outputFormat,
			String outputObject) throws UnsupportedEncodingException {
		
		// 1、创建request，并设置参数
		SubmitJobsRequest request = new SubmitJobsRequest();
		
		// 2、转码输入参数
		JSONObject input = new JSONObject();
		input.put("Location", inputLocation);
		input.put("Bucket", inputBucket);
		input.put("Object", URLEncoder.encode(inputObject, "utf-8"));
		request.setInput(input.toJSONString());
		
		JSONObject output = new JSONObject();
		
		String outputOSSObject = URLEncoder.encode(outputObject, "utf-8");
		output.put("OutputObject", outputOSSObject);
		
		// Ouput->TemplateId:转码模板ID。支持自定义转码模板与系统预置模板。
		output.put("TemplateId", templateId);
		// Ouput->Container: 如设置则覆盖指定转码模版中的对应参数，参见 Container详情
		JSONObject container = new JSONObject();
		container.put("Format", outputFormat);
		output.put("Container", container.toJSONString());
		
		// Ouput->Audio
		JSONObject audio = new JSONObject();
		audio.put("Codec", "AAC");
		audio.put("Bitrate", "128");
		audio.put("Channels", "2");
		audio.put("Samplerate", "44100");
		output.put("Audio", audio.toJSONString());
		
		request.setPipelineId(pipelineId);
		request.setOutputBucket(outputBucket);
		request.setOutputLocation(outputLocation);
		
		
		// 发起请求并处理应答或异常
        SubmitJobsResponse response;
        try {
            response = getMpsTemplate().getAcsResponse(request);
            System.out.println("RequestId is:"+response.getRequestId());
            if (response.getJobResultList().get(0).getSuccess()) {
                System.out.println("JobId is:" + response.getJobResultList().get(0).getJob().getJobId());
            } else {
                System.out.println("SubmitJobs Failed code:" + response.getJobResultList().get(0).getCode() +
                                   " message:" + response.getJobResultList().get(0).getMessage());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
		return null;
        
	}
	
	/**
	 * 
	 * TODO
	 * @param pipelineId
	 * @param ossLocation 输入OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param ossBucket 输入文件所在OSS Bucket， 例：example-bucket
	 * @param ossInputObject
	 * @param ossOutputObject 输出的文件名（OSS Object）
	 * @param ossOutputFormat 输出的文件名格式，视频转码支持flv、mp4、HLS（m3u8+ts）、MPEG-DASH（MPD+fMP4）（默认值：mp4 ）
	 * @param templateId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public SnapshotRequest submitJob(TransformRequest transRequest) throws UnsupportedEncodingException {

		// 1、创建request，并设置参数
		SubmitJobsRequest request = new SubmitJobsRequest();
		// 2、转码参数
		request.setInput(getMpsTemplate().writeValueAsString(transRequest.getInput()));
		
		
		
		request.setOutputs(outputs);
		
		
		String outputOSSObject = URLEncoder.encode(ossOutputObject, "utf-8");
		JSONObject output = new JSONObject();
		output.put("OutputObject", outputOSSObject);
		// Ouput->Container
		JSONObject container = new JSONObject();
		container.put("Format", "mp4");
		output.put("Container", container.toJSONString());
		// 编解码格式
		JSONObject video = new JSONObject();
		video.put("Codec", "H.264");  // 支持H.264、H.265、GIF、WEBP。默认值：H.264
		video.put("Bitrate", "1500"); // 视频输出文件的码率,值范围：[10,50000],单位：Kbps
		
		video.put("Width", "1280");
		video.put("Fps", "25");
		output.put("Video", video.toJSONString());
		// Ouput->TemplateId
		output.put("TemplateId", templateId);
		
		request.setPipelineId(pipelineId);
		
		 // 发起请求并处理应答或异常
        SubmitJobsResponse response;
        try {
            response = getMpsTemplate().getAcsResponse(request);
            System.out.println("RequestId is:"+response.getRequestId());
            if (response.getJobResultList().get(0).getSuccess()) {
                System.out.println("JobId is:" + response.getJobResultList().get(0).getJob().getJobId());
            } else {
                System.out.println("SubmitJobs Failed code:" + response.getJobResultList().get(0).getCode() +
                                   " message:" + response.getJobResultList().get(0).getMessage());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
		return null;
	}
	
}
