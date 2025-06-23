package com.hengheng.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hengheng.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单管理
 * @author lyw
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_menu")
public class SysMenuModel extends BaseModel {

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 上级ID，一级菜单为0
     */
    private Long pid;

    /**
     * 排序
     */
    private Integer sort;


    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)
     */
    private String authority;

    /**
     * 菜单名称
     */
    private String name;

}
