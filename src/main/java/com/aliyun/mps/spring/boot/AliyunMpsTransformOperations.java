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
import java.util.Arrays;

import com.aliyun.mps.spring.boot.model.TransformRequest;
import com.aliyun.mps.spring.boot.model.transform.Audio;
import com.aliyun.mps.spring.boot.model.transform.Container;
import com.aliyun.mps.spring.boot.model.transform.Input;
import com.aliyun.mps.spring.boot.model.transform.OutputSimple;
import com.aliyun.mps.spring.boot.model.transform.Video;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.mts.model.v20140618.CancelJobRequest;
import com.aliyuncs.mts.model.v20140618.CancelJobResponse;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.mts.model.v20140618.SubmitJobsResponse;
import com.aliyuncs.mts.model.v20140618.SubmitJobsResponse.JobResult;

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
	 * @param pipelineId 管道ID。管道的定义详见术语表；若需要异步通知，须保证此管道绑定了可用的消息主题。
	 * @param templateId 转码模板ID。支持自定义转码模板与系统预置模板。
	 * @param location 输入/输出 OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param bucket 输入/输出文件所在OSS Bucket， 例：example-bucket
	 * @param inputObject 输入的文件名（OSS Object）
	 * @param outputObject 输出的文件名（OSS Object）
	 * @param outputFormat 输出的文件名格式（ 默认值：mp4），
	 * <ul>
	 *  <li>视频转码支持flv、mp4、HLS（m3u8+ts）、MPEG-DASH（MPD+fMP4）</li>
	 *  <li>音频转码支持mp3、mp4、ogg、flac、m4a</li>
	 *  <li>图片支持gif、WEBP</li>
	 *  <li>容器格式为gif时，Video Codec设置只能设置为GIF</li>
	 *  <li>容器格式为webp时，Video Codec设置只能设置为WEBP</li>
	 *  <li>容器格式为flv时，Video Codec不能设置为H.265。</li>
	 * </ul>
	 * @param audio 输出的Audio配置
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public SubmitJobsResponse submitJob(
			String pipelineId, 
			String templateId,
			String location,
			String bucket,
			String inputObject,
			String outputObject,
			String outputFormat,
			Video video) throws UnsupportedEncodingException {
		return this.submitJob(pipelineId, templateId, location, bucket, inputObject, location, bucket, outputObject, outputFormat, video);
	}
	
	/**
	 * 
	 * 1、提交转码作业
	 * API：
	 * https://help.aliyun.com/document_detail/29226.html?spm=a2c4g.11186623.6.659.2dab3dbfqBWDro
	 * https://help.aliyun.com/document_detail/67662.html?spm=a2c4g.11186623.6.757.6be33f00ikEQcR
	 * @param pipelineId 管道ID。管道的定义详见术语表；若需要异步通知，须保证此管道绑定了可用的消息主题。
	 * @param templateId 转码模板ID。支持自定义转码模板与系统预置模板。
	 * @param inputLocation 输入OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param inputBucket 输入文件所在OSS Bucket， 例：example-bucket
	 * @param inputObject 输入的文件名（OSS Object）
	 * @param outputLocation 输出OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param outputBucket 输出的文件名（OSS Object）
	 * @param outputObject 输出的文件名（OSS Object）
	 * @param outputFormat 输出的文件名格式（ 默认值：mp4），
	 * <ul>
	 *  <li>视频转码支持flv、mp4、HLS（m3u8+ts）、MPEG-DASH（MPD+fMP4）</li>
	 *  <li>音频转码支持mp3、mp4、ogg、flac、m4a</li>
	 *  <li>图片支持gif、WEBP</li>
	 *  <li>容器格式为gif时，Video Codec设置只能设置为GIF</li>
	 *  <li>容器格式为webp时，Video Codec设置只能设置为WEBP</li>
	 *  <li>容器格式为flv时，Video Codec不能设置为H.265。</li>
	 * </ul>
	 * @param audio 输出的Audio配置
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public SubmitJobsResponse submitJob(
			String pipelineId, 
			String templateId,
			String inputLocation,
			String inputBucket,
			String inputObject,
			String outputLocation,
			String outputBucket,
			String outputObject,
			String outputFormat,
			Video video) throws UnsupportedEncodingException {
		
		// 1、转码输入参数
		Input input = new Input();
		input.setBucket(inputBucket);
		input.setLocation(inputLocation);
		input.setObject(URLEncoder.encode(inputObject, "utf-8"));
		
		OutputSimple output = new OutputSimple();
		output.setOutputObject(URLEncoder.encode(outputObject, "utf-8"));
		output.setTemplateId(templateId);
		
		Container container = new Container();
		container.setFormat(outputFormat);
		output.setContainer(container);
		output.setVideo(video);
		
		// 2、创建request，并设置参数
		SubmitJobsRequest request = new SubmitJobsRequest();
		
		request.setInput(getMpsTemplate().writeValueAsString(input));
		request.setOutputs(getMpsTemplate().writeValueAsString(Arrays.asList(output)));
		
		request.setPipelineId(pipelineId);
		request.setOutputBucket(outputBucket);
		request.setOutputLocation(outputLocation);
		
		// 3、执行逻辑
		return this.submitJob(request);
	}
	
	/**
	 * 
	 * 1、提交转码作业
	 * API：
	 * https://help.aliyun.com/document_detail/29226.html?spm=a2c4g.11186623.6.659.2dab3dbfqBWDro
	 * https://help.aliyun.com/document_detail/67662.html?spm=a2c4g.11186623.6.757.6be33f00ikEQcR
	 * @param pipelineId 管道ID。管道的定义详见术语表；若需要异步通知，须保证此管道绑定了可用的消息主题。
	 * @param templateId 转码模板ID。支持自定义转码模板与系统预置模板。
	 * @param location 输入/输出 OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param bucket 输入/输出文件所在OSS Bucket， 例：example-bucket
	 * @param inputObject 输入的文件名（OSS Object）
	 * @param outputObject 输出的文件名（OSS Object）
	 * @param outputFormat 输出的文件名格式（ 默认值：mp4），
	 * <ul>
	 *  <li>视频转码支持flv、mp4、HLS（m3u8+ts）、MPEG-DASH（MPD+fMP4）</li>
	 *  <li>音频转码支持mp3、mp4、ogg、flac、m4a</li>
	 *  <li>图片支持gif、WEBP</li>
	 *  <li>容器格式为gif时，Video Codec设置只能设置为GIF</li>
	 *  <li>容器格式为webp时，Video Codec设置只能设置为WEBP</li>
	 *  <li>容器格式为flv时，Video Codec不能设置为H.265。</li>
	 * </ul>
	 * @param audio 输出的Audio配置
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public SubmitJobsResponse submitJob(
			String pipelineId, 
			String templateId,
			String location,
			String bucket,
			String inputObject,
			String outputObject,
			String outputFormat,
			Audio audio) throws UnsupportedEncodingException {
		return this.submitJob(pipelineId, templateId, location, bucket, inputObject, location, bucket, outputObject, outputFormat, audio);
	}
	
	/**
	 * 
	 * 1、提交转码作业
	 * API：
	 * https://help.aliyun.com/document_detail/29226.html?spm=a2c4g.11186623.6.659.2dab3dbfqBWDro
	 * https://help.aliyun.com/document_detail/67662.html?spm=a2c4g.11186623.6.757.6be33f00ikEQcR
	 * @param pipelineId 管道ID。管道的定义详见术语表；若需要异步通知，须保证此管道绑定了可用的消息主题。
	 * @param templateId 转码模板ID。支持自定义转码模板与系统预置模板。
	 * @param inputLocation 输入OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param inputBucket 输入文件所在OSS Bucket， 例：example-bucket
	 * @param inputObject 输入的文件名（OSS Object）
	 * @param outputLocation 输出OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param outputBucket 输出的文件名（OSS Object）
	 * @param outputObject 输出的文件名（OSS Object）
	 * @param outputFormat 输出的文件名格式（ 默认值：mp4），
	 * <ul>
	 *  <li>视频转码支持flv、mp4、HLS（m3u8+ts）、MPEG-DASH（MPD+fMP4）</li>
	 *  <li>音频转码支持mp3、mp4、ogg、flac、m4a</li>
	 *  <li>图片支持gif、WEBP</li>
	 *  <li>容器格式为gif时，Video Codec设置只能设置为GIF</li>
	 *  <li>容器格式为webp时，Video Codec设置只能设置为WEBP</li>
	 *  <li>容器格式为flv时，Video Codec不能设置为H.265。</li>
	 * </ul>
	 * @param audio 输出的Audio配置
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public SubmitJobsResponse submitJob(
			String pipelineId, 
			String templateId,
			String inputLocation,
			String inputBucket,
			String inputObject,
			String outputLocation,
			String outputBucket,
			String outputObject,
			String outputFormat,
			Audio audio) throws UnsupportedEncodingException {
		
		// 1、转码输入参数
		Input input = new Input();
		input.setBucket(inputBucket);
		input.setLocation(inputLocation);
		input.setObject(URLEncoder.encode(inputObject, "utf-8"));
		
		OutputSimple output = new OutputSimple();
		output.setOutputObject(URLEncoder.encode(outputObject, "utf-8"));
		output.setTemplateId(templateId);
		
		Container container = new Container();
		container.setFormat(outputFormat);
		output.setContainer(container);
		output.setAudio(audio);
		
		// 2、创建request，并设置参数
		SubmitJobsRequest request = new SubmitJobsRequest();
		
		request.setInput(getMpsTemplate().writeValueAsString(input));
		request.setOutputs(getMpsTemplate().writeValueAsString(Arrays.asList(output)));
		
		request.setPipelineId(pipelineId);
		request.setOutputBucket(outputBucket);
		request.setOutputLocation(outputLocation);
		
		// 3、执行逻辑
		return this.submitJob(request);
	}
	
	public SubmitJobsResponse submitJob(
			String templateId,
			String inputLocation,
			String inputBucket,
			String inputObject,
			String outputObject) throws UnsupportedEncodingException {
		
		// 1、转码输入参数
		Input input = new Input();
		input.setBucket(inputBucket);
		input.setLocation(inputLocation);
		input.setObject(URLEncoder.encode(inputObject, "utf-8"));
		
		OutputSimple output = new OutputSimple();
		output.setOutputObject(URLEncoder.encode(outputObject, "utf-8"));
		output.setTemplateId(templateId);
		
		// 2、创建request，并设置参数
		SubmitJobsRequest request = new SubmitJobsRequest();
		
		request.setInput(getMpsTemplate().writeValueAsString(input));
		request.setOutputs(getMpsTemplate().writeValueAsString(Arrays.asList(output)));
		
		request.setOutputBucket(inputBucket);
		request.setOutputLocation(inputLocation);
		
		// 3、执行逻辑
		return this.submitJob(request);
	}
	
	public SubmitJobsResponse submitJob(
			String templateId,
			String inputLocation,
			String inputBucket,
			String inputObject,
			String outputLocation,
			String outputBucket,
			String outputObject) throws UnsupportedEncodingException {
		
		// 1、转码输入参数
		Input input = new Input();
		input.setBucket(inputBucket);
		input.setLocation(inputLocation);
		input.setObject(URLEncoder.encode(inputObject, "utf-8"));
		
		OutputSimple output = new OutputSimple();
		output.setOutputObject(URLEncoder.encode(outputObject, "utf-8"));
		output.setTemplateId(templateId);
		
		// 2、创建request，并设置参数
		SubmitJobsRequest request = new SubmitJobsRequest();
		
		request.setInput(getMpsTemplate().writeValueAsString(input));
		request.setOutputs(getMpsTemplate().writeValueAsString(Arrays.asList(output)));
		
		request.setOutputBucket(outputBucket);
		request.setOutputLocation(outputLocation);
		
		// 3、执行逻辑
		return this.submitJob(request);
	}
	
	
	/**
	 * 2、提交转码作业
	 * API：
	 * https://help.aliyun.com/document_detail/29226.html?spm=a2c4g.11186623.6.659.2dab3dbfqBWDro
	 * https://help.aliyun.com/document_detail/67662.html?spm=a2c4g.11186623.6.757.6be33f00ikEQcR
	 * @param pipelineId 管道ID。管道的定义详见术语表；若需要异步通知，须保证此管道绑定了可用的消息主题。
	 * @param outputLocation 输出OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param outputBucket 输出的文件名（OSS Object）
	 * @param transRequest 转换情况去年是
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public SubmitJobsResponse submitJob(
			String pipelineId, 
			TransformRequest transRequest) throws UnsupportedEncodingException {
		// 1、创建request，并设置参数
		SubmitJobsRequest request = new SubmitJobsRequest();
		// 2、转码参数设置
		request.setInput(getMpsTemplate().writeValueAsString(transRequest.getInput()));
		request.setOutputs(getMpsTemplate().writeValueAsString(transRequest.getOutputs()));
		request.setPipelineId(pipelineId);
		// 3、执行逻辑
		return this.submitJob(request);
	}
	
	/**
	 * 2、提交转码作业
	 * API：
	 * https://help.aliyun.com/document_detail/29226.html?spm=a2c4g.11186623.6.659.2dab3dbfqBWDro
	 * https://help.aliyun.com/document_detail/67662.html?spm=a2c4g.11186623.6.757.6be33f00ikEQcR
	 * @param pipelineId 管道ID。管道的定义详见术语表；若需要异步通知，须保证此管道绑定了可用的消息主题。
	 * @param outputLocation 输出OSS Bucket 所在数据中心（OSS Location），例：oss-cn-hangzhou
	 * @param outputBucket 输出的文件名（OSS Object）
	 * @param transRequest 转换情况去年是
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public SubmitJobsResponse submitJob(
			String pipelineId, 
			String outputLocation,
			String outputBucket,
			TransformRequest transRequest) throws UnsupportedEncodingException {
		// 1、创建request，并设置参数
		SubmitJobsRequest request = new SubmitJobsRequest();
		// 2、转码参数设置
		request.setInput(getMpsTemplate().writeValueAsString(transRequest.getInput()));
		request.setOutputLocation(outputLocation);
		request.setOutputBucket(outputBucket);
		request.setOutputs(getMpsTemplate().writeValueAsString(transRequest.getOutputs()));
		request.setPipelineId(pipelineId);
		// 3、执行逻辑
		return this.submitJob(request);
	}
	
	public SubmitJobsResponse submitJob(SubmitJobsRequest request) {
		// 发起请求并处理应答或异常
        SubmitJobsResponse response;
        try {
            response = getMpsTemplate().getAcsResponse(request);
            log.debug("RequestId is:{}", response.getRequestId());
            for (JobResult jobResult : response.getJobResultList()) {
            	 if (jobResult.getSuccess()) {
                 	log.debug("JobId is:" + jobResult.getJob().getJobId());
                 } else {
                 	log.error("SubmitJobs Failed code: {}, message:{}" , jobResult.getCode(), jobResult.getMessage());
                 }
			}
        } catch (ServerException e) {
	        e.printStackTrace();
	        log.error("服务端端异常， ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
	    } catch (ClientException e) {
	        e.printStackTrace();
	        log.error("客户端异常， ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
	    }
		return null;
	}
	
	/**
	 * 2、取消转码作业
	 * https://help.aliyun.com/document_detail/29227.html?spm=a2c4g.11186623.6.660.3a887b70IFShlS#h2-url-4
	 * @param jobId 作业ID
	 * @return
	 */
	public CancelJobResponse cancelJob( String jobId) {
		CancelJobRequest request = new CancelJobRequest();
		request.setJobId(jobId);
		// 发起请求并处理应答或异常
		CancelJobResponse response;
        try {
            response = getMpsTemplate().getAcsResponse(request);
            log.debug("RequestId is:{}", response.getRequestId());
            log.debug("JobId is:{}", response.getJobId());
        } catch (ServerException e) {
	        e.printStackTrace();
	        log.error("服务端端异常， ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
	    } catch (ClientException e) {
	        e.printStackTrace();
	        log.error("客户端异常， ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
	    }
		return null;
	}
	
}
