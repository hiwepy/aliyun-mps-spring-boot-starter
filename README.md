# aliyun-ons-spring-boot-starter

#### 组件简介

 > 基于阿里云视频转码实现的 Spring Boot Starter 实现，依赖少，使用简单

#### 使用说明

##### 1、Spring Boot 项目添加 Maven 依赖

``` xml
<dependency>
	<groupId>com.github.hiwepy</groupId>
	<artifactId>aliyun-mps-spring-boot-starter</artifactId>
	<version>1.0.1.RELEASE</version>
</dependency>
```

##### 2、在`application.yml`文件中增加如下配置

```yaml
#################################################################################################
### 阿里云Mps配置：
#################################################################################################
alibaba:
  cloud:
    mps:
      access-key: test
      secret-key: test
      oss-location: ss
      oss-bucket: ss
      region-id: hangzhou
```

##### 3、使用示例

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliyunMpsApplicationTests {

	@Autowired
	private AliyunMpsTemplate mpsTemplate;
	
    @Test
    public void testProducer() throws Exception {
    }
    

}
```

## Jeebiz 技术社区

Jeebiz 技术社区 **微信公共号**、**小程序**，欢迎关注反馈意见和一起交流，关注公众号回复「Jeebiz」拉你入群。

|公共号|小程序|
|---|---|
| ![](https://raw.githubusercontent.com/hiwepy/static/main/images/qrcode_for_gh_1d965ea2dfd1_344.jpg)| ![](https://raw.githubusercontent.com/hiwepy/static/main/images/gh_09d7d00da63e_344.jpg)|