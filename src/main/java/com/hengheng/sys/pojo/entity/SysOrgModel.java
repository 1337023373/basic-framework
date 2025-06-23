package com.hengheng.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.hengheng.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构管理
 * @author lyw
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_org")
public class SysOrgModel extends BaseModel {

    /**
     * 机构名称
     */
    private String name;

    /**
     * 上级ID
     */
    private Long pid;

    /**
     * 排序
     */
    private Integer sort;
}
