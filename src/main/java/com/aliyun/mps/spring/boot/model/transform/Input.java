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
 * Job input descriptor, serialised to a JSON object (see the Input parameter details).
 * <p>Example: {@code {"Bucket":"example-bucket","Location":"oss-cn-hangzhou","Object":"example.flv"}}.
 * Cloud resource authorisation must be completed in the console beforehand.</p>
 * <p>See
 * <a href="https://help.aliyun.com/document_detail/29253.html">the Input parameter reference</a>.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class Input {

	/**
	 * OSS bucket holding the input object. Read access must be granted to the Media Processing
	 * Service on the Bucket authorisation page of the resource control console.
	 */
	@JsonProperty("Bucket")
	private String bucket;

	/** OSS location (region id) of the input bucket. */
	@JsonProperty("Location")
	private String location;

	/**
	 * Input file (OSS object key). Must be URL-encoded using UTF-8.
	 */
	@JsonProperty("Object")
	private String object;

}
