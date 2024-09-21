package com.paul.apiPlatform.model.dto.UserInterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author <a href="https://github.com/lipaul">paul</a>
 * @from <a href="https://paul.icu">编程导航知识星球</a>
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

    private Long id;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 接口状态0:关闭,1:开启
     */
    private Integer status;


    private static final long serialVersionUID = 1L;
}