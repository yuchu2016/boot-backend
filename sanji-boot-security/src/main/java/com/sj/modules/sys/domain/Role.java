package com.sj.modules.sys.domain;

import com.sj.common.base.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by sunxyz on 2017/3/13.
 */

@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity<Role> {

    private String name;//给用户看

    private String description;//描述

    private String roleType;//权限类型

    @ManyToMany
    @JoinTable(name = "sys_role_menu", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private Set<Menu> menuSet;

    {
        roleType = "";
    }
}
