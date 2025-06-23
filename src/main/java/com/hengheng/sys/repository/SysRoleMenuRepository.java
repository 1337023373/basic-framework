package com.hengheng.sys.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.sys.mapper.SysRoleMenuMapper;
import com.hengheng.sys.pojo.entity.SysRoleMenuModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lkj
 * @Date 2025/6/20 8:40
 * @Version 1.0
 */
@Repository
public class SysRoleMenuRepository extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuModel> {
    public Map<Long, List<Long>> getByRoleIds(List<Long> roleIds) {
        List<SysRoleMenuModel> roleMenuModels = this.lambdaQuery().in(SysRoleMenuModel::getRoleId, roleIds).list();
        return roleMenuModels.stream().collect(Collectors.groupingBy(SysRoleMenuModel::getRoleId, Collectors.mapping(SysRoleMenuModel::getMenuId, Collectors.toList())));
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        if (menuIdList == null) {
            menuIdList = new ArrayList<>();
        }
        //根据角色id获取角色菜单表中的菜单id
        List<Long> dbMenuIdList = getMenuIdByRoleId(roleId);
        // 需要新增的菜单ID
        Collection<Long> insertMenuIdList = CollUtil.subtract(menuIdList, dbMenuIdList);
        if (CollUtil.isNotEmpty(insertMenuIdList)) {
            List<SysRoleMenuModel> menuList = insertMenuIdList.stream().map(menuId -> {
                SysRoleMenuModel sysRoleMenuModel = new SysRoleMenuModel();
                sysRoleMenuModel.setRoleId(roleId);
                sysRoleMenuModel.setMenuId(menuId);
                return sysRoleMenuModel;
            }).collect(Collectors.toList());
            // 批量新增
            this.saveBatch(menuList);
        }
        //需要删除的菜单id
        Collection<Long> deleteMenuIdList = CollUtil.subtract(dbMenuIdList, menuIdList);
        if (CollUtil.isNotEmpty(deleteMenuIdList)) {
            this.remove(new LambdaQueryWrapper<SysRoleMenuModel>().eq(SysRoleMenuModel::getRoleId, roleId).in(SysRoleMenuModel::getMenuId, deleteMenuIdList));
        }
    }

    private List<Long> getMenuIdByRoleId(Long roleId) {
        return this.lambdaQuery().eq(SysRoleMenuModel::getRoleId, roleId).list().stream().map(SysRoleMenuModel::getMenuId).collect(Collectors.toList());
    }
}
