package com.hengheng.sys.service;

import com.hengheng.sys.pojo.entity.SysOrgModel;
import com.hengheng.sys.pojo.vo.SysOrgVO;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/18 17:05
 * @Version 1.0
 */
public interface SysOrgService {
    List<SysOrgVO> getList();

    List<SysOrgModel> getAll();

    boolean save(SysOrgVO vo);

    boolean update(SysOrgVO vo);

    boolean delete(Long id);
}
