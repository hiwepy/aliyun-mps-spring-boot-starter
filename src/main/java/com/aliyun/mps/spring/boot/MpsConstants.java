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
 * TODO
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
public final class MpsConstants {

	/**
	 * Prefix of AliyunMpsProperties.
	 */
	public static final String PREFIX = "alibaba.cloud.mps";

	/**
	 * Enable Mps.
	 */
	public static final String ENABLED = PREFIX + ".enabled";

	private MpsConstants() {
		throw new AssertionError("Must not instantiate constant utility class");
	}
	
}
