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

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import com.aliyun.mps.spring.boot.model.TransformComplexRequest;
import com.aliyun.mps.spring.boot.model.TransformSimpleRequest;
import com.aliyun.mps.spring.boot.model.transform.Container;
import com.aliyun.mps.spring.boot.model.transform.Input;
import com.aliyun.mps.spring.boot.model.transform.OutputSimple;
import com.aliyun.mps.spring.boot.model.transform.Video;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.mts.model.v20140618.CancelJobRequest;
import com.aliyuncs.mts.model.v20140618.CancelJobResponse;
import com.aliyuncs.mts.model.v20140618.QueryJobListRequest;
import com.aliyuncs.mts.model.v20140618.QueryJobListResponse;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.mts.model.v20140618.SubmitJobsResponse;
import com.aliyuncs.mts.model.v20140618.SubmitJobsResponse.JobResult;

import lombok.extern.slf4j.Slf4j;

/**
 * Transcode operations for Alibaba Cloud Media Processing Service.
 * <p>See <a href="https://help.aliyun.com/document_detail/29196.html">the transcode documentation</a>.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Slf4j
public class AliyunMpsTransformOperations extends AliyunMpsOperations {

	/**
	 * Creates transcode operations bound to the given template.
	 * @param mpsTemplate the template used to execute MPS requests
	 */
	public AliyunMpsTransformOperations(AliyunMpsTemplate mpsTemplate) {
		super(mpsTemplate);
	}

	/**
	 * 1. Submits a template-based transcode job using the template's default OSS input/output.
	 * <p>API references:
	 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
	 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitJobs parameters</a>.</p>
	 * @param templateId the transcode template id. Both custom and system-preset templates are supported.
	 * @param inputObject the input file name (OSS object key)
	 * @param outputObject the output file name (OSS object key)
	 * @return the submit response, or {@code null} when the request fails
	 * @throws Exception if the object key cannot be URL-encoded
	 */
	public SubmitJobsResponse submitJob(
			String templateId,
			String inputObject,
			String outputObject) throws Exception {
		
			// 1. Build the transcode input parameters.
			Input input = new Input();
			input.setBucket(getMpsTemplate().getOssBucket());
			input.setLocation(getMpsTemplate().getOssLocation());
			input.setObject(URLEncoder.encode(inputObject, "utf-8"));

			OutputSimple output = new OutputSimple();
			output.setOutputObject(URLEncoder.encode(outputObject, "utf-8"));
			output.setTemplateId(templateId);

			// Output -> Video
			Video video = new Video();
			video.setCodec("H.264");
			output.setVideo(video);

			// 2. Build the request and set parameters.
			SubmitJobsRequest request = new SubmitJobsRequest();
		
		request.setInput(getMpsTemplate().writeValueAsString(input));
		request.setOutputs(getMpsTemplate().writeValueAsString(Arrays.asList(output)));
		
		request.setPipelineId(getMpsTemplate().getPipelineId());
			request.setOutputBucket(getMpsTemplate().getOutputBucket());
			request.setOutputLocation(getMpsTemplate().getOutputLocation());

			// 3. Execute the request.
			return this.submitJob(request);
		}

		/**
		 * 2. Submits a template-based transcode job with explicit OSS input location and bucket.
		 * <p>API references:
		 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
		 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitJobs parameters</a>.</p>
		 * @param templateId the transcode template id. Both custom and system-preset templates are supported.
		 * @param ossLocation the OSS location of the input bucket, e.g. {@code oss-cn-hangzhou}
		 * @param ossBucket the OSS bucket that holds the input object, e.g. {@code example-bucket}
		 * @param inputObject the input file name (OSS object key)
		 * @param outputObject the output file name (OSS object key)
		 * @return the submit response, or {@code null} when the request fails
		 * @throws Exception if the object key cannot be URL-encoded
		 */
		public SubmitJobsResponse submitJob(
			String templateId,
			String ossLocation,
			String ossBucket,
			String inputObject,
			String outputObject) throws Exception {
		
			// 1. Build the transcode input parameters.
			Input input = new Input();
			input.setBucket(ossBucket);
			input.setLocation(ossLocation);
			input.setObject(URLEncoder.encode(inputObject, "utf-8"));

			OutputSimple output = new OutputSimple();
			output.setOutputObject(URLEncoder.encode(outputObject, "utf-8"));
			output.setTemplateId(templateId);

			// 2. Build the request and set parameters.
			SubmitJobsRequest request = new SubmitJobsRequest();

			request.setInput(getMpsTemplate().writeValueAsString(input));
			request.setOutputs(getMpsTemplate().writeValueAsString(Arrays.asList(output)));

			request.setPipelineId(getMpsTemplate().getPipelineId());
			request.setOutputBucket(ossBucket);
			request.setOutputLocation(ossLocation);

			// 3. Execute the request.
			return this.submitJob(request);
		}

		/**
		 * 3. Submits a transcode job with explicit OSS input and output locations.
		 * <p>API references:
		 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
		 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitJobs parameters</a>.</p>
		 * @param templateId the transcode template id. Both custom and system-preset templates are supported.
		 * @param ossLocation the OSS location of the input bucket, e.g. {@code oss-cn-hangzhou}
		 * @param ossBucket the OSS bucket that holds the input object, e.g. {@code example-bucket}
		 * @param inputObject the input file name (OSS object key)
		 * @param outputLocation the OSS location of the output bucket, e.g. {@code oss-cn-hangzhou}
		 * @param outputBucket the OSS bucket used to store the output object
		 * @param outputObject the output file name (OSS object key)
		 * @return the submit response, or {@code null} when the request fails
		 * @throws Exception if the object key cannot be URL-encoded
		 */
		public SubmitJobsResponse submitJob(
			String templateId,
			String ossLocation,
			String ossBucket,
			String inputObject,
			String outputLocation,
			String outputBucket,
			String outputObject) throws Exception {
		
			// 1. Build the transcode input parameters.
			Input input = new Input();
			input.setBucket(ossBucket);
			input.setLocation(ossLocation);
			input.setObject(URLEncoder.encode(inputObject, "utf-8"));

			OutputSimple output = new OutputSimple();
			output.setOutputObject(URLEncoder.encode(outputObject, "utf-8"));
			output.setTemplateId(templateId);

			// 2. Build the request and set parameters.
			SubmitJobsRequest request = new SubmitJobsRequest();
			request.setInput(getMpsTemplate().writeValueAsString(input));
			request.setOutputs(getMpsTemplate().writeValueAsString(Arrays.asList(output)));
			request.setOutputBucket(outputBucket);
			request.setOutputLocation(outputLocation);
			request.setPipelineId(getMpsTemplate().getPipelineId());

			// 3. Execute the request.
			return this.submitJob(request);
		}

		/**
		 * 4. Submits a transcode job with explicit pipeline and output container format.
		 * <p>API references:
		 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
		 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitJobs parameters</a>.</p>
		 * @param pipelineId the pipeline id. For asynchronous notification the pipeline must be bound to a message topic.
		 * @param templateId the transcode template id. Both custom and system-preset templates are supported.
		 * @param ossLocation the OSS location of the input bucket, e.g. {@code oss-cn-hangzhou}
		 * @param ossBucket the OSS bucket that holds the input object, e.g. {@code example-bucket}
		 * @param inputObject the input file name (OSS object key)
		 * @param outputLocation the OSS location of the output bucket, e.g. {@code oss-cn-hangzhou}
		 * @param outputBucket the OSS bucket used to store the output object
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
		 * @return the submit response, or {@code null} when the request fails
		 * @throws Exception if the object key cannot be URL-encoded
		 */
		public SubmitJobsResponse submitJob(
			String pipelineId, 
			String templateId,
			String ossLocation,
			String ossBucket,
			String inputObject,
			String outputLocation,
			String outputBucket,
			String outputObject,
			String outputFormat) throws Exception {
		
			// 1. Build the transcode input parameters.
			Input input = new Input();
			input.setBucket(ossBucket);
			input.setLocation(ossLocation);
			input.setObject(URLEncoder.encode(inputObject, "utf-8"));

			OutputSimple output = new OutputSimple();
			output.setOutputObject(URLEncoder.encode(outputObject, "utf-8"));
			output.setTemplateId(templateId);

			Container container = new Container();
			container.setFormat(outputFormat);
			output.setContainer(container);

			// 2. Build the request and set parameters.
			SubmitJobsRequest request = new SubmitJobsRequest();

			request.setInput(getMpsTemplate().writeValueAsString(input));
			request.setOutputs(getMpsTemplate().writeValueAsString(Arrays.asList(output)));

			request.setPipelineId(pipelineId);
			request.setOutputBucket(outputBucket);
			request.setOutputLocation(outputLocation);

			// 3. Execute the request.
			return this.submitJob(request);
		}


		/**
		 * 5. Submits a complex transcode job (format conversion, merging, clipping).
		 * <p>API references:
		 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
		 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitJobs parameters</a>.</p>
		 * @param pipelineId the pipeline id. For asynchronous notification the pipeline must be bound to a message topic.
		 * @param outputLocation the OSS location of the output bucket, e.g. {@code oss-cn-hangzhou}
		 * @param outputBucket the OSS bucket used to store the output object
		 * @param transRequest the complex media-processing parameters (conversion, merging, clipping)
		 * @return the submit response, or {@code null} when the request fails
		 * @throws Exception if serialisation fails
		 */
		public SubmitJobsResponse submitJob(
			String pipelineId, 
			String outputLocation,
			String outputBucket,
			TransformComplexRequest transRequest) throws Exception {
			// 1. Build the request and set parameters.
			SubmitJobsRequest request = new SubmitJobsRequest();
			// 2. Set the transcode parameters.
			request.setInput(getMpsTemplate().writeValueAsString(transRequest.getInput()));
			request.setOutputs(getMpsTemplate().writeValueAsString(transRequest.getOutputs()));
			request.setOutputLocation(outputLocation);
			request.setOutputBucket(outputBucket);
			request.setPipelineId(pipelineId);
			// 3. Execute the request.
			return this.submitJob(request);
		}

		/**
		 * 6. Submits a simple transcode job (format conversion only).
		 * <p>API references:
		 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
		 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitJobs parameters</a>.</p>
		 * @param pipelineId the pipeline id. For asynchronous notification the pipeline must be bound to a message topic.
		 * @param outputLocation the OSS location of the output bucket, e.g. {@code oss-cn-hangzhou}
		 * @param outputBucket the OSS bucket used to store the output object
		 * @param transRequest the simple media-processing parameters (format conversion only)
		 * @return the submit response, or {@code null} when the request fails
		 * @throws Exception if serialisation fails
		 */
		public SubmitJobsResponse submitJob(
			String pipelineId, 
			String outputLocation,
			String outputBucket,
			TransformSimpleRequest transRequest) throws Exception {
			// 1. Build the request and set parameters.
			SubmitJobsRequest request = new SubmitJobsRequest();
			// 2. Set the transcode parameters.
			request.setInput(getMpsTemplate().writeValueAsString(transRequest.getInput()));
			request.setOutputs(getMpsTemplate().writeValueAsString(transRequest.getOutputs()));
			request.setOutputLocation(outputLocation);
			request.setOutputBucket(outputBucket);
			request.setPipelineId(pipelineId);
			// 3. Execute the request.
			return this.submitJob(request);
		}

		/**
		 * 7. Submits a complex transcode job (format conversion, merging, clipping) using the default output bucket.
		 * <p>API references:
		 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
		 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitJobs parameters</a>.</p>
		 * @param pipelineId the pipeline id. For asynchronous notification the pipeline must be bound to a message topic.
		 * @param transRequest the complex media-processing parameters (conversion, merging, clipping)
		 * @return the submit response, or {@code null} when the request fails
		 * @throws Exception if serialisation fails
		 */
		public SubmitJobsResponse submitJob(
				String pipelineId,
				TransformComplexRequest transRequest) throws Exception {
			// 1. Build the request and set parameters.
			SubmitJobsRequest request = new SubmitJobsRequest();
			// 2. Set the transcode parameters.
			request.setInput(getMpsTemplate().writeValueAsString(transRequest.getInput()));
			request.setOutputs(getMpsTemplate().writeValueAsString(transRequest.getOutputs()));
			request.setOutputBucket(getMpsTemplate().getOutputBucket());
			request.setOutputLocation(getMpsTemplate().getOutputLocation());
			request.setPipelineId(pipelineId);
			// 3. Execute the request.
			return this.submitJob(request);
		}


		/**
		 * 8. Submits a simple transcode job (format conversion only) using the default output bucket.
		 * <p>API references:
		 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
		 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitJobs parameters</a>.</p>
		 * @param pipelineId the pipeline id. For asynchronous notification the pipeline must be bound to a message topic.
		 * @param transRequest the simple media-processing parameters (format conversion only)
		 * @return the submit response, or {@code null} when the request fails
		 * @throws Exception if serialisation fails
		 */
		public SubmitJobsResponse submitJob(
				String pipelineId,
				TransformSimpleRequest transRequest) throws Exception {
			// 1. Build the request and set parameters.
			SubmitJobsRequest request = new SubmitJobsRequest();
			// 2. Set the transcode parameters.
			request.setInput(getMpsTemplate().writeValueAsString(transRequest.getInput()));
			request.setOutputs(getMpsTemplate().writeValueAsString(transRequest.getOutputs()));
			request.setOutputBucket(getMpsTemplate().getOutputBucket());
			request.setOutputLocation(getMpsTemplate().getOutputLocation());
			request.setPipelineId(pipelineId);
			// 3. Execute the request.
			return this.submitJob(request);
		}

		/**
		 * 9. Submits a transcode job from a pre-built request.
		 * <p>API references:
		 * <a href="https://help.aliyun.com/document_detail/29226.html">SubmitJobs</a>,
		 * <a href="https://help.aliyun.com/document_detail/67662.html">SubmitJobs parameters</a>.</p>
		 * @param request the media-processing request
		 * @return the submit response, or {@code null} when the request fails
		 */
		public SubmitJobsResponse submitJob(SubmitJobsRequest request) {
			// Send the request and handle the response or exceptions.
	        SubmitJobsResponse response = null;
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
		        log.error("Server exception, ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
		    } catch (ClientException e) {
		        e.printStackTrace();
		        log.error("Client exception, ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
		    }
			return response;
		}

		/**
		 * 10. Cancels a transcode job.
		 * <p>See <a href="https://help.aliyun.com/document_detail/29227.html">CancelJob</a>.</p>
		 * @param jobId the job id
		 * @return the cancel response, or {@code null} when the request fails
		 */
		public CancelJobResponse cancelJob( String jobId) {
			CancelJobRequest request = new CancelJobRequest();
			request.setJobId(jobId);
			// Send the request and handle the response or exceptions.
			CancelJobResponse response = null;
	        try {
	            response = getMpsTemplate().getAcsResponse(request);
	            log.debug("RequestId is:{}", response.getRequestId());
	            log.debug("JobId is:{}", response.getJobId());
	        } catch (ServerException e) {
		        e.printStackTrace();
		        log.error("Server exception, ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
		    } catch (ClientException e) {
		        e.printStackTrace();
		        log.error("Client exception, ErrorType : {}, ErrorCode : {}, ErrMsg : {}", e.getErrorType(), e.getErrCode(),  e.getErrMsg());
		    }
			return response;
		}

		/**
		 * 11. Queries transcode jobs by id. Results are sorted by CreationTime descending.
		 * <p>See <a href="https://help.aliyun.com/document_detail/29228.html">QueryJobList</a>.</p>
		 * @param jobIds the transcode job ids; comma-separated, up to 10 per call
		 * @return the query response, or {@code null} when the request fails
		 */
		public QueryJobListResponse queryJob( String... jobIds) {
			if(Objects.isNull(jobIds) || jobIds.length == 0) {
				return null;
			}
			QueryJobListRequest request = new QueryJobListRequest();
			request.setJobIds(Arrays.asList(jobIds).stream().collect(Collectors.joining(",")));
			// Send the request and handle the response or exceptions.
			QueryJobListResponse response = null;
	        try {
	            response = getMpsTemplate().getAcsResponse(request);
	            log.debug("RequestId is:{}", response.getRequestId());
	            log.debug("JobList is:{}", getMpsTemplate().writeValueAsString(response.getJobList()));
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
