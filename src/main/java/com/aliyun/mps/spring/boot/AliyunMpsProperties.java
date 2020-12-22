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

}
