package com.aliyun.mps.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration properties for Alibaba Cloud Media Processing Service (MPS).
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = AliyunMpsProperties.PREFIX)
@Data
public class AliyunMpsProperties {

	/**
     * The prefix of the property of {@link AliyunMpsProperties}.
     */
    public static final String PREFIX = "alibaba.cloud.mps";

    /** The pipeline id used to queue submitted jobs. */
	private String pipelineId;

    /**
     * Region id of the MPS service. Defaults to {@code cn-hangzhou}.
     * <p>See
     * <a href="https://help.aliyun.com/document_detail/43248.html">region list</a> and
     * <a href="https://help.aliyun.com/document_detail/40654.html">service endpoints</a>.</p>
     */
    private String regionId = "cn-hangzhou";

	/** AccessKey id used to authenticate the MPS caller. */
	private String accessKey;
	/** AccessKey secret used to authenticate the MPS caller. */
	private String secretKey;

	/**
     * OSS location (region id) of the input bucket. Defaults to {@code oss-cn-hangzhou}.
     * <p>See
     * <a href="https://help.aliyun.com/document_detail/31837.html">OSS region/endpoint mapping</a>.</p>
     */
    private String ossLocation = "oss-cn-hangzhou";

    /** OSS bucket that holds the input objects. */
    private String ossBucket;

    /**
     * OSS location (region id) of the output bucket. When unset it falls back to {@link #ossLocation}.
     * <p>See
     * <a href="https://help.aliyun.com/document_detail/31837.html">OSS region/endpoint mapping</a>.</p>
     */
    private String outputLocation;

    /** OSS bucket used to store output objects. When unset it falls back to {@link #ossBucket}. */
    private String outputBucket;



}
