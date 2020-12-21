/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aliyun.mps.spring.boot.model.transform;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 作业输入，JSON对象，Input定义详见参数详情 。例如：{"Bucket":"example-bucket","Location":"oss-cn-hangzhou","Object":"example.flv"}需在控制台中完成云资源授权。 
 * https://help.aliyun.com/document_detail/29253.html?spm=a2c4g.11186623.2.14.53cb7b709JhnSM#reference-hhy-xc4-y2b
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class Input {

	/**
	 * 输入文件所在OSS Bucket。需在控制台中资源控制频道里的Bucket授权页面授予此Bucket读权限给媒体处理服务，遵守OSS Bucket定义，见术语表Bucket。
	 */
	@JsonProperty("Bucket")
	private String bucket;

	/**
	 * 输入OSS Bucket所在数据中心（OSS Location）。遵守OSS Location定义，见术语表Location。
	 */
	@JsonProperty("Location")
	private String location;

	/**
	 * 输入文件 （OSS Object）。须进行UrlEncode，使用UTF-8编码，遵守OSS Object定义，见术语表Object。
	 */
	@JsonProperty("Object")
	private String object;
	
}
