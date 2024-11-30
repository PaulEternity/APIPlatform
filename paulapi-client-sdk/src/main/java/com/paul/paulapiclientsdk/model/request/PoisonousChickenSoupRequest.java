package com.paul.paulapiclientsdk.model.request;


import com.paul.paulapiclientsdk.model.enums.RequestMethodEnum;
import com.paul.paulapiclientsdk.model.params.PoisonousChickenSoupParams;
import com.paul.paulapiclientsdk.model.response.PoisonousChickenSoupResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class PoisonousChickenSoupRequest extends BaseRequest<PoisonousChickenSoupParams,PoisonousChickenSoupResponse>{

    @Override
    public String getPath(){
        return "/PoisonousChickenSoup";
    }

    @Override
    public Class<PoisonousChickenSoupResponse> getResponseClass() {
        return PoisonousChickenSoupResponse.class;
    }

    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
