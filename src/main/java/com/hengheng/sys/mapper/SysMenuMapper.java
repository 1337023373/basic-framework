package com.hengheng.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hengheng.sys.pojo.entity.SysMenuModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lkj
 * @Date 2025/6/19 16:36
 * @Version 1.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuModel> {
    List<SysMenuModel> getListByUserId(@Param("userId") Long userId);

}
