package com.aliyun.mps.spring.boot;

import org.springframework.util.StringUtils;

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
 * Core template for invoking Alibaba Cloud Media Processing Service (MPS) APIs.
 * <p>Wraps an Alibaba Cloud {@link IAcsClient} and exposes grouped operation helpers
 * ({@link AliyunMpsPipelineOperations}, {@link AliyunMpsTransformOperations} and
 * {@link AliyunMpsSnapshotOperations}).</p>
 * <p>See <a href="https://help.aliyun.com/document_detail/66804.html">the MPS overview</a>.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Slf4j
public class AliyunMpsTemplate {

	private final ObjectMapper objectMapper;
	private final IAcsClient acsClient;
	private final AliyunMpsPipelineOperations pipelineOps = new AliyunMpsPipelineOperations(this);
	private final AliyunMpsTransformOperations transformOps = new AliyunMpsTransformOperations(this);
	private final AliyunMpsSnapshotOperations snapshotOps = new AliyunMpsSnapshotOperations(this);

	/** The default pipeline id used to queue submitted jobs. */
	private final String pipelineId;
	/**
     * OSS location (region id) of the input bucket. Defaults to {@code oss-cn-hangzhou}.
     * <p>See
     * <a href="https://help.aliyun.com/document_detail/31837.html">OSS region/endpoint mapping</a>.</p>
     */
    private final String ossLocation;

    /** OSS bucket that holds the input objects. */
    private final String ossBucket;

    /**
     * OSS location (region id) of the output bucket. Falls back to {@link #ossLocation} when not set.
     * <p>See
     * <a href="https://help.aliyun.com/document_detail/31837.html">OSS region/endpoint mapping</a>.</p>
     */
    private final String outputLocation;

    /** OSS bucket used to store output objects. Falls back to {@link #ossBucket} when not set. */
    private final String outputBucket;

	/**
	 * Creates a template bound to the given client and properties.
	 * @param acsClient the Alibaba Cloud ACS client used to execute requests
	 * @param objectMapper the JSON mapper used to serialise request bodies
	 * @param properties the MPS-specific properties
	 */
	public AliyunMpsTemplate(IAcsClient acsClient, ObjectMapper objectMapper, AliyunMpsProperties properties) {
		this.acsClient = acsClient;
		this.objectMapper = objectMapper;
		this.pipelineId = properties.getPipelineId();
		this.ossLocation = properties.getOssLocation();
		this.ossBucket = properties.getOssBucket();
		this.outputLocation = StringUtils.hasText(properties.getOutputLocation()) ? properties.getOutputLocation() : properties.getOssLocation();
		this.outputBucket = StringUtils.hasText(properties.getOutputBucket()) ? properties.getOutputBucket() : properties.getOssBucket();
	}

	/**
	 * Executes the given request and returns the response.
	 * @param <T> the response type
	 * @param request the ACS request
	 * @return the ACS response
	 * @throws ServerException when the server returns an error
	 * @throws ClientException when the client fails to send the request
	 */
	public <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request) throws ServerException, ClientException {
		return getAcsClient().getAcsResponse(request);
	}

	/**
	 * Executes the given request with retry options.
	 * @param <T> the response type
	 * @param request the ACS request
	 * @param autoRetry whether to retry automatically
	 * @param maxRetryCounts the maximum number of retries
	 * @return the ACS response
	 * @throws ServerException when the server returns an error
	 * @throws ClientException when the client fails to send the request
	 */
	public <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, boolean autoRetry, int maxRetryCounts)
			throws ServerException, ClientException {
		return getAcsClient().getAcsResponse(request, autoRetry, maxRetryCounts);
	}

	/**
	 * Executes the given request using a specific profile.
	 * @param <T> the response type
	 * @param request the ACS request
	 * @param profile the client profile to use
	 * @return the ACS response
	 * @throws ServerException when the server returns an error
	 * @throws ClientException when the client fails to send the request
	 */
	public <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, IClientProfile profile)
			throws ServerException, ClientException {
		return getAcsClient().getAcsResponse(request, profile);
	}

	/**
	 * Executes the given request against a specific region.
	 * @param <T> the response type
	 * @param request the ACS request
	 * @param regionId the target region id
	 * @return the ACS response
	 * @throws ServerException when the server returns an error
	 * @throws ClientException when the client fails to send the request
	 */
	public <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request, String regionId)
			throws ServerException, ClientException {
		return getAcsClient().getAcsResponse(request, regionId);
	}

	/**
	 * Serialises the given value to a JSON string.
	 * @param value the object to serialise
	 * @return the JSON string, or an empty string when serialisation fails
	 */
	public String writeValueAsString(Object value) {
		try {
			return getObjectMapper().writeValueAsString(value);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return "";
	}

	/**
	 * Executes a common (untyped) request.
	 * @param request the common request
	 * @return the common response
	 * @throws ServerException when the server returns an error
	 * @throws ClientException when the client fails to send the request
	 */
	public CommonResponse getCommonResponse(CommonRequest request) throws ServerException, ClientException {
		return getAcsClient().getCommonResponse(request);
	}

	/** @return the pipeline operation helper */
	public AliyunMpsPipelineOperations getPipelineOps() {
		return pipelineOps;
	}

	/** @return the snapshot operation helper */
	public AliyunMpsSnapshotOperations getSnapshotOps() {
		return snapshotOps;
	}

	/** @return the transcode operation helper */
	public AliyunMpsTransformOperations getTransformOps() {
		return transformOps;
	}

	/** @return the underlying Alibaba Cloud ACS client */
	public IAcsClient getAcsClient() {
		return acsClient;
	}

	/** @return the JSON mapper used to serialise request bodies */
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/** @return the default pipeline id */
	public String getPipelineId() {
		return pipelineId;
	}

	/** @return the OSS location (region id) of the input bucket */
	public String getOssLocation() {
		return ossLocation;
	}

	/** @return the OSS bucket that holds the input objects */
	public String getOssBucket() {
		return ossBucket;
	}

	/** @return the OSS location (region id) of the output bucket */
	public String getOutputLocation() {
		return outputLocation;
	}

	/** @return the OSS bucket used to store output objects */
	public String getOutputBucket() {
		return outputBucket;
	}

}
