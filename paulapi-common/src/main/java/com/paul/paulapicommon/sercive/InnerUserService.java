package com.paul.paulapicommon.sercive;


import com.baomidou.mybatisplus.extension.service.IService;
import com.paul.paulapicommon.model.entity.User;
import com.paul.paulapicommon.model.vo.UserVO;


/**
 * 用户服务
 *
 * @author paul
 */
public interface InnerUserService {
    UserVO getInvokeUser(String accessKey);


}
