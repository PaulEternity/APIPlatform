package com.paul.paulapiclientsdk.model.request;

import com.paul.paulapiclientsdk.model.enums.RequestMethodEnum;
import com.paul.paulapiclientsdk.model.response.LoveResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class LoveRequest extends BaseRequest<String, LoveResponse> {

    @Override
    public String getPath(){
        return "/loveTalk";
    }

    @Override
    public Class<LoveResponse> getResponseClass(){
        return LoveResponse.class;
    }

    @Override
    public String getMethod(){
        return RequestMethodEnum.GET.getValue();
    }
}
