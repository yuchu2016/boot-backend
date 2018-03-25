package com.sj.modules.sys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sj.common.base.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * //前台菜单 最好做缓存处理
 * Created by sunxyz on 2017/3/14.
 */
@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity<Menu> {

    private String name;

    private String icon;

    private String description;

    private String url;

    private Long sort;

    @ManyToOne
    private Menu parent;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    @Enumerated
    private Type depth;//菜单类别对应的深度

    private String permission;//许可用于认证标识

    private String skin;//皮肤 查看skins.css对应的样式

    public enum Type {
        ROOT, SYSTEM, DIRECTORY, MENU
    }

}
