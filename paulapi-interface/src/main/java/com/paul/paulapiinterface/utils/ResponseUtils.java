package com.paul.paulapiinterface.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paul.paulapiclientsdk.exception.ApiException;
import com.paul.paulapiclientsdk.model.response.ResultResponse;
import com.paul.paulapicommon.common.ErrorCode;
import com.paul.paulapicommon.exception.BusinessException;


import java.util.Map;

import static com.paul.paulapiinterface.utils.RequestUtils.get;


public class ResponseUtils {
    public static Map<String, Object> responseToMap(String response) {
        return new Gson().fromJson(response, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static <T> ResultResponse baseResponse(String baseUrl, T params) {
        String response = null;
        try {
            response = get(baseUrl,params);
            Map<String,Object> fromResponse = responseToMap(response);
            boolean success = (boolean) fromResponse.get("success");
            ResultResponse baseResponse = new ResultResponse();
            if (!success) {
                baseResponse.setData(fromResponse);
                return baseResponse;
            }
            fromResponse.remove("success");
            return baseResponse;
        }catch (ApiException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"构建url异常");
        }
    }

}
