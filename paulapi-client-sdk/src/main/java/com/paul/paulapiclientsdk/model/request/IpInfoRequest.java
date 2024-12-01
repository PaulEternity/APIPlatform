package com.paul.paulapiclientsdk.model.request;

import com.paul.paulapiclientsdk.model.enums.RequestMethodEnum;
import com.paul.paulapiclientsdk.model.response.ResultResponse;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;

@Accessors(chain = true)
public class IpInfoRequest extends BaseRequest<IpInfoRequest, ResultResponse> {

    @Override
    public String getPath(){
        return "/ipInfo";
    }

    @Override
    public Class<ResultResponse> getResponseClass(){
        return ResultResponse.class;
    }

    @Override
    public String getMethod(){
        return RequestMethodEnum.GET.getValue();
    }

}
