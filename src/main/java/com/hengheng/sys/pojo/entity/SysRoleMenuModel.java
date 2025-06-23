package com.hengheng.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hengheng.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色菜单关系
 * @author lyw
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role_menu")
public class SysRoleMenuModel extends BaseModel {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

}
