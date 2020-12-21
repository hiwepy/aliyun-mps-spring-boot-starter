package com.aliyun.mps.spring.boot;

import com.aliyuncs.AcsRequest;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 媒体处理
 * https://help.aliyun.com/document_detail/66804.html?spm=a2c4g.11186623.6.644.59ae7b70zmnWLk
 */
@Slf4j
public class AliyunMpsTemplate {

	private final ObjectMapper objectMapper;
	private final IAcsClient acsClient;
	private final AliyunMpsPipelineOperations pipelineOps = new AliyunMpsPipelineOperations(this);
	private final AliyunMpsTransformOperations transformOps = new AliyunMpsTransformOperations(this);
	private final AliyunMpsSnapshotOperations snapshotOps = new AliyunMpsSnapshotOperations(this);

	public AliyunMpsTemplate(IAcsClient acsClient, ObjectMapper objectMapper) {
		this.acsClient = acsClient;
		this.objectMapper = objectMapper;
	}
	
	public <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request) throws ServerException, ClientException {
		return getAcsClient().getAcsResponse(request);
	}
	
	public <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, boolean autoRetry, int maxRetryCounts)
			throws ServerException, ClientException {
		return getAcsClient().getAcsResponse(request, autoRetry, maxRetryCounts);
	}

	public <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, IClientProfile profile)
			throws ServerException, ClientException {
		return getAcsClient().getAcsResponse(request, profile);
	}

	public <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, String regionId)
			throws ServerException, ClientException {
		return getAcsClient().getAcsResponse(request, regionId);
	}
	
	public String writeValueAsString(Object value) {
		try {
			return getObjectMapper().writeValueAsString(value);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return "";
	}
	
	public CommonResponse getCommonResponse(CommonRequest request) throws ServerException, ClientException {
		return getAcsClient().getCommonResponse(request);
	}

	public AliyunMpsPipelineOperations getPipelineOps() {
		return pipelineOps;
	}

	public AliyunMpsSnapshotOperations getSnapshotOps() {
		return snapshotOps;
	}

	public AliyunMpsTransformOperations getTransformOps() {
		return transformOps;
	}
	
	public IAcsClient getAcsClient() {
		return acsClient;
	}
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

}
