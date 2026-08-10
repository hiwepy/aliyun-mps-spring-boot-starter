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
 * Base class for Alibaba Cloud Media Processing Service (MPS) operation helpers.
 * <p>See <a href="https://help.aliyun.com/document_detail/66804.html">the MPS overview</a>.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public abstract class AliyunMpsOperations {

	/** The shared {@link AliyunMpsTemplate} used to invoke MPS APIs. */
	protected AliyunMpsTemplate mpsTemplate;

	/**
	 * Creates an operation helper bound to the given template.
	 * @param mpsTemplate the template used to execute MPS requests
	 */
	public AliyunMpsOperations(AliyunMpsTemplate mpsTemplate) {
		this.mpsTemplate = mpsTemplate;
	}

	/**
	 * Returns the underlying template.
	 * @return the template used to execute MPS requests
	 */
	public AliyunMpsTemplate getMpsTemplate() {
		return mpsTemplate;
	}

}
