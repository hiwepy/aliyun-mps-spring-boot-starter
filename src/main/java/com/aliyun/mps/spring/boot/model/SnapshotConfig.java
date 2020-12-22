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

import com.aliyun.mps.spring.boot.model.transform.OutputFile;
import com.aliyun.mps.spring.boot.model.transform.TileOut;
import com.aliyun.mps.spring.boot.model.transform.TileOutputFile;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * TODO
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SnapshotConfig {

	@JsonProperty("Time")
	private String time;

	@JsonProperty("Interval")
	private String interval;

	@JsonProperty("Num")
	private String num;

	@JsonProperty("Width")
	private String width;

	@JsonProperty("Height")
	private String height;

	@JsonProperty("FrameType")
	private String frameType;
	
	@JsonProperty("OutputFile")
	private OutputFile outputFile;
	
	@JsonProperty("TileOutputFile")
	private TileOutputFile tileOutputFile;
	
	@JsonProperty("TileOut")
	private TileOut tileOut;
	
}
