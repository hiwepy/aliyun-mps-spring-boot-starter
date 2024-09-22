package com.aliyun.mps.spring.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AliyunMpsApplicationTests {

	@Autowired
	private AliyunMpsTemplate mpsTemplate;
	
    @Test
    public void testProducer() throws Exception {
    }
    

}
