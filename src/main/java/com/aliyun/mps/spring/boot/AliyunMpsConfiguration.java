package com.aliyun.mps.spring.boot;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ConditionalOnClass({ SubmitJobsRequest.class })
@EnableConfigurationProperties(AliyunMpsProperties.class)
public class AliyunMpsConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public IAcsClient acsClient(AliyunMpsProperties properties) {
		// 创建DefaultAcsClient实例并初始化
		DefaultProfile profile = DefaultProfile.getProfile(properties.getRegionId(), // 地域ID
				properties.getAccessKey(), // RAM账号的AccessKey ID
				properties.getAccessKey()); // RAM账号Access Key Secret
		IAcsClient client = new DefaultAcsClient(profile);
		return client;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public AliyunMpsTemplate aliyunOnsMqTemplate(IAcsClient acsClient,
			ObjectProvider<ObjectMapper> objectMapperProvider) {
		return new AliyunMpsTemplate(acsClient, objectMapperProvider.getIfAvailable());
	}

}
