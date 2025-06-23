package com.hengheng.sys.service.impl;

import com.hengheng.common.base.MyPageResult;
import com.hengheng.common.constant.SuperAdminEnum;
import com.hengheng.common.exception.ServerException;
import com.hengheng.common.utils.DozerUtils;
import com.hengheng.sys.mapper.SysUserMapper;
import com.hengheng.sys.pojo.entity.SysOrgModel;
import com.hengheng.sys.pojo.entity.SysUserModel;
import com.hengheng.sys.pojo.entity.SysUserRoleModel;
import com.hengheng.sys.pojo.query.BaseQuery;
import com.hengheng.sys.pojo.vo.SysUserVO;
import com.hengheng.sys.repository.SysOrgRepository;
import com.hengheng.sys.repository.SysUserRepository;
import com.hengheng.sys.repository.SysUserRoleRepository;
import com.hengheng.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author lkj
 * @Date 2025/6/19 11:21
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private SysUserRoleRepository sysUserRoleRepository;
    @Resource
    private SysOrgRepository sysOrgRepository;

    @Override
    public MyPageResult<SysUserVO> getPage(BaseQuery<SysUserModel> query) {
        //sysUserRepository.getPage(query.getSearch(), query.getPage(), query.getLimit());
        MyPageResult<SysUserModel> result = sysUserRepository.getPage(query);
        return new MyPageResult<>(setRoleIds(result.getList()), result.getTotal());
    }

    /**
     * @param
     * @return
     * @description 设置员工相关信息
     * @author lkj
     * @date 2025/6/19
     */

    private List<SysUserVO> setRoleIds(List<SysUserModel> models) {
        List<SysUserVO> list = new ArrayList<>();
        if (!models.isEmpty()) {
            //获取员工id集合
            List<Long> userIds = new ArrayList<>();
            List<Long> orgIds = new ArrayList<>();
            for (SysUserModel model : models) {
                userIds.add(model.getId());
                if (model.getOrgId() != null && !orgIds.contains(model.getOrgId())) {
                    orgIds.add(model.getOrgId());
                }
            }
            //根据用户id获取用户角色
            Map<Long, List<Long>> roleMaps = sysUserRoleRepository.getByUserIds(userIds);
            Map<Long, String> orgMaps = new HashMap<>();
            if (!orgIds.isEmpty()) {
                orgMaps = sysOrgRepository.getOrgNameByIds(orgIds);
            }
            //赋值角色及组织
            list = DozerUtils.mapList(models, SysUserVO.class);
            for (SysUserVO vo : list) {
                if (roleMaps.containsKey(vo.getId())) {
                    vo.setRoleIdList(roleMaps.get(vo.getId()));
                }
                if (roleMaps.containsKey(vo.getOrgId())) {
                    vo.setOrgName(orgMaps.get(vo.getOrgId()));
                }
            }
        }
        return list;
    }

    @Override
    public List<SysUserVO> getList() {
        List<SysUserModel> list = sysUserRepository.getList();
        return DozerUtils.mapList(list, SysUserVO.class);
    }

    @Override
    public boolean updatePassword(Long id, String encode) {
        return sysUserRepository.updatePassword(id, encode);
    }

    @Override
    public boolean save(SysUserVO vo) {
        SysUserModel model = DozerUtils.map(vo, SysUserModel.class);
        //设置是否为超级管理员
        model.setSuperAdmin(SuperAdminEnum.NO.getValue());
        //判断用户名是否已经存在
        SysUserModel user = sysUserRepository.getByUsername(model.getUsername());
        if (user != null) {
            throw new ServerException("用户名已经存在");
        }
        //判断手机号是否存在
        user = sysUserRepository.getByMobile(model.getMobile());
        if (user != null) {
            throw new ServerException("手机号已经存在");
        }
        //保存用户
        boolean res = sysUserRepository.save(model);
        //保存用户角色关系
        sysUserRoleRepository.saveOrUpdateUserRole(model.getId(), vo.getRoleIdList());
        return res;
    }

    @Override
    public boolean update(SysUserVO vo) {
        SysUserModel model = DozerUtils.map(vo, SysUserModel.class);
        //判断用户名是否已经存在
        SysUserModel user = sysUserRepository.getByUsername(model.getUsername());
        if (user != null && !user.getId().equals(vo.getId())) {
            throw new ServerException("用户名已经存在");
        }
        //判断手机号是否存在
        user = sysUserRepository.getByMobile(model.getMobile());
        if (user != null && !user.getId().equals(vo.getId())) {
            throw new ServerException("手机号已经存在");
        }
        //更新用户
        boolean res = sysUserRepository.updateById(model);
        //更新用户角色表
        sysUserRoleRepository.saveOrUpdateUserRole(model.getId(), vo.getRoleIdList());
        return res;
    }

    @Override
    public boolean delete(Long id) {
        //删除用户
        boolean res = sysUserRepository.removeById(id);
        //删除用户角色关系
        sysUserRoleRepository.deleteByUserIdsAndRoleId(id);
        return res;
    }

    @Override
    public SysUserVO getById(Long id) {
        SysUserModel model = sysUserRepository.getById(id);
        if (model != null) {
            List<SysUserModel> list = new ArrayList<>();
            list.add(model);
            return setRoleIds(list).get(0);
        }
        return null;
    }
}
