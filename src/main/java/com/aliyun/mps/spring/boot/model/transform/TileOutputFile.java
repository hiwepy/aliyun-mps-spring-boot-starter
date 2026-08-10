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
 * Location descriptor for the tiled snapshot image produced by a Media Processing Service job.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TileOutputFile {

	/** OSS bucket used to store the tiled output object. */
	@JsonProperty("Bucket")
	private String bucket;

	/** OSS location (region id) of the output bucket. */
	@JsonProperty("Location")
	private String location;

	/** Tiled output file name (OSS object key). */
	@JsonProperty("Object")
	private String object;

	/** ARN of the role used to access the output bucket. */
	@JsonProperty("RoleArn")
	private String roleArn;

}