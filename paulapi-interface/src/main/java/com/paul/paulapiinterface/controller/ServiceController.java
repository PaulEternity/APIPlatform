package com.paul.paulapiinterface.controller;


import cn.hutool.json.JSONUtil;
import com.paul.paulapiclientsdk.exception.ApiException;
import com.paul.paulapiclientsdk.model.params.*;
import com.paul.paulapiclientsdk.model.response.NameResponse;
import com.paul.paulapiclientsdk.model.response.RandomWallpaperResponse;
import com.paul.paulapiclientsdk.model.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.paul.paulapiinterface.utils.RequestUtils.buildUrl;
import static com.paul.paulapiinterface.utils.RequestUtils.get;
import static com.paul.paulapiinterface.utils.ResponseUtils.baseResponse;
import static com.paul.paulapiinterface.utils.ResponseUtils.responseToMap;

@RestController
@RequestMapping("/")
@Slf4j
public class ServiceController {

    @GetMapping("/name")
    public NameResponse getName(NameParams nameParams){
        return JSONUtil.toBean(JSONUtil.toJsonStr(nameParams), NameResponse.class);
    }

    @GetMapping("/loveTalk")
    public String randomTalk(){
        return get("https://api.vvhan.com/api/love");
    }

    @GetMapping("poisonousChickenSoup")
    public String poisonousChickenSoup(){
        return get("https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json");
    }

    @PostMapping("/randomWallpaper")
    public RandomWallpaperResponse randomWallpaper(@RequestBody RandomWallpaperParams randomWallpaperParams) throws ApiException {
        String baseUrl = "https://api.btstu.cn/sjbz/api.php";
        String url = buildUrl(baseUrl, randomWallpaperParams);
        if (StringUtils.isAllBlank(randomWallpaperParams.getLx(), randomWallpaperParams.getMethod())) {
            url = url + "?format=json";
        } else {
            url = url + "&format=json";
        }
        return JSONUtil.toBean(get(url), RandomWallpaperResponse.class);

    }

    @GetMapping("/horoscope")
    public ResultResponse getHoroscope(HoroscopeParams horoscopeParams) throws ApiException {
        String response = get("https://api.vvhan.com/api/horoscope", horoscopeParams);
        Map<String, Object> fromResponse = responseToMap(response);
        boolean success = (boolean) fromResponse.get("success");
        if (!success) {
            ResultResponse baseResponse = new ResultResponse();
            baseResponse.setData(fromResponse);
            return baseResponse;
        }
        return JSONUtil.toBean(response, ResultResponse.class);
    }

    @GetMapping("/ipInfo")
    public ResultResponse getIpInfo(IpInfoParams ipInfoParams) {
        return baseResponse("https://api.vvhan.com/api/getIpInfo", ipInfoParams);
    }

    @GetMapping("/weather")
    public ResultResponse getWeatherInfo(WeatherParams weatherParams) {
        return baseResponse("https://api.vvhan.com/api/weather", weatherParams);
    }



}
