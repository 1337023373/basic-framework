package com.hengheng.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hengheng.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.OpenAPI30;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lyw
 * @version 1.0
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseModel implements Serializable {
    @Schema(description = "主键ID", example = "1")
    public Long id;

    @Schema(description = "创建人")
    public Long  creator;

    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    public Date createTime;

    @Schema(description = "更新人ID")
    public Long  updater;

    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    public Date updateTime;

    @TableLogic
    @Schema(description = "是否删除")
    public boolean deleted;
}
