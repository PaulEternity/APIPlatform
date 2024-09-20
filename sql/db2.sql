# 用户调用接口次数
create table user_interface_info
(
    id              bigint auto_increment comment '主键'
        primary key,
    userId          bigint                             not null comment '调用用户ID',
    interfaceInfoId bigint                             not null comment '被调用接口ID',
    totalNum        int                                not null comment '总调用次数',
    leftNum         int                                not null comment '剩余调用次数',
    status          int      default 0                 not null comment '接口状态0:关闭,1:开启',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment 'apiplatform.`interface_info`';
