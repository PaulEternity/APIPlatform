package com.paul.paulapiclientsdk.model.request;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paul.paulapiclientsdk.model.response.ResultResponse;
import com.paul.paulapicommon.common.BaseResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRequest<O,T extends ResultResponse> {
    private Map<String,Object> requestParams = new HashMap<>();

    /**
     * get方法
     * @return
     */
    public abstract String getMethod();

    /**
     * 获取路径
     * @return
     */
    public abstract String getPath();

    /**
     * 获取响应类
     * @return
     */
    public abstract Class<T> getResponseClass();

    @JsonAnyGetter
    public Map<String, Object> getRequestParams() {return requestParams;}

    public void setRequestParams(O params){
        this.requestParams = new Gson().fromJson(JSONUtil.toJsonStr(params),new TypeToken<Map<String,Object>>(){
        }.getType());
    }
}
