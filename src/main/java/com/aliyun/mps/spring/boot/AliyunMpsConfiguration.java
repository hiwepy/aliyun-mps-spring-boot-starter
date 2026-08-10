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

/**
 * Spring Boot auto-configuration for Alibaba Cloud Media Processing Service (MPS).
 * <p>Creates the {@link AliyunMpsTemplate} and the backing {@code IAcsClient} from the
 * configured {@link AliyunProperties} and {@link AliyunMpsProperties}.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({ SubmitJobsRequest.class })
@EnableConfigurationProperties({AliyunProperties.class, AliyunMpsProperties.class})
public class AliyunMpsConfiguration {

	/**
	 * Registers a default {@link ObjectMapper} when none is present in the context.
	 * @return a new {@code ObjectMapper} instance
	 */
	@Bean
	@ConditionalOnMissingBean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	/**
	 * Creates the {@link AliyunMpsTemplate} bean.
	 * <p>Resolves the Alibaba Cloud credentials from {@link AliyunMpsProperties}, falling
	 * back to {@link AliyunProperties} when the MPS-specific keys are not set.</p>
	 * @param objectMapper the JSON mapper used to serialise request bodies
	 * @param properties the shared Alibaba Cloud account properties
	 * @param mpsProperties the MPS-specific properties
	 * @return the configured {@code AliyunMpsTemplate}
	 */
	@Bean
	public AliyunMpsTemplate aliyunMpsTemplate(
			ObjectMapper objectMapper,
			AliyunProperties properties,
			AliyunMpsProperties mpsProperties) {
		
		// AccessKey ID of the RAM account used to authenticate the caller.
		String accessKey = StringUtils.hasText(mpsProperties.getAccessKey()) ? mpsProperties.getAccessKey() : properties.getAccessKey();
		// AccessKey Secret of the RAM account used to authenticate the caller.
		String secretKey = StringUtils.hasText(mpsProperties.getSecretKey()) ? mpsProperties.getSecretKey() : properties.getSecretKey();
		
		// Build and initialise the DefaultAcsClient used to call MPS APIs.
		DefaultProfile profile = DefaultProfile.getProfile(mpsProperties.getRegionId(), accessKey,  secretKey);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		
		return new AliyunMpsTemplate(acsClient, objectMapper, mpsProperties);
	}

}
