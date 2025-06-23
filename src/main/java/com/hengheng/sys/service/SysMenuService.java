package com.hengheng.sys.service;

import com.hengheng.common.base.UserDetail;
import com.hengheng.sys.pojo.vo.SysMenuVO;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/18 17:05
 * @Version 1.0
 */
public interface SysMenuService {
    List<SysMenuVO> getUserMenuList(UserDetail user);

    boolean save(SysMenuVO vo);

    boolean update(SysMenuVO vo);

    boolean delete(Long id);
}
