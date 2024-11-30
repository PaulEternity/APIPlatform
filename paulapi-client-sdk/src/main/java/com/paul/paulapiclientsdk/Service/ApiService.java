package com.paul.paulapiclientsdk.Service;

import com.paul.paulapiclientsdk.client.PaulApiClient;
import com.paul.paulapiclientsdk.exception.ApiException;
import com.paul.paulapiclientsdk.model.request.BaseRequest;
import com.paul.paulapiclientsdk.model.request.RandomWallpaperRequest;
import com.paul.paulapiclientsdk.model.response.PoisonousChickenSoupResponse;
import com.paul.paulapiclientsdk.model.response.RandomWallpaperResponse;
import com.paul.paulapiclientsdk.model.response.ResultResponse;

public interface ApiService {

    /**
     * 通用请求
     * @param request
     * @return
     * @param <O>
     * @param <T>
     * @throws ApiException
     */
    <O,T extends ResultResponse> T request(BaseRequest<O, T> request) throws ApiException;

    <O,T extends ResultResponse> T request(PaulApiClient paulApiClient, BaseRequest<O,T>request) throws ApiException;

    /**
     * 随机毒鸡汤
     *
     * @return
     * @throws ApiException
     */
    PoisonousChickenSoupResponse getPoisonousChickenSoup() throws ApiException;

    /**
     * 随机毒鸡汤客户端使用
     * @param paulApiClient
     * @return
     * @throws ApiException
     */
    PoisonousChickenSoupResponse getPoisonousChickenSoup(PaulApiClient paulApiClient) throws ApiException;

    /**
     * 随机壁纸
     * @param request
     * @return
     * @throws ApiException
     */
    RandomWallpaperResponse getRandomWallpaper(RandomWallpaperRequest request) throws ApiException;


    RandomWallpaperResponse getRandomWallpaper(PaulApiClient paulApiClient,RandomWallpaperRequest request) throws ApiException;

}
