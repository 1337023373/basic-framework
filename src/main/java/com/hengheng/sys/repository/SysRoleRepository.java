package com.hengheng.sys.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.common.base.MyPageResult;
import com.hengheng.sys.mapper.SysRoleMapper;
import com.hengheng.sys.pojo.entity.SysRoleModel;
import com.hengheng.sys.pojo.query.BaseQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/20 8:46
 * @Version 1.0
 */
@Repository
public class SysRoleRepository extends ServiceImpl<SysRoleMapper, SysRoleModel> {
    public MyPageResult<SysRoleModel> getPage(BaseQuery query) {
        LambdaQueryWrapper<SysRoleModel> wrapper = new LambdaQueryWrapper<>();
        if (query.getSearch() != null) {
            wrapper.like(SysRoleModel::getName, query.getSearch());
        }
        Page<SysRoleModel> page = this.page(query.toPage(), wrapper);
        return query.toResult(page);
    }
}
