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
 * Pipeline-related operations for Alibaba Cloud Media Processing Service.
 * <p>See <a href="https://help.aliyun.com/document_detail/29196.html">the pipeline documentation</a>.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Slf4j
public class AliyunMpsPipelineOperations extends AliyunMpsOperations {

	/**
	 * Creates pipeline operations bound to the given template.
	 * @param mpsTemplate the template used to execute MPS requests
	 */
	public AliyunMpsPipelineOperations(AliyunMpsTemplate mpsTemplate) {
		super(mpsTemplate);
	}

	/**
	 * Searches for pipelines.
	 * <p>API references:
	 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
	 * <a href="https://help.aliyun.com/document_detail/67662.html">SearchPipeline</a>.</p>
	 * @param pipelineId the target pipeline id
	 * @return the search response, or {@code null} when the request fails
	 */
	public SearchPipelineResponse search(String pipelineId) {
		// Build the API request.
	    SearchPipelineRequest request = new SearchPipelineRequest();
	    // Send the request and handle the response or exceptions.
	    SearchPipelineResponse response = null;
	    try {
	        response = getMpsTemplate().getAcsResponse(request);
	        System.out.println("PipelineName is:" + response.getPipelineList().get(0).getName());
	        System.out.println("PipelineId is:" + response.getPipelineList().get(0).getId());
	    } catch (ServerException e) {
	        e.printStackTrace();
	        log.error("Server exception, ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
	    } catch (ClientException e) {
	        e.printStackTrace();
	        log.error("Client exception, ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
	    }
	    return response;
	}
	 
	
	
}
