package com.paul.paulapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息
 * apiplatform.`interface_info`
 *
 * @TableName interface_info
 */
@TableName(value = "interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 返回格式
     */
    private String returnFormat;

    /**
     * 用户名
     */
    private String name;

    /**
     * 请求方法
     */
    private String method;
    /**
     * 总调用次数
     */
    private Long totalInvokes;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求示例
     */
    private String requestExample;

    /**
     * 地址
     */
    private String url;

    /**
     * 减少积分个数
     */
    private Integer reduceScore;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 接口状态0:关闭,1:开启
     */
    private Integer status;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 接口请求参数
     */
    private String requestParams;
    /**
     * 接口响应参数
     */
    private String responseParams;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}