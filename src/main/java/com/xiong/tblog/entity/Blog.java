package com.xiong.tblog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xiong
 * @since 2020-05-22
 */
@Data
@TableName("tb_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * blogID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 浏览次数
     */
    private Long views;

    /**
     * 点赞量
     */
    private Long likes;

    /**
     * 踩
     */
    private Long stamp;

    /**
     * 赞赏开启
     */
    private Boolean appreciation;

    /**
     * 评论开启
     */
    private Boolean comment;

    /**
     * 默认发布，否则存入草稿箱
     */
    private Boolean publish;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableLogic
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date created;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;

    @NotEmpty(message = "分类必须勾选")
    @TableField(exist = false)
    private List<Integer> checkedTypes;
    @NotEmpty(message = "标签必须勾选")
    @TableField(exist = false)
    private List<Integer> checkedTags;

}
