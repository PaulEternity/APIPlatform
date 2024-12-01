package com.paul.paulapiclientsdk.model.request;

import com.paul.paulapiclientsdk.model.enums.RequestMethodEnum;
import com.paul.paulapiclientsdk.model.params.WeatherParams;
import com.paul.paulapiclientsdk.model.response.ResultResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class WeatherRequest extends BaseRequest<WeatherParams, ResultResponse> {

    @Override
    public String getPath() {
        return "/weather";
    }

    @Override
    public Class<ResultResponse> getResponseClass() {
        return ResultResponse.class;
    }

    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
