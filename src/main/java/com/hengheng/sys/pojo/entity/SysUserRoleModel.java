package com.hengheng.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hengheng.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关系
 * @author lyw
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user_role")
public class SysUserRoleModel extends BaseModel {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 用户ID
     */
    private Long userId;

}
