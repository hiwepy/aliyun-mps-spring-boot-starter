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

    /**
     * Region Id（服务地域ID）
     * https://help.aliyun.com/document_detail/43248.html?spm=a2c4g.11186623.6.560.126f42c3AjZuPl
     * https://help.aliyun.com/document_detail/40654.html?spm=a2c4g.11186623.2.11.37a77f248oCoPp#concept-h4v-j5k-xdb
     */
    private String regionId = "cn-hangzhou";
    
	/**
	 * AccessKey, 用于标识、校验用户身份
	 */
	private String accessKey;
	/**
	 * SecretKey, 用于标识、校验用户身份
	 */
	private String secretKey;
	
	/**
     * OSS Bucket 所在数据中心（Region ID）（输入对象），默认：oss-cn-hangzhou
     * <a href=
     * "https://help.aliyun.com/document_detail/31837.html?spm=5176.8465980.0.0.4e701450EbA5gw#concept-zt4-cvy-5db">OSS Region和Endpoint对照表</a>.
     */
    private String ossLocation = "oss-cn-hangzhou";

    /**
     * OSS Bucket（输入对象）
     */
    private String ossBucket;
    
    /**
     * OSS Bucket 所在数据中心（Region ID）（输出对象），不指定时与 ossLocation 相同；
     * <a href=
     * "https://help.aliyun.com/document_detail/31837.html?spm=5176.8465980.0.0.4e701450EbA5gw#concept-zt4-cvy-5db">OSS Region和Endpoint对照表</a>.
     */
    private String outputLocation;
    
    /**
     * OSS Bucket（输出对象），不指定时与 ossBucket 相同
     */
    private String outputBucket;

 

}
