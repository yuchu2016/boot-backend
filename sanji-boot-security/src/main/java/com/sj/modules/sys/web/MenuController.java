package com.sj.modules.sys.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.sj.common.Result;
import com.sj.modules.sys.domain.Menu;
import com.sj.modules.sys.repository.MenuRepository;
import com.sj.modules.sys.repository.RoleRepository;
import com.sj.modules.sys.service.MenuTreeService;
import com.sj.modules.sys.view.MenuTreeVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.sj.common.ResultGenerator.error;
import static com.sj.common.ResultGenerator.ok;

/**
 * Created by yangrd on 2017/7/18.
 */
@RestController
@RequestMapping("/api/menu")
@AllArgsConstructor
public class MenuController {

    private MenuRepository repository;

    private MenuTreeService menuTreeService;

    private RoleRepository roleRepository;

    @PostMapping
    public Result<Menu> add(@RequestBody Menu menu) {
        Long pid = menu.getParent().getId();
        Menu parent = repository.findOne(pid);
        if (Objects.isNull(parent)) {
            return error("pid 不存在");
        }
        menu.setParent(parent);
        return ok(repository.save(menu));
    }

    @Transactional
    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        boolean nonDelete = idList.stream().noneMatch(id -> roleRepository.countByMenuSet_id(id) > 0);
        if (nonDelete) {
            return error("有权限正在使用该资源");
        } else {
            nonDelete = idList.stream().anyMatch(id -> repository.countByParent_Id(id) > 0);
            if (nonDelete) {
                return error("先删除子資源");
            } else {
                idList.forEach(repository::delete);
                return ok();
            }
        }
    }

    @Transactional
    @PutMapping("{id}")
    public Result<Menu> update(@PathVariable("id") Menu old, @RequestBody Menu self) {
        if (Objects.isNull(self)) {
            return error("id 不存在");
        }
        updateVal(old, self);
        return ok(repository.save(old));
    }

    @GetMapping
    @JsonView(MenuTreeVO.View.ShowChildren.class)
    public Result<Collection<MenuTreeVO>> listSystem() {
        return ok(menuTreeService.listSystem());
    }

    @GetMapping("/{id}")
    public Result<Menu> get(@PathVariable("id") Menu menu) {
        return ok(menu);
    }

    @GetMapping("{pid}/child")
    public Result<List<Menu>> findByParent(@PathVariable("pid") Menu parent) {
        if (Objects.isNull(parent)) {
            return error("pid 不存在");
        }
        return ok(repository.findByParentOrderBySortAsc(parent));
    }

    private void updateVal(Menu old, Menu self) {
        BeanUtils.copyProperties(self, old, "parent");
    }

}
