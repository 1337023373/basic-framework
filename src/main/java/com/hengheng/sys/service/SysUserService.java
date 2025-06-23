package com.hengheng.sys.service;

import com.hengheng.common.base.MyPageResult;
import com.hengheng.sys.pojo.entity.SysOrgModel;
import com.hengheng.sys.pojo.entity.SysUserModel;
import com.hengheng.sys.pojo.query.BaseQuery;
import com.hengheng.sys.pojo.vo.SysUserVO;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/18 17:04
 * @Version 1.0
 */
public interface SysUserService {
    MyPageResult<SysUserVO> getPage(BaseQuery<SysUserModel> query);

    List<SysUserVO> getList();

    boolean updatePassword(Long id, String encode);

    boolean save(SysUserVO vo);

    boolean update(SysUserVO vo);

    boolean delete(Long id);

    SysUserVO getById(Long id);
}
