package com.hengheng.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hengheng.common.base.MyPageResult;
import com.hengheng.common.utils.DozerUtils;
import com.hengheng.sys.pojo.entity.SysRoleMenuModel;
import com.hengheng.sys.pojo.entity.SysRoleModel;
import com.hengheng.sys.pojo.query.BaseQuery;
import com.hengheng.sys.pojo.vo.SysRoleVO;
import com.hengheng.sys.repository.SysRoleMenuRepository;
import com.hengheng.sys.repository.SysRoleRepository;
import com.hengheng.sys.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lkj
 * @Date 2025/6/19 11:21
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleRepository sysRoleRepository;
    @Resource
    private SysRoleMenuRepository sysRoleMenuRepository;

    @Override
    public MyPageResult<SysRoleVO> getPage(BaseQuery query) {
        MyPageResult<SysRoleModel> result = sysRoleRepository.getPage(query);
        return new MyPageResult<>(setMenuIds(result.getList()), result.getTotal());
    }

    private List<SysRoleVO> setMenuIds(List<SysRoleModel> models) {
        List<SysRoleVO> list = new ArrayList<>();
        if (!models.isEmpty()) {
            List<Long> roleIds = models.stream().map(SysRoleModel::getId).collect(Collectors.toList());
            //获取角色对应的菜单集合
            Map<Long, List<Long>> roleMenuMaps = sysRoleMenuRepository.getByRoleIds(roleIds);
            list = DozerUtils.mapList(models, SysRoleVO.class);
            //list = convertToVOList(models);
            for (SysRoleVO vo : list) {
                if(roleMenuMaps.containsKey(vo.getId())){
                    vo.setMenuIdList(roleMenuMaps.get(vo.getId()));
                }
            }
        }
        return list;
    }

    @Override
    public boolean save(SysRoleVO vo) {
        SysRoleModel model = DozerUtils.map(vo, SysRoleModel.class);
        //保存role表数据
        boolean res = sysRoleRepository.save(model);
        //保存roleMenu
        List<SysRoleMenuModel> list = new ArrayList<>();
        //SysRoleMenuModel sysRoleMenuModel = new SysRoleMenuModel();
        vo.getMenuIdList().forEach(menuId -> {
            SysRoleMenuModel sysRoleMenuModel = new SysRoleMenuModel();
            sysRoleMenuModel.setMenuId(menuId);
            sysRoleMenuModel.setRoleId(model.getId());
            list.add(sysRoleMenuModel);
        });
        sysRoleMenuRepository.saveBatch(list);
        return res;
    }

    @Override
    public boolean update(SysRoleVO vo) {
        SysRoleModel model = DozerUtils.map(vo, SysRoleModel.class);
        //更新role表数据
        boolean res = sysRoleRepository.updateById(model);
        //更新roleMenu
        sysRoleMenuRepository.saveOrUpdate(vo.getId(),vo.getMenuIdList());
        return res;
    }

    @Override
    public boolean delete(Long id) {
        boolean res = sysRoleRepository.removeById(id);
        //删除角色对应菜单
        sysRoleMenuRepository.remove(new LambdaQueryWrapper<SysRoleMenuModel>().eq(SysRoleMenuModel::getRoleId, id));
        return res;
    }

    @Override
    public List<SysRoleVO> getAll() {
        List<SysRoleModel> roleModels = sysRoleRepository.list();
        List<SysRoleVO> roleVOS = DozerUtils.mapList(roleModels, SysRoleVO.class);
        Map<Long, List<Long>> roleIdMaps = sysRoleMenuRepository.getByRoleIds(roleModels.stream().map(SysRoleModel::getId).collect(Collectors.toList()));
        roleVOS.forEach(roleVO -> {
            roleVO.setMenuIdList(roleIdMaps.get(roleVO.getId()));
        });
        return roleVOS;
    }
}

