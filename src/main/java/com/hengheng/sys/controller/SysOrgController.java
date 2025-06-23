package com.hengheng.sys.controller;


import com.hengheng.common.base.Result;
import com.hengheng.sys.pojo.entity.SysOrgModel;
import com.hengheng.sys.pojo.vo.SysOrgVO;
import com.hengheng.sys.service.SysOrgService;
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
@RequestMapping("org")
@Tag(name="组织管理")
@AllArgsConstructor
public class SysOrgController {
    private final SysOrgService dataService;

    @GetMapping("get-list")
    @Operation(summary = "列表")
    public Result<List<SysOrgVO>> getList() {
        return Result.ok(dataService.getList());
    }

    @GetMapping("get-all")
    @Operation(summary = "所有数据")
    public Result<List<SysOrgModel>> getAll() {
        return Result.ok(dataService.getAll());
    }


    @PostMapping
    @Operation(summary = "保存数据")
    public Result<Boolean> save(@RequestBody @Valid SysOrgVO vo){
        return Result.ok(dataService.save(vo));
    }

    @PutMapping
    @Operation(summary = "更新数据")
    public Result<Boolean> update(@RequestBody @Valid SysOrgVO vo){
        return Result.ok(dataService.update(vo));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "根据ID删除数据")
    public Result<Boolean> delete(@PathVariable("id") Long id){
        return Result.ok(dataService.delete(id));
    }
}
