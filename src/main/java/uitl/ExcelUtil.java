package uitl;

import model.BusinessResourceOrderRespVO;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.util.List;

/**
 * @ClassName ExcelUtil
 * @Description 数据导出到excel
 * @Author Innocence
 * @Date 2020/7/14 16:13
 * @Version 1.0
 */
public class ExcelUtil {

    public ExcelUtil(){}


    public static HSSFCell getCell(HSSFSheet sheet, int row, int col) {

        HSSFRow sheetRow = sheet.getRow(row);

        if (sheetRow == null){

            sheetRow = sheet.createRow(row);

        }

        HSSFCell cell = sheetRow.getCell(col);

        if (cell == null){

            cell = sheetRow.createCell(col);

        }

        return cell;

    }


    public static void setText(HSSFCell cell, String text) {

        cell.setCellType(CellType.STRING);

        cell.setCellValue(text);

    }


    public static void toExcel(List<String> titles, List<BusinessResourceOrderRespVO> list) throws Exception {
        // 定义一个新的工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建第一个Sheet页
        HSSFSheet sheet = wb.createSheet("第一个Sheet页");
        // 创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);
        // 创建一个行
        HSSFRow row = sheet.createRow(0);
        // 创建一个单元格 第1列
        HSSFCell cell = row.createCell(0);

        // cell.setCellValue(new Date()); // 给单元格设置值

        for (int i = 0; i < titles.size(); i++){ // 设置标题

            String title = titles.get(i);

            cell = getCell(sheet, 0, i);

            setText(cell, title);

            cell.setCellStyle(style);

        }

        for (int i = 0; i < list.size(); i++){

            BusinessResourceOrderRespVO vpd = list.get(i);

            for (int j = 0; j < titles.size(); j++){
                StringBuilder str = new StringBuilder();
                //订单编号
                if (j == 0){
                    str.append(vpd.getOrderCode() != null ? vpd.getOrderCode() : "");
                }
                //买家名字
                else if (j == 1){
                    str.append(vpd.getSupplierName() != null ? vpd.getSupplierName() : "");
                }
                cell = getCell(sheet, i + 1, j);

                setText(cell, String.valueOf(str));

                cell.setCellStyle(style);

            }

        }

        FileOutputStream fileOut = new FileOutputStream("D://成员信息表.xls");
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }
}