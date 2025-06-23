package com.hengheng.sys.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.hengheng.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 集成配置
 *
 * @author Phoenix
 * @version 1.0
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_integration_config")
public class SysIntegrationModel extends BaseModel{

    private String dbType;

    /**
     * 是否高级属性（0缓存 1不缓存）
     */
    private Integer isAdvanced;

    /**
     * 适用版本
     */
    private String applicableVersion;

    /**
     * 同步工具名
     */
    private String product;

    /**
     * 值类型
     */
    private String valueType;

    /**
     * 子类
     */
    private String subCategory;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 配置项
     */
    private String label;

    /**
     * 配置项标题
     */
    private String title;

    /**
     * 控件配置
     */
    private String compConfig;

    /**
     * 映射实际任务配置项
     */
    private String realLabel;

    /**
     * 配置项描述
     */
    private String descr;

    /**
     * 是否必须项（0是 1否）
     */
    private Integer isRequired;

    /**
     * 控件类型
     */
    private String compType;

    /**
     * 分类
     */
    private String category;

    /**
     * 排序字段
     */
    private int seq;

}
