package com.hengheng.sys.pojo.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hengheng.common.base.TreeNode;
import com.hengheng.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author lyw
 * @version 1.0
 */
@Data
@Schema(description = "机构管理返回参数")
public class SysOrgVO extends TreeNode<SysOrgVO> {
    @Schema(description = "机构名称", required = true)
    @NotBlank(message = "机构名称不能为空")
    private String name;

    @Schema(description = "排序", required = true)
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date createTime;

    @Schema(description = "上级名称")
    private String parentName;
}
