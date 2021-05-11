package com.aliyun.mps.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ConditionalOnClass({ SubmitJobsRequest.class })
@EnableConfigurationProperties({AliyunProperties.class, AliyunMpsProperties.class})
public class AliyunMpsConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public AliyunMpsTemplate aliyunMpsTemplate(
			ObjectMapper objectMapper,
			AliyunProperties properties,
			AliyunMpsProperties mpsProperties) {
		
		/**
		 * RAM账号的AccessKey ID, 用于标识、校验用户身份
		 */
		String accessKey = StringUtils.hasText(mpsProperties.getAccessKey()) ? mpsProperties.getAccessKey() : properties.getAccessKey();
		/**
		 * RAM账号Access Key Secret, 用于标识、校验用户身份
		 */
		String secretKey = StringUtils.hasText(mpsProperties.getSecretKey()) ? mpsProperties.getSecretKey() : properties.getSecretKey();
		
		// 创建DefaultAcsClient实例并初始化
		DefaultProfile profile = DefaultProfile.getProfile(mpsProperties.getRegionId(), accessKey,  secretKey);  
		IAcsClient acsClient = new DefaultAcsClient(profile);
		
		return new AliyunMpsTemplate(acsClient, objectMapper, mpsProperties);
	}

}
