package com.hengheng.sys.service.impl;

import com.hengheng.common.constant.Constant;
import com.hengheng.common.exception.ServerException;
import com.hengheng.common.utils.DozerUtils;
import com.hengheng.common.utils.TreeUtils;
import com.hengheng.sys.pojo.entity.SysOrgModel;
import com.hengheng.sys.pojo.vo.SysOrgVO;
import com.hengheng.sys.repository.SysOrgRepository;
import com.hengheng.sys.repository.SysUserRepository;
import com.hengheng.sys.service.SysOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lkj
 * @Date 2025/6/19 11:21
 * @Version 1.0
 */
@Service
public class SysOrgServiceImpl implements SysOrgService {
    @Resource
    private SysOrgRepository sysOrgRepository;
    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public List<SysOrgVO> getList() {
        List<SysOrgModel> orgModels = sysOrgRepository.list();
        List<SysOrgVO> sysOrgVOS = DozerUtils.mapList(orgModels, SysOrgVO.class);
        List<SysOrgVO> list = setParentName(sysOrgVOS);
        return TreeUtils.build(sysOrgVOS);
    }

    @Override
    public List<SysOrgModel> getAll() {
        return sysOrgRepository.list();
    }

    @Override
    public boolean save(SysOrgVO vo) {
        SysOrgModel sysOrgModel = DozerUtils.map(vo, SysOrgModel.class);
        return sysOrgRepository.save(sysOrgModel);
    }

    @Override
    public boolean update(SysOrgVO vo) {
        if (vo.getId().equals(vo.getPid())) {
            throw new ServerException("上级组织不能为自身");
        }
        //获取本部门以及子部门数据
        List<Long> subOrgList = sysOrgRepository.getDeptAndSubDeptList(vo.getId());
        if (subOrgList.contains(vo.getPid())) {
            throw new ServerException("上级组织不能为下级");
        }
        return sysOrgRepository.updateById(DozerUtils.map(vo, SysOrgModel.class));
    }

    @Override
    public boolean delete(Long id) {
        //查询是否存在子组织
        Long count = sysOrgRepository.getCountByPid(id);
        if (count > 0) {
            throw new ServerException("请先删除子组织");
        }
        //判断机构下面是否存在用户
        count = sysUserRepository.getCountByOrgId(id);
        if (count > 0) {
            throw new ServerException("组织下面有用户，不能删除");
        }
        return sysOrgRepository.removeById(id);
    }

    public List<SysOrgVO> setParentName(List<SysOrgVO> list){
        if(!list.isEmpty()){
            List<Long> parentIds = new ArrayList<>();
            for (SysOrgVO vo : list){
                if(!Constant.ROOT.equals(vo.getPid()) && !parentIds.contains(vo.getPid())){
                    parentIds.add(vo.getPid());
                }
            }
            if(parentIds.size() > 0){
                Map<Long,String> map = sysOrgRepository.getByIds(parentIds).stream().collect(Collectors.toMap(SysOrgModel::getId,SysOrgModel::getName));
                for (SysOrgVO vo : list){
                    if(vo.getPid() > 0 && map.containsKey(vo.getPid())){
                        vo.setParentName(map.get(vo.getPid()));
                    }
                }
            }
        }
        return list;
    }
}
