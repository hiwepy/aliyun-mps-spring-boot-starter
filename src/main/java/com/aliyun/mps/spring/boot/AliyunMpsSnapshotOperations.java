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
 * Snapshot operations for Alibaba Cloud Media Processing Service.
 * <p>See <a href="https://help.aliyun.com/document_detail/29196.html">the snapshot documentation</a>.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Slf4j
public class AliyunMpsSnapshotOperations extends AliyunMpsOperations {

	/**
	 * Creates snapshot operations bound to the given template.
	 * @param mpsTemplate the template used to execute MPS requests
	 */
	public AliyunMpsSnapshotOperations(AliyunMpsTemplate mpsTemplate) {
		super(mpsTemplate);
	}

	/**
	 * Submits a snapshot job.
	 * <p>API references:
	 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
	 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitSnapshotJob</a>.</p>
	 * @param pipelineId the pipeline id. For asynchronous notification the pipeline must be bound to a message topic.
	 * @param templateId the transcode template id. Both custom and system-preset templates are supported.
	 * @param inputLocation the OSS location of the input bucket, e.g. {@code oss-cn-hangzhou}
	 * @param inputBucket the OSS bucket that holds the input object, e.g. {@code example-bucket}
	 * @param inputObject the input file name (OSS object key)
	 * @param outputLocation the OSS location of the output bucket, e.g. {@code oss-cn-hangzhou}
	 * @param outputBucket the OSS bucket that holds the output object
	 * @param outputObject the output file name (OSS object key)
	 * @param outputFormat the output container format (defaults to {@code mp4}):
	 * <ul>
	 *  <li>Video transcode supports flv, mp4, HLS (m3u8+ts), MPEG-DASH (MPD+fMP4)</li>
	 *  <li>Audio transcode supports mp3, mp4, ogg, flac, m4a</li>
	 *  <li>Image supports gif, WEBP</li>
	 *  <li>When the container is gif the video codec must be GIF</li>
	 *  <li>When the container is webp the video codec must be WEBP</li>
	 *  <li>When the container is flv the video codec cannot be H.265</li>
	 * </ul>
	 * @param config the snapshot output configuration
	 * @return the submit response, or {@code null} when the request fails
	 * @throws IOException if the object key cannot be URL-encoded
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
		
        // 1. Build the transcode input parameters.
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

        // Build the request.
        SubmitSnapshotJobRequest request = new SubmitSnapshotJobRequest();
        
        request.setInput(getMpsTemplate().writeValueAsString(input));
        request.setSnapshotConfig(getMpsTemplate().writeValueAsString(snapshotConfig));
		request.setPipelineId(pipelineId);
		
        return this.submitJob(request);
		
	}
	
	/**
	 * Submits a snapshot job from a pre-built request.
	 * @param request the snapshot job request
	 * @return the submit response, or {@code null} when the request fails
	 */
	public SubmitSnapshotJobResponse submitJob(SubmitSnapshotJobRequest request) {
		// Call the API.
        SubmitSnapshotJobResponse response = null;
        try {
            response = getMpsTemplate().getAcsResponse(request);
            log.debug("RequestId is:{}", response.getRequestId());
            log.debug("JobId is:{}", response.getSnapshotJob().getId());
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
