package com.hengheng.sys.service;

import com.hengheng.common.base.MyPageResult;
import com.hengheng.sys.pojo.query.BaseQuery;
import com.hengheng.sys.pojo.vo.SysRoleVO;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/19 8:35
 * @Version 1.0
 */
public interface SysRoleService {
    MyPageResult<SysRoleVO> getPage(BaseQuery query);

    boolean save(SysRoleVO vo);

    boolean update(SysRoleVO vo);

    boolean delete(Long id);

    List<SysRoleVO> getAll();
}
