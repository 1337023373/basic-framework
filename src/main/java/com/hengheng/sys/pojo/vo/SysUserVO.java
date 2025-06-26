package com.hengheng.sys.pojo.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hengheng.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lyw
 * @version 1.0
 */
@Data
@Schema(description = "用户管理返回参数")
public class SysUserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    private Long id;

    @Schema(description = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    @ExcelProperty("用户名")
    private String username;

    @Schema(description = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ExcelIgnore
    private String password;

    @Schema(description = "姓名", required = true)
    @NotBlank(message = "姓名不能为空")
    @ExcelProperty("姓名")
    private String realName;

    @Schema(description = "头像")
    @ExcelProperty("头像地址")
    @ExcelIgnore
    private String avatar;

    @Schema(description = "性别 0：男 1：女 2：未知", required = true)
    @ExcelIgnore
    private Integer gender;

    @ExcelProperty("性别")
    private String genderStr; // 性别中文值（导出用）

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    @ExcelProperty("邮箱")
    private String email;

    @Schema(description = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @ExcelProperty("手机号")
    private String mobile;

    @Schema(description = "机构ID", required = true)
    @NotNull(message = "机构ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    private Long orgId;

    @Schema(description = "组织名称")
    @ExcelProperty("组织名称")
    private String orgName;

    @Schema(description = "状态 0：停用 1：正常", required = true)
    @ExcelIgnore
    private Integer status;

    @ExcelProperty("状态")
    private String statusStr; // 状态中文值（导出用）

    @Schema(description = "角色ID列表")
    @ExcelIgnore
    private List<Long> roleIdList;

    @Schema(description = "超级管理员 0：否 1：是")
    @ExcelIgnore
    private Integer superAdmin;

    @ExcelIgnore
    @Schema(description = "超级管理员   0：否   1：是")
    private String superAdminStr; // 导出是否为超级管理员

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date createTime;

    @Schema(description = "头像url")
    @ExcelIgnore
    private String avatarUrl;
}
