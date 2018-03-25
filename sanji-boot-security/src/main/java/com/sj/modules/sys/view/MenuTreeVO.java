package com.sj.modules.sys.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.sj.common.GeneralViews;
import com.sj.modules.sys.domain.Menu;
import lombok.Data;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Created by yangrd on 2017/8/3.
 */
@JsonInclude(NON_NULL)
@Data
public class MenuTreeVO implements Comparable<MenuTreeVO> {

    public interface View{
         interface Base extends GeneralViews.NormalView{ }
         interface ShowChildren extends Base{ }
         interface ShowParent extends Base{ }
    }

    @JsonView(View.Base.class)
    private Long id;

    @JsonView(View.Base.class)
    private String name;

    @JsonView(View.Base.class)
    private String icon;

    @JsonView(View.Base.class)
    private String description;

    @JsonView(View.Base.class)
    private String url;

    @JsonView(View.Base.class)
    private Long sort;

    @JsonView(View.Base.class)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Menu.Type depth;//菜单类别对应的深度

    @JsonView(View.Base.class)
    private String permission;//许可用于认证标识

    @JsonView(View.Base.class)
    private String skin;//皮肤 查看skins.css对应的样式

    @JsonView(View.ShowChildren.class)
    private Set<MenuTreeVO> children;

    @JsonView(View.ShowParent.class)
    private MenuTreeVO parent;

    public void addChildren(MenuTreeVO menuTreeVO) {
        if (Objects.isNull(this.children)) {
            synchronized (this) {
                if (Objects.isNull(this.children)) {
                    children = new TreeSet<>();
                }
            }
        }
        this.children.add(menuTreeVO);
    }

    public Long getParentId() {
        return Objects.nonNull(parent) ? parent.getId() : -1L;
    }

    @Override
    public int compareTo(MenuTreeVO o) {
        return (int) (Objects.nonNull(getSort()) ? getSort() - o.getSort() : getId() - o.getId());
    }
}
