package com.paul.apiPlatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paul.apiPlatform.common.ErrorCode;
import com.paul.apiPlatform.exception.BusinessException;
import com.paul.apiPlatform.mapper.InterfaceInfoMapper;
import com.paul.paulapicommon.model.entity.InterfaceInfo;
import com.paul.apiPlatform.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author 30420
 * @description 针对表【interface_info(apiplatform.`interface_info`)】的数据库操作Service实现
 * @createDate 2024-09-16 11:11:40
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();

        // 创建时，参数不能为空
        if(add){
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if(StringUtils.isNotBlank(name) && name.length() > 50){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(description) && description.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "描述过长");
        }

    }

}







