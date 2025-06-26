package com.hengheng.sys.controller;

import cn.hutool.core.util.StrUtil;

import com.hengheng.common.base.MyPageResult;
import com.hengheng.common.base.Result;
import com.hengheng.common.base.SecurityUser;
import com.hengheng.common.base.UserDetail;
import com.hengheng.common.utils.DozerUtils;
import com.hengheng.common.utils.EasyExcelUtils;
import com.hengheng.sys.pojo.entity.SysUserModel;
import com.hengheng.sys.pojo.query.BaseQuery;
import com.hengheng.sys.pojo.vo.SysUserPasswordVO;
import com.hengheng.sys.pojo.vo.SysUserVO;
import com.hengheng.sys.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author lyw
 * @version 1.0
 */
@RestController
@RequestMapping("user")
@Tag(name = "用户管理")
@AllArgsConstructor
public class SysUserController {
    private final SysUserService dataService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("page")
    @Operation(summary = "根据条件获取分页数据")
    public Result<MyPageResult<SysUserVO>> page(@RequestBody BaseQuery<SysUserModel> query) {
        return Result.ok(dataService.getPage(query));
    }

    @GetMapping("list")
    @Operation(summary = "获取全部")
    public Result<List<SysUserVO>> getList() {
        return Result.ok(dataService.getList());
    }

    @PutMapping("password")
    @Operation(summary = "修改密码")
    public Result<Boolean> password(@RequestBody @Valid SysUserPasswordVO vo) {
        // 原密码不正确
        UserDetail user = SecurityUser.getUser();
        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return Result.ok(false);
        }
        return Result.ok(dataService.updatePassword(user.getId(), passwordEncoder.encode(vo.getNewPassword())));
    }

    @PostMapping
    @Operation(summary = "保存数据")
    public Result<Boolean> save(@RequestBody @Valid SysUserVO vo) {
        if (StrUtil.isBlank(vo.getPassword())) {
            vo.setPassword("123456");
        }
        //密码加密
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        return Result.ok(dataService.save(vo));
    }

    @PutMapping
    @Operation(summary = "更新数据")
    public Result<Boolean> update(@RequestBody @Valid SysUserVO vo) {
        return Result.ok(dataService.update(vo));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "根据ID删除数据")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        return Result.ok(dataService.delete(id));
    }

    @GetMapping("{id}")
    @Operation(summary = "根据Id获取数据")
    public Result<SysUserVO> getById(@PathVariable("id") Long id) {
        return Result.ok(dataService.getById(id));
    }

    @GetMapping("export")
    @Operation(summary = "导出全部")
    public void export(HttpServletResponse response) throws IOException {
        List<SysUserVO> list = dataService.getList();
        EasyExcelUtils.writeWeb("用户信息导出", SysUserVO.class,list,"用户列表",response);
    }

    @PostMapping("import")
    @Operation(summary = "导出全部")
    public Result<String> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<SysUserVO> voList = EasyExcelUtils.readExcelOneSheet(inputStream, SysUserVO.class);
        List<SysUserModel> sysUserModels = DozerUtils.mapList(voList, SysUserModel.class);
        return Result.ok("导入成功，共 " + sysUserModels.size() + " 条记录");
    }

}
