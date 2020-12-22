package com.aliyun.mps.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = AliyunMpsProperties.PREFIX)
@Data
public class AliyunMpsProperties {

	/**
     * The prefix of the property of {@link AliyunMpsProperties}.
     */
    public static final String PREFIX = "alibaba.cloud.mps";

    private String regionId;
    
	/**
	 * AccessKey, 用于标识、校验用户身份
	 */
	private String accessKey;
	/**
	 * SecretKey, 用于标识、校验用户身份
	 */
	private String secretKey;
	
	/**
     * inputLocation, please see <a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.659.29f145dc3KOwTh">oss
     * docs</a>.
     */
    private String inputLocation;

    private String inputBucket;
    
    private String outputLocation;

    private String outputBucket;

}
