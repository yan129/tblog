package com.xiong.tblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiong
 * @since 2020-05-09
 */
@Data
@TableName("tb_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 请求路径规则
     */
    private String url;

    /**
     * 路由path
     */
    private String path;

    /**
     * 对应前端组件名称
     */
    private String component;

    /**
     * 组件名
     */
    private String name;

    /**
     * 菜单图标
     */
    @TableField("iconCls")
    private String iconCls;

    /**
     * 菜单切换时是否保活
     */
    @TableField("keepAlive")
    private Boolean keepAlive;

    /**
     * 是否登录后才能访问
     */
    @TableField("requireAuth")
    private Boolean requireAuth;

    /**
     * 父菜单ID
     */
    @TableField("parentId")
    private Long parentId;

    /**
     * 是否可用
     */
    private Boolean enabled;

    private List<Role> roles;
}
