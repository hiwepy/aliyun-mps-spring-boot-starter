package com.aliyun.mps.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;

@Configuration
@ConditionalOnClass({ SubmitJobsRequest.class })
@EnableConfigurationProperties(AliyunMpsProperties.class)
public class AliyunMpsConfiguration {
    
	@Bean
	public AliyunMpsTemplate aliyunOnsMqTemplate() {
		return new AliyunMpsTemplate();
	}
	
}
