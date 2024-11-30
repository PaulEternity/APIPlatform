package com.paul.paulapiclientsdk.Service;

import com.paul.paulapiclientsdk.client.PaulApiClient;
import com.paul.paulapiclientsdk.exception.ApiException;
import com.paul.paulapiclientsdk.model.request.BaseRequest;
import com.paul.paulapiclientsdk.model.response.PoisonousChickenSoupResponse;
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
     * 客户端使用
     * @param paulApiClient
     * @return
     * @throws ApiException
     */
    PoisonousChickenSoupResponse getPoisonousChickenSoup(PaulApiClient paulApiClient) throws ApiException;
}
