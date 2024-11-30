package com.paul.paulapiclientsdk.Service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.paul.paulapiclientsdk.client.PaulApiClient;
import com.paul.paulapiclientsdk.exception.ApiException;
import com.paul.paulapiclientsdk.exception.ErrorResponse;
import com.paul.paulapiclientsdk.model.request.BaseRequest;
import com.paul.paulapiclientsdk.model.response.ResultResponse;
import com.paul.paulapiclientsdk.utils.SignUtils;
import com.paul.paulapicommon.common.ErrorCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public abstract class BaseService implements ApiService {
    protected PaulApiClient paulApiClient;

    /**
     * 网关地址
     */
    private String gatewayHost = "localhost:8090";

    public void checkConfig(PaulApiClient paulApiClient) throws ApiException {
        if (paulApiClient == null && this.getPaulApiClient() == null) {
            throw new ApiException(ErrorCode.NO_AUTH_ERROR, "请先配置密钥");
        }
        if (paulApiClient != null && !StringUtils.isAnyBlank(paulApiClient.getAccessKey(), paulApiClient.getSecretKey())) {
            this.setPaulApiClient(paulApiClient);
        }

    }

    /**
     * 执行请求
     * @param request
     * @return
     * @param <O>
     * @param <T>
     * @throws ApiException
     */
    private <O, T extends ResultResponse> HttpResponse doRequest(BaseRequest<O, T> request) throws ApiException {
        try (HttpResponse httpResponse = getHttpRequestByRequestMethod(request).execute()) {
            return httpResponse;
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OPERATION_ERROR);
        }

    }

    /**
     * 通过请求方法获取http响应
     * @param request
     * @return
     * @param <O>
     * @param <T>
     * @throws ApiException
     */
    private <O, T extends ResultResponse> HttpRequest getHttpRequestByRequestMethod(BaseRequest<O, T> request) throws ApiException {

        if (ObjectUtils.isEmpty(request)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR);
        }
        String path = request.getPath().trim();
        String method = request.getMethod().trim().toUpperCase();

        if (ObjectUtils.isEmpty(method)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求方法不存在");
        }
        if (StringUtils.isBlank(path)) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "请求路径不存在");
        }

        if (path.startsWith(gatewayHost)) {
            path = path.substring(gatewayHost.length());
        }
        log.info("请求方法：{},请求路径:{}，请求参数：{}", method, path, request.getRequestParams());
        HttpRequest httpRequest;
        switch (method) {
            case "GET": {
                httpRequest = HttpRequest.get(splicingGetRequest(request,path));
                break;
            }
            case "POST": {
                httpRequest = HttpRequest.post(gatewayHost + path);
                break;
            }
            default: {
                throw new ApiException(ErrorCode.OPERATION_ERROR, "不支持该请求");
            }
        }
        return httpRequest.addHeaders(getHeaders(JSONUtil.toJsonStr(request),paulApiClient)).body(JSONUtil.toJsonStr(request.getRequestParams()));
    }

    /**
     * 获取响应数据
     * @param request
     * @return
     * @param <O>
     * @param <T>
     * @throws ApiException
     */
    public <O, T extends ResultResponse> T res(BaseRequest<O, T> request) throws ApiException {
        if (paulApiClient == null || StringUtils.isAnyBlank(paulApiClient.getAccessKey(), paulApiClient.getSecretKey())) {
            throw new ApiException(ErrorCode.NO_AUTH_ERROR, "请先配置密钥");
        }
        T rsp;
        try {
            Class<T> clazz = request.getResponseClass();
            rsp = clazz.newInstance();
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
        HttpResponse httpResponse = doRequest(request);
        String body = httpResponse.body();
        Map<String, Object> data = new HashMap<>();
        if (httpResponse.getStatus() != 200) {
            ErrorResponse errorResponse = JSONUtil.toBean(body, ErrorResponse.class);
            data.put("code", errorResponse.getCode());
            data.put("message", errorResponse.getMessage());
        } else {
            try {
                data = new Gson().fromJson(body, new TypeToken<Map<String, Object>>() {
                }.getType());
            } catch (JsonSyntaxException e) {
                data.put("value", body);
            }
        }
        rsp.setData(data);
        return rsp;
    }

    /**
     * 拼接GET请求
     * @param request
     * @param path
     * @return
     * @param <O>
     * @param <T>
     * @throws ApiException
     */
    private <O,T extends ResultResponse> String splicingGetRequest(BaseRequest<O, T> request,String path) throws ApiException {
        StringBuilder urlBuilder = new StringBuilder(gatewayHost);
        if(urlBuilder.toString().endsWith("/") && path.startsWith("/")){
            urlBuilder.setLength(urlBuilder.length()-1);
        }
        urlBuilder.append(path);
        if(!request.getRequestParams().isEmpty()){
            urlBuilder.append("?");
            for(Map.Entry<String, Object> entry : request.getRequestParams().entrySet()){
                String key = entry.getKey();
                String value = entry.getValue().toString();
                urlBuilder.append(key).append("=").append(value).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length()-1);
        }
        log.info("GET请求路径：{}",urlBuilder);
        return urlBuilder.toString();
    }

    /**
     * 获取请求头
     * @param body
     * @param paulApiClient
     * @return
     */
    private Map<String,String> getHeaders(String body,PaulApiClient paulApiClient)  {
        Map<String,String> hashMap = new HashMap<>(4);
        hashMap.put("accessKey", paulApiClient.getAccessKey());
        String encodedBody = SecureUtil.md5(body);
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.getSign(encodedBody, paulApiClient.getSecretKey()));
        return hashMap;
    }

    @Override
    public <O, T extends ResultResponse> T request(BaseRequest<O, T> request) throws ApiException {
        try {
            return res(request);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
    }

    @Override
    public <O, T extends ResultResponse> T request(PaulApiClient paulApiClient, BaseRequest<O, T> request) throws ApiException {
        checkConfig(paulApiClient);
        return request(request);
    }


}
