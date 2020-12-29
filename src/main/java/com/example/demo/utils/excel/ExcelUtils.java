package com.example.demo.utils.excel;

import com.example.demo.dto.excel.ParamsStringDto;
import com.example.demo.entity.User;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelUtils {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    private static NumberFormat numberFormat = NumberFormat.getInstance();
    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    static {
        numberFormat.setGroupingUsed(false);
    }
    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if(file.getName().endsWith(EXCEL_XLS)){	 //Excel 2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){	// Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * 根据单元格值得属性获取值
     * @throws Exception
     */
    public static String getValueStr(Cell cell) throws Exception{

        if(cell == null){
            return "";
        }
        int cellType = cell.getCellType();
        String cellValue = "";
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:        // 文本
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:    // 数字、日期
              /*  if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = fmt.format(cell.getDateCellValue());
                } else {
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = numberFormat.format(cell.getNumericCellValue());
                }*/
                break;
            case Cell.CELL_TYPE_BOOLEAN:    // 布尔型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK: // 空白
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_ERROR: // 错误
                cellValue = "错误";
                break;
            case Cell.CELL_TYPE_FORMULA:    // 公式
                // 得到对应单元格的公式
                //cellValue = cell.getCellFormula() + "#";
                // 得到对应单元格的字符串
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cellValue = String.valueOf(cell.getRichStringCellValue().getString()) + "#";
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    /**
     * 返回指定的合并单元格的lastColumn
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private static int getLastColumn(Sheet sheet, int row , int column) {
        int firstColumn = 0;
        int lastColumn = 0;
        int firstRow = 0;
        int lastRow = 0;
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            firstColumn = range.getFirstColumn();
            lastColumn = range.getLastColumn();
            firstRow = range.getFirstRow();
            lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return lastColumn;
                }
            }
        }
        return lastColumn;
    }

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText 源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        int total = 0;
        for (String tmp = srcText; tmp != null&&tmp.length() >= findText.length();){
            if(tmp.indexOf(findText) == 0){
                total ++;
            }
            tmp = tmp.substring(1);
        }
        return total;
    }

    /**
     * 返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索
     * @param srcText
     * @param findText
     * @return
     */
    public static List<String> appearStr(String srcText, String findText) {
        List<String> strs = new ArrayList<>();
        int index = 0;
        while ((index = srcText.indexOf(findText, index)) != -1) {
            index = index + findText.length();
            strs.add(srcText.substring(0,index));
        }
        return strs;
    }

    /**
     * 从平台获取部门名称和idMap
     * @param availableDepartments
     * @return
     */
    private static Map<String,Long> getDepartmentId(List<String> availableDepartments, List<ParamsStringDto> list) {
        //目前没有部门接口，用一个map来代替
        Map<String,Long> maps = new HashMap<>();
        for(int i = 0 ; i < availableDepartments.size() ; i++ ){
            //用于判断availableDepartments.get(i)是否存在
            Boolean flag = false;
            for(ParamsStringDto dto:list){
                if (dto.getLabel().equals(availableDepartments.get(i))) {
                    maps.put(availableDepartments.get(i),Long.valueOf(dto.getValue()));
                    flag = true;
                }
            }
            if (!flag){
                return null;
            }
        }
        return maps;
    }

    /**
     * 从平台获取全部部门名称
     * @return
     * @param list
     */
    private static Map<String,Long> getDepartments(List<ParamsStringDto> list) {
        //存放接口部门信息 todo
        Map<String,Long> maps = new HashMap<>();
        if (list != null) {
            for (ParamsStringDto dto : list) {
                maps.put(dto.getLabel(), Long.valueOf(dto.getValue()));
            }
        }
        return maps;
    }

    public static XSSFCell getCell(XSSFSheet sheet, int row, int col) {
        XSSFRow sheetRow = sheet.getRow(row);
        if (sheetRow == null) {
            sheetRow = sheet.createRow(row);
        }
        XSSFCell cell = sheetRow.getCell(col);
        if (cell == null) {
            cell = sheetRow.createCell(col);
        }
        return cell;
    }
    public static void setText(XSSFCell cell, String text) {
        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(text);
    }

    /**
     * 跨年支付下载模板
     */
    public static Map<String, Object> crossYearToExcel(List<String> titles, HttpServletResponse response, String modelPath, String FileName)throws Exception{


//        response.setHeader("Content-disposition", "attachment; filename=" + new String(FileList.FileName.getBytes("GB2312"), "8859_1") + ".xls");// 设定输出文件头
        response.setHeader("Content-disposition", "attachment; filename=" + new String(FileName.getBytes("GB2312"), "8859_1") + ".xlsx");
        response.setContentType("application/msexcel");// 定义输出类型


        //建立excel文件

        XSSFWorkbook wb=new XSSFWorkbook(); // 定义一个新的工作簿
        XSSFSheet sheet=wb.createSheet("第一个Sheet页");  // 创建第一个Sheet页
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        XSSFRow row=sheet.createRow(0); // 创建一个行
        XSSFCell cell=row.createCell(0); // 创建一个单元格  第1列
        //cell.setCellValue(new Date());  // 给单元格设置值
        for (int i = 0; i < titles.size(); i++) { //设置标题
            String title = titles.get(i);
            cell = getCell(sheet, 0, i);
            setText(cell, title);
            cell.setCellStyle(style);
        }


        ServletOutputStream fileOut=response.getOutputStream();
        wb.write(fileOut);
        fileOut.close();

        return null;
    }

    /**
     * 部门导出
     * @param response
     * @param pathString
     * @param fileName
     */
    public static void exportModelExcelStatisticsByDept(HttpServletResponse response, String pathString, String fileName) {
        try {
            //定义输出
            ServletOutputStream out = response.getOutputStream();
            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "8859_1") + ".xlsx");
            // 定义输出类型
            response.setContentType("application/msexcel");
//            File excelFile = new File(pathString); // 创建文件对象
            File excelFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + pathString);
            excelFile = new File(excelFile.getPath());
            //判断对象文件是否存在
            if(excelFile.exists()){
                logger.info("模板路径存在");
            }
            FileInputStream is = new FileInputStream(excelFile); // 文件流
            //建立excel文件
            Workbook workBook = ExcelUtils.getWorkbok(is,excelFile);
            //获取工作表
            Sheet sheet = workBook.getSheetAt(0);
            //sheet名称赋值
            workBook.setSheetName(0,fileName+"一览");
            //


            workBook.write(out);
            logger.info("返回响应流");
            out.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }

    /**=================================主体导入并录入   开始========================================================**/
    /**
     * 主体导入下载导出模板
     * @param response              返回
     * @param fileName              文件名
     * @param listBm                部门信息
     * @param subjectPath           模板路径
     * @throws Exception
     */
    public static void exportModelExcelSubject(HttpServletResponse response, String fileName, List<ParamsStringDto>  listBm, String subjectPath) throws Exception{
        try {
            //定义输出
            ServletOutputStream out = response.getOutputStream();
            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "8859_1") + ".xlsx");
            // 定义输出类型
            response.setContentType("application/msexcel");
//            File excelFile = new File(subjectPath); // 创建文件对象
            File excelFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + subjectPath);
            excelFile = new File(excelFile.getPath());
            if(excelFile.exists()){
                logger.info("模板路径存在");
            }
            FileInputStream is = new FileInputStream(excelFile); // 文件流
            //建立excel文件
            Workbook workBook = ExcelUtils.getWorkbok(is,excelFile);
            //获取工作表
            Sheet sheet = workBook.getSheetAt(0);
            //用于存放可使用部门id\列号
            Map<Long,Integer> lineMaps = new HashMap<>();
            //主体模板导出 用于存放可使用部门\ID
            Map<String,Long> depMaps = getDepartments(listBm);
            int numDept = depMaps.size();  //添加的列数量 实际循环时添加的数量要从16列开始
            Row rowfirst = sheet.getRow(0);
            Row rowSpecial = sheet.getRow(1);
            Row rowtwo = sheet.getRow(2);
            Row rowAdd = sheet.getRow(3);
            //对标题行操作
            for (int j=0;j<numDept-1;j++){
                CellStyle style0 = rowfirst.getCell(16).getCellStyle();
                rowfirst.createCell(j+17);
                rowfirst.getCell(j+17).setCellStyle(style0);
                CellStyle style1 = rowtwo.getCell(16).getCellStyle();
                rowtwo.createCell(j+17);
                rowtwo.getCell(j+17).setCellStyle(style1);
                CellStyle style2 = rowAdd.getCell(16).getCellStyle();
                rowAdd.createCell(j+17);
                rowAdd.getCell(j+17).setCellStyle(style2);
                CellStyle style3 = rowSpecial.getCell(16).getCellStyle();
                rowSpecial.createCell(j+17);
                rowSpecial.getCell(j+17).setCellStyle(style3);
            }
            //合并预算可用部门
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,15+numDept));
            sheet.addMergedRegion(new CellRangeAddress(2,2,16,15+numDept));
            //循环depMaps将信息存放到lineMaps中
            int n = 16;         //部门在主体模板中是12列分单元格
            for (Map.Entry<String,Long> m:depMaps.entrySet()){
                //存放lineMaps
                lineMaps.put(m.getValue(),n);
                //列赋值
                rowAdd.getCell(n).setCellValue(m.getKey());
                n += 1;
            }
            workBook.write(out);
            logger.info("返回响应流");
            out.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void userInfoExport(HttpServletResponse response, String fileName, List<User> listBm, String subjectPath) throws Exception{
        try {
            //定义输出
            ServletOutputStream out = response.getOutputStream();
            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "8859_1") + ".xlsx");
            // 定义输出类型
            response.setContentType("application/msexcel");
//            File excelFile = new File(subjectPath); // 创建文件对象
            File excelFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + subjectPath);
            excelFile = new File(excelFile.getPath());
            if(excelFile.exists()){
                logger.info("模板路径存在");
            }
            FileInputStream is = new FileInputStream(excelFile); // 文件流
            //建立excel文件
            Workbook workBook = ExcelUtils.getWorkbok(is,excelFile);
            CellStyle cellStyle =workBook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            //获取工作表
            Sheet sheet = workBook.getSheetAt(0);
            Row row = null;
           /* row.createCell(1).setCellValue(listBm.get(0).getCouponName());*/

            for(int i =0;i<listBm.size();i++){
                row = sheet.createRow(i+2);
                row.setHeightInPoints(25);
                Cell cell0= row.createCell(0);
                Cell cell1= row.createCell(1);
                Cell cell2= row.createCell(2);
                Cell cell3= row.createCell(3);
                cell0.setCellStyle(cellStyle);
                cell1.setCellStyle(cellStyle);
                cell2.setCellStyle(cellStyle);
                cell3.setCellStyle(cellStyle);
                    cell0.setCellValue(i+1);
                    cell1.setCellValue(listBm.get(i).getUserName());
                    cell2.setCellValue(listBm.get(i).getPassword());
                    cell3.setCellValue(listBm.get(i).getPhone());

            }
            workBook.write(out);
            logger.info("返回响应流");
        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }
}
