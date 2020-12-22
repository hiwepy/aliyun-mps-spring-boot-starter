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
import com.aliyun.mps.spring.boot.model.transform.OutputComplex;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 复杂的媒体处理参数对象（支持格式转换、拼接、剪辑）
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class TransformComplexRequest {

	/**
	 * 作业输入，JSON对象，Input定义详见参数详情 。
	 */
	@JsonProperty("Input")
	public Input input;
	
	/**
	 * 作业输出，JSON对象，Output定义详见参数详情 。
	 */
	@JsonProperty("Outputs")
	public List<OutputComplex> outputs;
	
}
