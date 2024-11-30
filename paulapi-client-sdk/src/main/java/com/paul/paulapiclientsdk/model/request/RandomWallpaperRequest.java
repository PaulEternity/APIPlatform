package com.paul.paulapiclientsdk.model.request;

import com.paul.paulapiclientsdk.model.enums.RequestMethodEnum;
import com.paul.paulapiclientsdk.model.params.RandomWallpaperParams;
import com.paul.paulapiclientsdk.model.response.RandomWallpaperResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class RandomWallpaperRequest extends BaseRequest<RandomWallpaperParams, RandomWallpaperResponse> {
    @Override
    public String getPath(){
        return "/randomWallpaper";
    }

    @Override
    public Class<RandomWallpaperResponse> getResponseClass(){
        return RandomWallpaperResponse.class;
    }

    @Override
    public String getMethod(){
        return RequestMethodEnum.GET.getValue();
    }
}
