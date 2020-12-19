package com.aliyun.openservices.spring.boot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.mts.model.v20140618.*;
public class Snapshot {
	
    private static String accessKeyId = "xxx";
    private static String accessKeySecret = "xxx";
    private static String mpsRegionId = "cn-hangzhou";
    private static String pipelineId = "xxx";
    private static String ossLocation = "oss-cn-hangzhou";
    private static String ossBucket = "xxx";
    private static String ossInputObject = "input.mp4";
    private static String ossOutputObject = "output_{Count}.jpg";
    public static void main(String[] args) {
        // DefaultAcsClient
        DefaultProfile profile = DefaultProfile.getProfile(
                mpsRegionId,      // Region ID
                accessKeyId,      // AccessKey ID
                accessKeySecret); // Access Key Secret
        IAcsClient client = new DefaultAcsClient(profile);
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
            response = client.getAcsResponse(request);
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