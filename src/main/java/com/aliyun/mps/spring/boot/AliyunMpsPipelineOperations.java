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

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.mts.model.v20140618.SearchPipelineRequest;
import com.aliyuncs.mts.model.v20140618.SearchPipelineResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 媒体处理
 * https://help.aliyun.com/document_detail/29196.html?spm=a2c4g.11186623.6.542.59ae7b70EKNHjP
 */
@Slf4j
public class AliyunMpsPipelineOperations extends AliyunMpsOperations {

	public AliyunMpsPipelineOperations(AliyunMpsTemplate mpsTemplate) {
		super(mpsTemplate);
	}
	
	/**
	 * 1、提交转码作业
	 * API：
	 * https://help.aliyun.com/document_detail/29226.html?spm=a2c4g.11186623.6.659.2dab3dbfqBWDro
	 * https://help.aliyun.com/document_detail/67662.html?spm=a2c4g.11186623.6.757.6be33f00ikEQcR
	 * @param pipelineId 业务ID
	 * @return 操作结果
	 */
	public SearchPipelineResponse search(String pipelineId) {
		// 创建API请求并设置参数
	    SearchPipelineRequest request = new SearchPipelineRequest();
	    // 发起请求并处理应答或异常
	    SearchPipelineResponse response = null;
	    try {
	        response = getMpsTemplate().getAcsResponse(request);
	        System.out.println("PipelineName is:" + response.getPipelineList().get(0).getName());
	        System.out.println("PipelineId is:" + response.getPipelineList().get(0).getId());
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
