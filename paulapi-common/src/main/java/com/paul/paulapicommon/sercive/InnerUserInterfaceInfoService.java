package com.paul.paulapicommon.sercive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paul.paulapicommon.model.entity.InterfaceInfo;
import com.paul.paulapicommon.model.entity.User;
import com.paul.paulapicommon.model.entity.UserInterfaceInfo;


/**
* @author 30420
* @description 针对表【user_interface_info(apiplatform.`interface_info`)】的数据库操作Service
* @createDate 2024-09-20 14:02:00
*/
public interface InnerUserInterfaceInfoService {

    boolean invokeCount(long interfaceInfoId, long userId);

}
