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
package com.aliyun.mps.spring.boot;

/**
 * 媒体处理
 * https://help.aliyun.com/document_detail/66804.html?spm=a2c4g.11186623.6.644.59ae7b70zmnWLk
 */
public abstract class AliyunMpsOperations {
 
	protected AliyunMpsTemplate mpsTemplate;

	public AliyunMpsOperations(AliyunMpsTemplate mpsTemplate) {
		this.mpsTemplate = mpsTemplate;
	}

	public AliyunMpsTemplate getMpsTemplate() {
		return mpsTemplate;
	}
	
}
