package com.hengheng.sys.controller;


import cn.hutool.json.JSONUtil;
import com.hengheng.common.base.MyPageResult;
import com.hengheng.common.base.Result;
import com.hengheng.sys.pojo.query.BaseQuery;
import com.hengheng.sys.pojo.vo.SysRoleVO;
import com.hengheng.sys.service.SysRoleService;
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
@RequestMapping("role")
@Tag(name="角色管理")
@AllArgsConstructor
public class SysRoleController {
    private final SysRoleService dataService;

    @PostMapping("page")
    @Operation(summary = "根据条件获取分页数据")
    public Result<MyPageResult<SysRoleVO>> page(@RequestBody BaseQuery query){
        return Result.ok(dataService.getPage(query));
    }

    @PostMapping
    @Operation(summary = "保存数据")
    public Result<Boolean> save(@RequestBody @Valid SysRoleVO vo){
        return Result.ok(dataService.save(vo));
    }

    @PutMapping
    @Operation(summary = "更新数据")
    public Result<Boolean> update(@RequestBody @Valid SysRoleVO vo){
        return Result.ok(dataService.update(vo));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "根据ID删除数据")
    public Result<Boolean> delete(@PathVariable("id") Long id){
        return Result.ok(dataService.delete(id));
    }

    @GetMapping("all")
    @Operation(summary = "获取全部数据")
    public Result<List<SysRoleVO>> all(){
        return Result.ok(dataService.getAll());
    }
}
