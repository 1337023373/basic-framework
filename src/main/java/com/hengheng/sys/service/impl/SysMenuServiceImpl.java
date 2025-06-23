package com.hengheng.sys.service.impl;

import com.hengheng.common.base.UserDetail;
import com.hengheng.common.exception.ServerException;
import com.hengheng.common.utils.DozerUtils;
import com.hengheng.common.utils.TreeUtils;
import com.hengheng.sys.pojo.entity.SysMenuModel;
import com.hengheng.sys.pojo.entity.SysRoleMenuModel;
import com.hengheng.sys.pojo.vo.SysMenuVO;
import com.hengheng.sys.repository.SysMenuRepository;
import com.hengheng.sys.repository.SysRoleMenuRepository;
import com.hengheng.sys.service.SysMenuService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lkj
 * @Date 2025/6/19 11:20
 * @Version 1.0
 */
@Service

public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuRepository sysMenuRepository;
    @Resource
    private SysRoleMenuRepository sysRoleMenuRepository;

    @Override
    public List<SysMenuVO> getUserMenuList(UserDetail user) {
        //查询所有菜单
        List<SysMenuModel> menuModelList = sysMenuRepository.lambdaQuery().orderByAsc(SysMenuModel::getSort).list();
        //根据权限分配所属菜单
        if (user.getSuperAdmin().equals(1)) {
            //属于超级管理员就全部展示菜单
            List<SysMenuVO> list = DozerUtils.mapList(menuModelList, SysMenuVO.class);
            return TreeUtils.build(list);
        }else {
            List<SysMenuModel> list = new ArrayList<>();
            List<SysMenuModel> userModels = sysMenuRepository.getListByUserId(user.getId());
            if (!userModels.isEmpty()) {
                Map<Long, SysMenuModel> allMaps = menuModelList.stream().collect(Collectors.toMap(SysMenuModel::getId, SysMenuModel -> SysMenuModel));
                Map<Long,SysMenuModel> userMaps = new HashMap<>();
                userModels.forEach(o -> {
                    getParent(allMaps, userMaps, o);
                });
                list = new ArrayList<>(userMaps.values());
            }
            return TreeUtils.build(DozerUtils.mapList(list, SysMenuVO.class));
        }
    }

    /**
     * @description 查询所有父级信息
     * @param
     * @author  lkj
     * @date  2025/6/19
     * @return
     */
    private void getParent(Map<Long, SysMenuModel> allMaps, Map<Long, SysMenuModel> userMaps, SysMenuModel model) {
        if (!userMaps.containsKey(model.id)){
            userMaps.put(model.id, model);
        }
        if(model.getPid() > 0 && allMaps.containsKey(model.getPid())){
            getParent(allMaps, userMaps, allMaps.get(model.getPid()));
        }
    }

    @Override
    public boolean save(SysMenuVO vo) {
        SysMenuModel model = DozerUtils.map(vo, SysMenuModel.class);
        return sysMenuRepository.save(model);
    }

    @Override
    public boolean update(SysMenuVO vo) {
        // 上级菜单不能为自己
        if(vo.getId().equals(vo.getPid())){
            throw new ServerException("上级菜单不能为自己");
        }
        SysMenuModel model = DozerUtils.map(vo, SysMenuModel.class);
        return sysMenuRepository.updateById(model);
    }

    @Override
    public boolean delete(Long id) {
        //删除菜单
        //先查询该id下是否存在子菜单
        Long count = sysMenuRepository.getCountByPid(id);
        if (count > 0) {
            throw new ServerException("请先删除子菜单");
        }
        sysMenuRepository.removeById(id);
        //删除角色菜单
        return sysRoleMenuRepository.removeById(id);
    }
}
