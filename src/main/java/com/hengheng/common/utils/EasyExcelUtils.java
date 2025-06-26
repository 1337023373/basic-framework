package com.hengheng.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
/**
 * @author 狐狸半面添
 * @create 2023-02-26 21:50
 */
public class EasyExcelUtils {
    private static final Log log = LogFactory.getLog(EasyExcelUtils.class);
    /**
     * 单sheet版本Excel读取
     * 从Excel中读取文件，读取的文件是一个DTO类
     *
     * @param inputStream 文件流
     * @param clazz       行数据类型
     */
    public static <T> List<T> readExcelOneSheet(InputStream inputStream, final Class<?> clazz) {
        // 1.创建监听类
        ExcelListener<T> listener = new ExcelListener<>();
        // 2.构建工作簿对象的输入流
        ExcelReader excelReader = EasyExcel.read(inputStream, clazz, listener).build();
        // 3.构建工作表对象的输入流，默认是第一张工作表
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        // 4.读取信息，每读取一行都会调用监听类的 invoke 方法
        excelReader.read(readSheet);
        // 5.关闭流，如果不关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        return listener.getDataList();
    }
    /**
     * 多sheet版本Excel读取
     *
     * @param <T>      行数据的类型
     * @param filePath 文件路径
     * @param clazz    行数据的类型
     * @return 所有信息
     */
    public static <T> List<T> readExcelAllSheet(String filePath, final Class<?> clazz) {
        ExcelListener<T> listener = new ExcelListener<>();
        // 读取全部sheet
        // 这里需要注意 ExcelListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(filePath, clazz, listener).doReadAll();
        return listener.getDataList();
    }
    /**
     * 网页上的下载导出，只有一个工作表
     *
     * @param fileName  文件名
     * @param clazz     类的字节码文件，行数据的类型
     * @param dataList  导出的数据
     * @param sheetName 工作表名
     * @param response  响应体
     * @throws IOException 异常对象
     */
    public static void writeWeb(String fileName, final Class<?> clazz, List<?> dataList, String sheetName, HttpServletResponse response) throws IOException {
        // 1.指定响应体内容类型
        response.setContentType("application/vnd.ms-excel");
        // 2.指定编码方式
        response.setCharacterEncoding("utf-8");
        // 3.URLEncoder.encode可以防止中文乱码：import java.net.URLEncoder
        fileName = URLEncoder.encode(fileName, "UTF-8");
        // 4.指定响应标头
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName + ".xlsx");
        // 5.获取工作簿对象的输出流
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        // 6.设置工作表的名称
        if (!StringUtils.hasText(sheetName)) {
            sheetName = "sheet1";
        }
        // 7.指定写用哪个class去写
        WriteSheet writeSheet = EasyExcel.writerSheet(0, sheetName).head(clazz).build();
        // 8.将 dataList 中的数据逐行写入工作表中
        excelWriter.write(dataList, writeSheet);
        // 9.finish关闭流
        excelWriter.finish();
        // 10.关闭流
        response.getOutputStream().close();
    }
    public static <T> void writeExcelList(HttpServletResponse response, List<List<T>> data, String fileName, Class<?> clazz, String sheetName) throws Exception {
        OutputStream out = getOutputStream(fileName, response);
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(out, clazz).excelType(ExcelTypeEnum.XLSX).registerWriteHandler(getDefaultHorizontalCellStyleStrategy());
        ExcelWriter excelWriter = excelWriterBuilder.build();
        ExcelWriterSheetBuilder excelWriterSheetBuilder;
        WriteSheet writeSheet;
        for (int i = 1; i <= data.size(); i++) {
            excelWriterSheetBuilder = new ExcelWriterSheetBuilder(excelWriter);
            excelWriterSheetBuilder.sheetNo(i);
            excelWriterSheetBuilder.sheetName(sheetName + i);
            writeSheet = excelWriterSheetBuilder.build();
            excelWriter.write(data.get(i - 1), writeSheet);
        }
        excelWriter.finish();
        out.close();
    }
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //  response.setContentType("application/vnd.ms-excel"); // .xls
        // .xlsx
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName + ".xlsx");
        return response.getOutputStream();
    }
    /**
     * 获取默认表头内容的样式
     *
     * @return
     */
    private static HorizontalCellStyleStrategy getDefaultHorizontalCellStyleStrategy() {
        /** 表头样式 **/
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色（浅灰色）
        // 可以参考：https://www.cnblogs.com/vofill/p/11230387.html
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 字体大小
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        /** 内容样式 **/
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 内容字体样式（名称、大小）
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("宋体");
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
//		//设置内容垂直居中对齐
//		contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		//设置内容水平居中对齐
//		contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        // 头样式与内容样式合并
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }
    /**
     * 导出 Excel到指定目录 ：单个 sheet，带表头,
     *
     * @param tableData
     * @param fileName  导出的路径+文件名  例如：   file/test.xlsx
     * @param sheetName 导入文件的 sheet 名
     * @throws Exception
     */
    public static void writeExcelAutoColumnWidth(String fileName, List<?> tableData, String sheetName, Class<?> clazz) throws Exception {
        // 根据用户传入字段 假设我们要忽略 date
        EasyExcel.write(fileName, clazz)
                .sheet(sheetName)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(tableData);
    }
    /**
     * 导出 Excel到指定目录 ：单个 sheet，带表头,
     *
     * @param fileName  导出的路径+文件名  例如：   file/test.xlsx
     * @param tableData
     */
    public static void writeExcelWithOneSheet1(String fileName, List<?> tableData, String sheetName, Class<?> clazz, Set<String> excludeColumnFiledNames) {
        // 根据用户传入字段 假设我们要忽略 date
        EasyExcel.write(fileName, clazz)
                .excludeColumnFiledNames(excludeColumnFiledNames)
                .sheet(sheetName)
                .registerWriteHandler(styleWrite(false))
                .doWrite(tableData);
    }
    public static HorizontalCellStyleStrategy styleWrite(boolean isWrapped) {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        // headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 18);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        //contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        //contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 11);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(isWrapped);
        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        //EasyExcel.write(fileName, DemoData.class).registerWriteHandler(horizontalCellStyleStrategy).sheet("模板")
        //    .doWrite(data());
    }
}