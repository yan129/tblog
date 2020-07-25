package com.xiong.tblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.*;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@TableName("tb_user")
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * userID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    @Length(max = 8, message = "昵称长度不能大于8个字符！")
    private String nickname;

    /**
     * 用户名
     */
    @NotBlank(message = "手机号不能为空！")
    @Length(min = 11, max = 11, message = "请输入正确手机号！")
    @Pattern(regexp = "^[1][34578]\\d{9}$", message = "请输入正确手机号！")
    private String username;

    /**
     * 密码
     */
//    @NotBlank(message = "密码不能为空！")
    @Length(min = 6, max = 20, message = "密码长度介于6~20间")
    @JsonIgnore
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个性签名
     */
    private String remark;

    /**
     * 性别,默认1为男
     */
    private boolean gender;

    /**
     * 账户是否可用
     */
    private boolean enabled;

    /**
     * 逻辑删除
     */
    private boolean status;

    /**
     * 创建时间
     */
    private Date created;

    @JsonIgnore
    @TableField(exist = false)
    private List<Role> roles;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //获取当前用户对象所具有的角色信息
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
