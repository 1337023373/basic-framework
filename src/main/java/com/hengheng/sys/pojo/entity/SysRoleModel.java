package com.hengheng.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hengheng.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色管理
 * @author lyw
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class SysRoleModel extends BaseModel {


    /**
     * 角色名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

}
