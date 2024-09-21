package com.paul.apiPlatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paul.apiPlatform.model.entity.UserInterfaceInfo;


/**
* @author 30420
* @description 针对表【user_interface_info(apiplatform.`interface_info`)】的数据库操作Service
* @createDate 2024-09-20 14:02:00
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    boolean invokeCount(long interfaceInfoId,long userId);

}
