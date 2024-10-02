package com.paul.apiPlatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paul.paulapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 30420
* @description 针对表【user_interface_info(apiplatform.`interface_info`)】的数据库操作Mapper
* @createDate 2024-09-20 14:02:00
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<UserInterfaceInfo> listTopInterfaceInfo(int limit);
}




