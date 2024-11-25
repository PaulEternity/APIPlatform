package com.paul.paulapicommon.sercive;

public interface InnerUserInterfaceInvokeService {

    /**
     * 接口调用
     * @param interfaceInfoId 接口id
     * @param userId 用户id
     * @param reduceScore 减少积分
     * @return
     */
    boolean invoke(Long interfaceInfoId, Long userId,Integer reduceScore);
}
