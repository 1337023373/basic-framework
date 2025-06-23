package com.hengheng.common.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author lyw
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPager<TEntity> {

    //@NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @Schema(description = "当前页码", required = true)
    Integer page = 1;

    //@NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数，取值范围 1-1000")
    @Max(value = 1000, message = "每页条数，取值范围 1-1000")
    @Schema(description = "每页条数", required = true)
    Integer limit = 10;

    public Page<TEntity> toPage() {
        return new Page<>(page, limit);
    }

    /**
     * 将 MyBatis-Plus 的分页结果转换成你自定义的分页结果对象
     */
    public MyPageResult<TEntity> toResult(IPage<TEntity> iPage) {
        return new MyPageResult<>(iPage.getRecords(), iPage.getTotal());
    }
}
