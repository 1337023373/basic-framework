package com.hengheng.common.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
/**
 * excel通用读取监听类
 *
 * @author 狐狸半面添
 * @create 2023-02-26 15:10
 */
@Slf4j
@Getter
@NoArgsConstructor
public class ExcelListener<T> extends AnalysisEventListener<T> {
    /**
     * 自定义用于暂时存储data 可以通过实例获取该值
     */
    private final List<T> dataList = new ArrayList<>();
    /**
     * 每解析一行都会回调invoke()方法
     *
     * @param data 每一行的数据
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        dataList.add(data);
        log.info("读取的一条信息：{}", data);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("{}条数据，解析完成", dataList.size());
    }
}