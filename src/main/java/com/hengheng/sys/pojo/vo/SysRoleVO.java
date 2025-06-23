package com.hengheng.sys.pojo.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hengheng.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lyw
 * @version 1.0
 */
@Data
@Schema(description = "角色管理返回参数")
public class SysRoleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "菜单ID列表")
    private List<Long> menuIdList;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date createTime;

}
