package com.sj.modules.sys.web;

import com.sj.common.Result;
import com.sj.modules.sys.domain.Role;
import com.sj.modules.sys.repository.RoleRepository;
import com.sj.modules.sys.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.sj.common.ResultGenerator.error;
import static com.sj.common.ResultGenerator.ok;

/**
 * Created by yangrd on 2017/7/17.
 */
@RestController
@RequestMapping(value = "/api/role")
@AllArgsConstructor
public class RoleController {

    private RoleRepository repository;

    private UserRepository userRepository;

    @Transactional
    @PostMapping
    public Result<String> add(@RequestBody Role role) {
        repository.save(role);
        return ok();
    }

    @Transactional
    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        boolean nonDelete = idList.stream().anyMatch(id -> userRepository.countByRoleSet_Id(id) > 0);
        if (nonDelete) {
            return error("有用户在使用该权限");
        }else{
            idList.forEach(repository::delete);
            return ok();
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable("id") Role old, @RequestBody Role role) {
        if (Objects.isNull(old)) {
            return error();
        }
        old.setName(role.getName());
        old.setRoleType(role.getRoleType());
        old.setDescription(role.getDescription());
        old.setMenuSet(role.getMenuSet());
        return ok();
    }

    @GetMapping("/{id}")
    public Result<Role> get(@PathVariable("id") Role role) {
        return ok(role);
    }


    @GetMapping
    public Result<Page<Role>> getAll(@RequestParam(defaultValue = "") String name, Pageable pageable) {
        return ok(repository.findByNameLike("%" + name + "%", pageable));
    }

}
