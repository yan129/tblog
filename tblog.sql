create database if not exists `tblog` default character set utf8mb4 collate utf8mb4_general_ci;
use `tblog`;

-- 用户表
create table `tb_user`(
  `id` bigint(20) not null auto_increment comment 'userID',
  `nickname` varchar(16) default null comment '昵称',
  `username` varchar(11) not null comment '用户名',
  `password` varchar(255) not null comment '密码',
  `avatar` varchar(255) default null comment '头像',
  `remark` varchar(30) default null comment '个性签名',
  `gender` tinyint(1) default '1' comment '性别,默认1为男',
  `enabled` tinyint(1) default '1' comment '账户是否可用',
  `status` tinyint(1) default '1' comment '逻辑删除',
  `created` timestamp not null comment '创建时间',
  primary key (`id`)
)engine=innoDB default charset=utf8;

-- 角色表
create table `tb_role`(
  `id` bigint(20) not null auto_increment,
  `roleName` varchar(32) default null,
  `roleNameZh` varchar(32) default null comment '角色中文名称',
  `status` tinyint(1) default '1' comment '逻辑删除',
  `created` timestamp not null comment '创建时间',
  primary key (`id`)
)engine=innoDB default charset=utf8;

-- 菜单表
CREATE TABLE `tb_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(64) DEFAULT NULL comment '请求路径规则',
  `path` varchar(64) DEFAULT NULL comment '路由path',
  `component` varchar(64) DEFAULT NULL comment '对应前端组件名称',
  `name` varchar(64) DEFAULT NULL comment '组件名',
  `iconCls` varchar(64) DEFAULT NULL comment '菜单图标',
  `keepAlive` tinyint(1) DEFAULT NULL comment '菜单切换时是否保活',
  `requireAuth` tinyint(1) DEFAULT NULL comment '是否登录后才能访问',
  `parentId` bigint(20) DEFAULT NULL comment '父菜单ID',
  `enabled` tinyint(1) DEFAULT '1' comment '是否可用',
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentId`),
  CONSTRAINT `menu_fk_1` FOREIGN KEY (`parentId`) REFERENCES `tb_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户角色关联表
create table `tb_user_role`(
  `id` bigint(20) not null auto_increment,
  `uid` bigint(20) not null,
  `rid` bigint(20) not null,
  primary key (`id`),
  key `rid` (`rid`),
  key `user_role_ibfk_1` (`uid`),
  CONSTRAINT `user_role_fk_1` FOREIGN KEY (`uid`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_role_fk_2` FOREIGN KEY (`rid`) REFERENCES `tb_role` (`id`)
)engine=innoDB default charset=utf8;

-- 菜单角色关联表
CREATE TABLE `tb_menu_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` bigint(20) DEFAULT NULL,
  `rid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mid` (`mid`),
  KEY `rid` (`rid`),
  CONSTRAINT `menu_role_fk_1` FOREIGN KEY (`mid`) REFERENCES `tb_menu` (`id`),
  CONSTRAINT `menu_role_fk_2` FOREIGN KEY (`rid`) REFERENCES `tb_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 博客表
create table `tb_blog`(
    `id` bigint(20) not null auto_increment comment 'blogID',
    `title` varchar(50) not null comment '标题',
    `summary` varchar(255) default null comment '摘要',
    `content` text not null comment '内容',
    `views` bigint(20) default 0 comment '浏览次数',
    `likes` bigint(20) default 0 comment '点赞量',
    `stamp` bigint(20) default 0 comment '踩',
    `appreciation` tinyint(1) default '0' comment '赞赏开启',
    `comment` tinyint(1) default '1' comment '评论开启',
    `publish` tinyint(1) default '1' comment '默认发布，否则存入草稿箱',
    `top` tinyint(1) default '0' comment '是否置顶',
    `is_deleted` tinyint(1) default '0' comment '逻辑删除 1（true）已删除， 0（false）未删除',
    `created` timestamp not null comment '创建时间',
    `modified` timestamp null comment '更新时间',
    primary key (`id`)
)engine=innoDB default charset=utf8;

-- 分类
create table `tb_type`(
    `id` bigint(20) not null auto_increment comment 'typeID',
    `typeName` varchar(32) not null comment '分类名',
    `is_deleted` tinyint(1) default '0' comment '逻辑删除',
    primary key (`id`)
)engine=innoDB default charset=utf8;

-- 标签
create table `tb_tag`(
    `id` bigint(20) not null auto_increment comment 'tagID',
    `tagName` varchar(32) not null comment '标签名',
    `is_deleted` tinyint(1) default '0' comment '逻辑删除',
    primary key (`id`)
)engine=innoDB default charset=utf8;

-- 博客分类关联表
create table `tb_blog_type`(
    `id` bigint(20) not null auto_increment,
    `bid` bigint(20) not null comment '博客ID',
    `tid` bigint(20) not null comment '分类ID',
    primary key (`id`),
    key `bid` (`bid`),
    key `tid` (`tid`),
    constraint `blog_type_fk_b` foreign key (`bid`) references `blog` (`id`),
    constraint `blog_type_fk_t` foreign key (`tid`) references `type` (`id`)
)engine=innoDB default charset=utf8;

-- 博客标签关联表
create table `tb_blog_tag`(
    `id` bigint(20) not null auto_increment,
    `bid` bigint(20) not null comment '博客ID',
    `tid` bigint(20) not null comment '标签ID',
    primary key (`id`),
    key `bid` (`bid`),
    key `tid` (`tid`),
    constraint `blog_tag_fk_b` foreign key (`bid`) references `blog` (`id`),
    constraint `blog_tag_fk_t` foreign key (`tid`) references `tag` (`id`)
)engine=innoDB default charset=utf8;

-- 用户博客关联表
create table `tb_user_blog`(
    `id` bigint(20) not null auto_increment,
    `uid` bigint(20) not null comment '用户ID',
    `bid` bigint(20) not null comment '博客ID',
    primary key (`id`),
    key `uid` (`uid`),
    key `bid` (`bid`),
    constraint `user_blog_fk_u` foreign key (`uid`) references `user` (`id`),
    constraint `user_blog_fk_b` foreign key (`bid`) references `blog` (`id`)
)engine=innoDB default charset=utf8;














