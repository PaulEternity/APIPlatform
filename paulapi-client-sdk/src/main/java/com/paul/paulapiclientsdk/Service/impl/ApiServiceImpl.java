package com.paul.paulapiclientsdk.Service.impl;

import com.paul.paulapiclientsdk.Service.ApiService;
import com.paul.paulapiclientsdk.Service.BaseService;
import com.paul.paulapiclientsdk.client.PaulApiClient;
import com.paul.paulapiclientsdk.exception.ApiException;
import com.paul.paulapiclientsdk.model.request.PoisonousChickenSoupRequest;
import com.paul.paulapiclientsdk.model.response.PoisonousChickenSoupResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiServiceImpl extends BaseService implements ApiService {

    @Override
    public PoisonousChickenSoupResponse getPoisonousChickenSoup() throws ApiException {
        PoisonousChickenSoupRequest request = new PoisonousChickenSoupRequest();
        return request(request);
    }

    @Override
    public PoisonousChickenSoupResponse getPoisonousChickenSoup(PaulApiClient paulApiClient) throws ApiException {
        PoisonousChickenSoupRequest request = new PoisonousChickenSoupRequest();
        return request(paulApiClient,request);
    }
}