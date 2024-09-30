package com.paul.apiPlatform.service.impl.Inner;

import com.paul.paulapicommon.sercive.InnerUserInterfaceInfoService;
import com.paul.apiPlatform.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfo, long userId){
        return userInterfaceInfoService.invokeCount(interfaceInfo,userId);
    }
}
