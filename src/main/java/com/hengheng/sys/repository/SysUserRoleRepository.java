package com.hengheng.sys.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.sys.mapper.SysUserRoleMapper;
import com.hengheng.sys.pojo.entity.SysUserRoleModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lkj
 * @Date 2025/6/19 14:11
 * @Version 1.0
 */
@Repository
public class SysUserRoleRepository extends ServiceImpl<SysUserRoleMapper, SysUserRoleModel> {
    public Map<Long, List<Long>> getByUserIds(List<Long> userIds) {
        List<SysUserRoleModel> list = this.lambdaQuery().in(SysUserRoleModel::getUserId, userIds).list();
        return list.stream().collect(Collectors.groupingBy(SysUserRoleModel::getUserId, Collectors.mapping(SysUserRoleModel::getRoleId, Collectors.toList())));
    }

    /**
     * @param
     * @return
     * @description 保存或更新用户角色信息
     * @author lkj
     * @date 2025/6/19
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateUserRole(Long id, List<Long> roleIdList) {
        if (roleIdList == null) {
            roleIdList = new ArrayList<>();
        }
        //获取角色id列表
        List<Long> roleIds = getRoleIdsByUserId(id);
        //跟参数的role比较，找到需要新增的角色id
        Collection<Long> insertRoleIdList = CollUtil.subtract(roleIdList, roleIds);
        if (CollUtil.isNotEmpty(insertRoleIdList)) {
            List<SysUserRoleModel> roleList = insertRoleIdList.stream().map(roleId -> {
                SysUserRoleModel sysUserRoleModel = new SysUserRoleModel();
                sysUserRoleModel.setUserId(id);
                sysUserRoleModel.setRoleId(roleId);
                return sysUserRoleModel;
            }).collect(Collectors.toList());
            this.saveBatch(roleList);
        }
    }

    private List<Long> getRoleIdsByUserId(Long id) {
        return this.lambdaQuery()
                .eq(SysUserRoleModel::getUserId, id)
                .select(SysUserRoleModel::getRoleId)
                .list()
                .stream()
                .map(SysUserRoleModel::getRoleId)
                .collect(Collectors.toList());
    }

    public void deleteByUserIdsAndRoleId(Long id) {
        removeById(id);
    }
}
