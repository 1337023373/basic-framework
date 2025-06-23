package com.hengheng.sys.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.sys.mapper.SysOrgMapper;
import com.hengheng.sys.pojo.entity.SysOrgModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lkj
 * @Date 2025/6/19 14:17
 * @Version 1.0
 */
@Repository
public class SysOrgRepository extends ServiceImpl<SysOrgMapper, SysOrgModel> {
    public Map<Long, String> getOrgNameByIds(List<Long> orgIds) {
        List<SysOrgModel> list = this.lambdaQuery().in(SysOrgModel::getId, orgIds).list();
        return list.stream().collect(Collectors.toMap(SysOrgModel::getId, SysOrgModel::getName));
    }

    public List<Long> getDeptAndSubDeptList(Long orgId) {
        List<SysOrgModel> orgList = this.list();
        List<Long> subIdList = new ArrayList<>();
        getTree(orgId, orgList, subIdList);
        subIdList.add(orgId);
        return subIdList;
    }

    private void getTree(Long id, List<SysOrgModel> orgList, List<Long> subIdList) {
        for (SysOrgModel org : orgList) {
            if (org.getPid().equals(id)) {
                getTree(org.getId(), orgList, subIdList);
                subIdList.add(org.getId());
            }
        }
    }

    public Long getCountByPid(Long id) {
        return this.lambdaQuery().eq(SysOrgModel::getPid, id).count();
    }

    public List<SysOrgModel> getByIds(List<Long> parentIds) {
        return this.lambdaQuery().in(SysOrgModel::getId, parentIds).list();
    }
}
