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

import java.io.IOException;
import java.net.URLEncoder;

import com.aliyun.mps.spring.boot.model.SnapshotConfig;
import com.aliyun.mps.spring.boot.model.transform.Input;
import com.aliyun.mps.spring.boot.model.transform.OutputFile;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.mts.model.v20140618.SubmitSnapshotJobRequest;
import com.aliyuncs.mts.model.v20140618.SubmitSnapshotJobResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 媒体处理
 * https://help.aliyun.com/document_detail/29196.html?spm=a2c4g.11186623.6.542.59ae7b70EKNHjP
 */
@Slf4j
public class AliyunMpsSnapshotOperations extends AliyunMpsOperations {

	public AliyunMpsSnapshotOperations(AliyunMpsTemplate mpsTemplate) {
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
	 * @param config 输出配置
	 * @return 操作结果
	 * @throws IOException 如果错则抛出异常
	 */
	public SubmitSnapshotJobResponse submitJob(
			String pipelineId, 
			String templateId,
			String inputLocation,
			String inputBucket,
			String inputObject,
			String outputLocation,
			String outputBucket,
			String outputObject,
			String outputFormat,
			SnapshotConfig config) throws IOException {
		
        // 1、转码输入参数
 		Input input = new Input();
 		input.setBucket(inputBucket);
 		input.setLocation(inputLocation);
 		input.setObject(URLEncoder.encode(inputObject, "utf-8"));
     	
 		SnapshotConfig snapshotConfig = new SnapshotConfig();
        
 		OutputFile outputFile =  new OutputFile();
 		outputFile.setBucket(outputLocation);
 		outputFile.setLocation(outputBucket);
 		outputFile.setObject(URLEncoder.encode(outputObject, "utf-8"));
 		
 		snapshotConfig.setOutputFile(outputFile);
 		
        // SnapshotConfig->Time
        //snapshotConfig.put("Time", "2");
        // SnapshotConfig->Interval/Num
        //snapshotConfig.put("Interval", "2");
        //snapshotConfig.put("Num", "3");
        // SnapshotConfig->Width/Height
        //snapshotConfig.put("Height", "360");

        // request
        SubmitSnapshotJobRequest request = new SubmitSnapshotJobRequest();
        
        request.setInput(getMpsTemplate().writeValueAsString(input));
        request.setSnapshotConfig(getMpsTemplate().writeValueAsString(snapshotConfig));
		request.setPipelineId(pipelineId);
		
        return this.submitJob(request);
		
	}
	
	public SubmitSnapshotJobResponse submitJob(SubmitSnapshotJobRequest request) {
		// call api
        SubmitSnapshotJobResponse response = null;
        try {
            response = getMpsTemplate().getAcsResponse(request);
            log.debug("RequestId is:{}", response.getRequestId());
            log.debug("JobId is:{}", response.getSnapshotJob().getId());
        } catch (ServerException e) {
	        e.printStackTrace();
	        log.error("服务端端异常， ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
	    } catch (ClientException e) {
	        e.printStackTrace();
	        log.error("客户端异常， ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
	    }
		return response;
	}
	
}
