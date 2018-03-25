package com.sj.modules.sys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sj.common.base.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sunxyz on 2017/3/13.
 * 只提供登录功能
 */
@Setter
@Getter
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "password", "news", "roleSet"})
@Entity
@Table(name = "sys_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity<User> {

    @NotEmpty(message = "用户名不能为空")
    @Column(unique = true, updatable = false)
    private String loginName;

    @NotEmpty(message = "密码不能为空")
    @Column(nullable = false)
    private String password;

    @Enumerated
    private UserStatus status;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roleSet;

    {
        status = UserStatus.NORMAL;
    }

    public boolean isEnabled() {
        return !status.equals(UserStatus.FROZEN);
    }

    public List<SimpleGrantedAuthority> listAuthorities(){
        return getRoleSet().stream().map(Role::getRoleType).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public enum UserStatus {
        NORMAL,//正常
        ABNORMAL,//异常
        FROZEN//冻结
    }
}
