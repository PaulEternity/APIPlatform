package com.paul.paulapiclientsdk.Service;

import com.paul.paulapiclientsdk.client.PaulApiClient;
import com.paul.paulapiclientsdk.exception.ApiException;
import com.paul.paulapiclientsdk.model.request.*;
import com.paul.paulapiclientsdk.model.response.LoveResponse;
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

    /**
     * 随机情话
     *
     * @return
     * @throws ApiException
     */
    LoveResponse randomLoveTalk() throws ApiException;

    LoveResponse randomLoveTalk(PaulApiClient paulApiClient) throws ApiException;

    /**
     * 天气信息
     * @param paulApiClient
     * @param request
     * @return
     * @throws ApiException
     */
    ResultResponse getWeatherInfo(PaulApiClient paulApiClient, WeatherRequest request) throws ApiException;

    ResultResponse getWeatherInfo(WeatherRequest request) throws ApiException;

    /**
     * 星座运势
     * @param request
     * @return
     * @throws ApiException
     */
    ResultResponse getHoroscope(HoroscopeRequest request) throws ApiException;

    ResultResponse getHoroscope(HoroscopeRequest request, PaulApiClient paulApiClient) throws ApiException;


    /**
     * 获取Ip地址
     * @param paulApiClient
     * @param ipInfoRequest
     * @return
     * @throws ApiException
     */
    ResultResponse getIpInfo(PaulApiClient paulApiClient,IpInfoRequest ipInfoRequest) throws ApiException;

    ResultResponse getIpInfo(IpInfoRequest ipInfoRequest) throws ApiException;


}
