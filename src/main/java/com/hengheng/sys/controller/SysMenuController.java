package com.hengheng.sys.controller;


import com.hengheng.common.base.Result;
import com.hengheng.common.base.SecurityUser;
import com.hengheng.common.base.UserDetail;
import com.hengheng.sys.pojo.vo.SysMenuVO;
import com.hengheng.sys.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lyw
 * @version 1.0
 */
@RestController
@RequestMapping("menu")
@Tag(name="菜单管理")
@AllArgsConstructor
public class SysMenuController {
    private final SysMenuService dataService;

    @GetMapping("get-menus")
    @Operation(summary = "菜单")
    public Result<List<SysMenuVO>> getMenus(){
        UserDetail user = SecurityUser.getUser();
        List<SysMenuVO> list = dataService.getUserMenuList(user);
        return Result.ok(list);
    }

    @PostMapping
    @Operation(summary = "保存数据")
    public Result<Boolean> save(@RequestBody @Valid SysMenuVO vo){
        return Result.ok(dataService.save(vo));
    }

    @PutMapping
    @Operation(summary = "更新数据")
    public Result<Boolean> update(@RequestBody @Valid SysMenuVO vo){
        return Result.ok(dataService.update(vo));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    public Result<Boolean> delete(@PathVariable("id") Long id){
        return Result.ok(dataService.delete(id));
    }
}
