create table interface_info
(
    id             bigint auto_increment comment '主键'
        primary key,
    userId         bigint                             not null comment '创建人',
    name           varchar(256)                       not null comment '用户名',
    description    varchar(256)                       not null comment '描述',
    url            varchar(512)                       not null comment '地址',
    requestHeader  text                               null comment '请求头',
    responseHeader text                               null comment '响应头',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    status         int      default 0                 not null comment '接口状态0:关闭,1:开启',
    isDeleted      tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)',
    method         varchar(256)                       not null comment '请求类型'
)
    comment 'apiplatform.`interface_info`';
