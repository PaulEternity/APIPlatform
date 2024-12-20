package com.paul.paulapiclientsdk.Service.impl;

import com.paul.paulapiclientsdk.Service.ApiService;
import com.paul.paulapiclientsdk.Service.BaseService;
import com.paul.paulapiclientsdk.client.PaulApiClient;
import com.paul.paulapiclientsdk.exception.ApiException;
import com.paul.paulapiclientsdk.model.request.*;
import com.paul.paulapiclientsdk.model.response.LoveResponse;
import com.paul.paulapiclientsdk.model.response.PoisonousChickenSoupResponse;
import com.paul.paulapiclientsdk.model.response.RandomWallpaperResponse;
import com.paul.paulapiclientsdk.model.response.ResultResponse;
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

    @Override
    public RandomWallpaperResponse getRandomWallpaper(RandomWallpaperRequest request) throws ApiException {
        return request(request);
    }

    @Override
    public RandomWallpaperResponse getRandomWallpaper(PaulApiClient paulApiClient, RandomWallpaperRequest request) throws ApiException {
        return request(paulApiClient,request);
    }

    @Override
    public LoveResponse randomLoveTalk() throws ApiException {
        LoveRequest request = new LoveRequest();
        return request(request);
    }

    @Override
    public LoveResponse randomLoveTalk(PaulApiClient paulApiClient) throws ApiException {
        LoveRequest request = new LoveRequest();
        return request(paulApiClient,request);
    }

    @Override
    public ResultResponse getWeatherInfo(PaulApiClient paulApiClient, WeatherRequest request) throws ApiException {
        return request(paulApiClient,request);
    }

    @Override
    public ResultResponse getWeatherInfo(WeatherRequest request) throws ApiException {
        return request(request);
    }

    @Override
    public ResultResponse getHoroscope(HoroscopeRequest request) throws ApiException {
        return request(request);
    }

    @Override
    public ResultResponse getHoroscope(HoroscopeRequest request, PaulApiClient paulApiClient) throws ApiException {
        return request(paulApiClient,request);
    }

    @Override
    public ResultResponse getIpInfo(PaulApiClient paulApiClient, IpInfoRequest ipInfoRequest) throws ApiException {
        return request(paulApiClient,ipInfoRequest);
    }

    @Override
    public ResultResponse getIpInfo(IpInfoRequest ipInfoRequest) throws ApiException {
        return request(ipInfoRequest);
    }


}
