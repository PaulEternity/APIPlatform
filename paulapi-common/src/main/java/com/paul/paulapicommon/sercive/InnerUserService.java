package com.paul.paulapicommon.sercive;


import com.baomidou.mybatisplus.extension.service.IService;
import com.paul.paulapicommon.model.entity.User;


/**
 * 用户服务
 *
 * @author paul
 */
public interface InnerUserService {
    User getInvokeUser(String accessKey);


}
