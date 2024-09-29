package com.paul.paulapicommon.sercive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paul.paulapicommon.model.entity.InterfaceInfo;


/**
* @author 30420
* @description 针对表【interface_info(apiplatform.`interface_info`)】的数据库操作Service
* @createDate 2024-09-16 11:11:40
*/
public interface InnerInterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

//    void getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request);

}
