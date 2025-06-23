package com.hengheng.sys.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.common.base.MyPageResult;
import com.hengheng.sys.mapper.SysUserMapper;
import com.hengheng.sys.pojo.entity.SysUserModel;
import com.hengheng.sys.pojo.query.BaseQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/19 11:14
 * @Version 1.0
 */
@Repository
public class SysUserRepository extends ServiceImpl<SysUserMapper, SysUserModel> {
    public SysUserModel getByUsername(String username) {
        return this.lambdaQuery().eq(SysUserModel::getUsername, username).one();
    }

    public MyPageResult<SysUserModel> getPage(BaseQuery<SysUserModel> query) {
            LambdaQueryWrapper<SysUserModel> wrapper = new LambdaQueryWrapper<>();
            if (!StringUtils.isBlank(query.getSearch())) {
                wrapper
                        .and(w -> w
                                .like(SysUserModel::getUsername, query.getSearch())
                                .or()
                                .like(SysUserModel::getRealName, query.getSearch()));
            }
        Page<SysUserModel> page = this.page(query.toPage(), wrapper);
        return query.toResult(page);
    }

    public List<SysUserModel> getList() {
        return this.lambdaQuery().list();
    }

    public boolean updatePassword(Long id, String encode) {
        return this.lambdaUpdate().eq(SysUserModel::getId, id).set(SysUserModel::getPassword, encode).update();
    }

    public SysUserModel getByMobile(String mobile) {
        return this.lambdaQuery().eq(SysUserModel::getMobile, mobile).one();
    }

    public Long getCountByOrgId(Long id) {
        return this.lambdaQuery().eq(SysUserModel::getOrgId, id).count();
    }
}
