package com.xiong.tblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiong
 * @since 2020-05-09
 */
@Data
@TableName("tb_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("roleName")
    private String roleName;

    /**
     * 角色中文名称
     */
    @TableField("roleNameZh")
    private String roleNameZh;

    /**
     * 逻辑删除
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private Date created;

}
