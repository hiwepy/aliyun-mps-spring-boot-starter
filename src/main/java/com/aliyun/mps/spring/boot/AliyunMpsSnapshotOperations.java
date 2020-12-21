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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.mps.spring.boot.model.SnapshotRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.mts.model.v20140618.SubmitSnapshotJobRequest;
import com.aliyuncs.mts.model.v20140618.SubmitSnapshotJobResponse;
import com.google.common.collect.ImmutableMap;
import com.tencentcloud.spring.boot.tim.resp.AccountImportResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 媒体处理
 * https://help.aliyun.com/document_detail/29196.html?spm=a2c4g.11186623.6.542.59ae7b70EKNHjP
 */
@Slf4j
public class AliyunMpsSnapshotOperations extends AliyunMpsOperations {

	public AliyunMpsSnapshotOperations(AliyunMpsTemplate mpsTemplate) {
		super(mpsTemplate);
	}
	
	/**
	 * 1、提交转码作业
	 * API：
	 * https://help.aliyun.com/document_detail/29226.html?spm=a2c4g.11186623.6.659.2dab3dbfqBWDro
	 * https://help.aliyun.com/document_detail/67662.html?spm=a2c4g.11186623.6.757.6be33f00ikEQcR
	 * @param userId 业务用户ID
	 * @param nickname 用户昵称
	 * @param avatar 用户头像
	 * @return 操作结果
	 */
	public SnapshotRequest snapshot(String pipelineId) {
		
        // request
        SubmitSnapshotJobRequest request = new SubmitSnapshotJobRequest();
        // Input
        JSONObject input = new JSONObject();
        input.put("Location", ossLocation);
        input.put("Bucket", ossBucket);
        try {
            input.put("Object", URLEncoder.encode(ossInputObject, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("input URL encode failed");
        }
        request.setInput(input.toJSONString());
        // SnapshotConfig
        JSONObject snapshotConfig = new JSONObject();
        // SnapshotConfig->OutputFile
        JSONObject output = new JSONObject();
        output.put("Location", ossLocation);
        output.put("Bucket", ossBucket);
        try {
            output.put("Object", URLEncoder.encode(ossOutputObject, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("output URL encode failed");
        }
        snapshotConfig.put("OutputFile", output.toJSONString());
        // SnapshotConfig->Time
        snapshotConfig.put("Time", "2");
        // SnapshotConfig->Interval/Num
        snapshotConfig.put("Interval", "2");
        snapshotConfig.put("Num", "3");
        // SnapshotConfig->Width/Height
        snapshotConfig.put("Height", "360");
        // SnapshotConfig
        request.setSnapshotConfig(snapshotConfig.toJSONString());
        // PipelineId
        request.setPipelineId(pipelineId);
        // call api
        SubmitSnapshotJobResponse response;
        
        try {
            response = getMpsTemplate().getAcsResponse(request);
            System.out.println("RequestId is:"+response.getRequestId());
            System.out.println("JobId is:" + response.getSnapshotJob().getId());
            System.out.println(String.format(
                                        "http://%s.%s.aliyuncs.com/output_00001.jpg",
                                        ossBucket,
                                        ossLocation));
            System.out.println(String.format(
                                        "http://%s.%s.aliyuncs.com/output_00002.jpg",
                                        ossBucket,
                                        ossLocation));
            System.out.println(String.format(
                                        "http://%s.%s.aliyuncs.com/output_00003.jpg",
                                        ossBucket,
                                        ossLocation));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
		
	}
	
	
}
