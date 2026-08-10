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

import lombok.Data;

/**
 * Tiled snapshot layout configuration for a Media Processing Service snapshot job.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TileOut {

	/** Number of rows in the tile grid. */
	private String lines;

	/** Number of columns in the tile grid. */
	private String columns;

	/** Width of each cell, in pixels. */
	private String cellWidth;

	/** Height of each cell, in pixels. */
	private String cellHeight;

	/** Outer margin of the tile grid, in pixels. */
	private String margin;

	/** Padding between cells, in pixels. */
	private String padding;

	/** Background colour of the tile grid. */
	private String color;

	/** Whether to keep the original cell pictures. */
	private String isKeepCellPic;

	/** Cell selection step. */
	private String cellSelStep;
}