package com.hengheng.sys.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengheng.sys.mapper.SysMenuMapper;
import com.hengheng.sys.pojo.entity.SysMenuModel;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/19 16:36
 * @Version 1.0
 */
@Repository
public class SysMenuRepository extends ServiceImpl<SysMenuMapper, SysMenuModel> {
    @Resource
    private SysMenuMapper sysMenuMapper;

    public List<SysMenuModel> getListByUserId(Long id) {
        return sysMenuMapper.getListByUserId(id);
    }

    public Long getCountByPid(Long id) {
        return this.lambdaQuery().eq(SysMenuModel::getPid, id).count();
    }
}
