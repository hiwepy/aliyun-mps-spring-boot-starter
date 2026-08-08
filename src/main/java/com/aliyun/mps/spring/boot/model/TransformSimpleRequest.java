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
package com.aliyun.mps.spring.boot.model;

import java.util.List;

import com.aliyun.mps.spring.boot.model.transform.Input;
import com.aliyun.mps.spring.boot.model.transform.OutputSimple;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Simple media-processing parameters (format conversion only).
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class TransformSimpleRequest {

	/** Job input, serialised to a JSON object (see the Input parameter details). */
	@JsonProperty("Input")
	public Input input;

	/** Job outputs, serialised to a JSON object (see the Output parameter details). */
	@JsonProperty("Outputs")
	public List<OutputSimple> outputs;

}
