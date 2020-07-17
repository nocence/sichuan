package uitl;

import model.*;
import org.apache.poi.hssf.usermodel.*;
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


    public static <T extends AbstractArticle> void toExcel(List<String> titles, List<T> list,String type) throws Exception {
        // 定义一个新的工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建第一个Sheet页
        HSSFSheet sheet = wb.createSheet("Sheet1");
        // 创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);
        // 创建一个行
        HSSFRow row = sheet.createRow(0);
        // 创建一个单元格 第1列
        HSSFCell cell = row.createCell(0);

        // cell.setCellValue(new Date()); // 给单元格设置值
        // 设置标题
        for (int i = 0; i < titles.size(); i++){
            String title = titles.get(i);
            cell = getCell(sheet, 0, i);
            setText(cell, title);
            cell.setCellStyle(style);
        }
        String fileName=null;
        for (int i = 0; i < list.size(); i++){
            if ("1".equals(type)){
                fileName="期刊";
                MediaInfo art=(MediaInfo)list.get(i);
                for (int j = 0; j < titles.size(); j++){
                    StringBuilder str = new StringBuilder();
                    //标题
                    if (j == 0){
                        str.append(art.getTitleC()!= null ? art.getTitleC() : "");
                    }
                    //作者
                    else if (j == 1){
                        str.append(art.getShowwriter()!= null ? art.getShowwriter() : "");
                    }
                    //机构
                    else if (j==2){
                        str.append(art.getShowOrgan() != null ? art.getShowOrgan() : "");
                    }
                    //出处中文
                    else if (j==3){
                        str.append(art.getMedia_c() != null && art.getMediasQk() !=null ? art.getMedia_c()+" "+
                                art.getMediasQk() : "");
                    }
                    //出处英文
                    else if (j==4){
                        str.append(art.getMedia_e()  != null ? art.getMedia_e() : "");
                    }
                    //摘要中文
                    else if (j==5){
                        str.append(art.getRemarkC() != null ? art.getRemarkC() : null);
                    }
                    //摘要英文
                    else if(j==6){
                        str.append(art.getRemarkE() != null ? art.getRemarkE() : null);
                    }
                    //关键词
                    else if (j==7){
                        str.append(art.getKeywordC() != null ? art.getKeywordC() : null);
                    }
                    //分类
                    else if(j==8){
                        str.append(art.getClasstypes() != null ? art.getClasstypes() : null);
                    }
                    //相关文献
                    else if(j==9){
                        str.append(art.getRelateText() != null ? art.getRelateText() : null);
                    }
                    //全文路径
                    else if (j==10){
                        str.append(art.getFulltextaddress()!=null ? art.getFulltextaddress() : "");
                    }
                    cell = getCell(sheet, i + 1, j);
                    setText(cell, String.valueOf(str));
                    cell.setCellStyle(style);
                }
            }else if ("2".equals(type)){
               fileName="（博硕）学位论文";
                DegreeInfo degree = (DegreeInfo) list.get(i);
                for (int j = 0; j < titles.size(); j++) {
                    StringBuilder str = new StringBuilder();
                    //标题
                    if (j==0){
                        str.append(degree.getTitleC()!= null ? degree.getTitleC() : "");
                    }
                    //作者
                    else if (j==1){
                        str.append(degree.getFirstWriter()!=null ? degree.getFirstWriter() :"");
                    }
                    //导师
                    else if (j==2){
                        str.append(degree.getBstutorsname() !=null ? degree.getBstutorsname() : "");
                    }
                    //授予单位
                    else if (j==3){
                        str.append(degree.getFirstOrgan() != null ? degree.getFirstOrgan() : "");
                    }
                    //学科专业
                    else if (j==4){
                        str.append(degree.getBsspeciality()!=null ? degree.getBsspeciality() : "");
                    }
                    //授予学位
                    else if (j==5){
                        str.append(degree.getBsdegree()!=null ? degree.getBsdegree() : "");
                    }
                    //学位年度
                    else if(j==6){
                        str.append(degree.getYears() != null ? degree.getYears() : "");
                    }
                    //摘要
                    else if (j==7){
                        str.append(degree.getRemarkC()!=null ? degree.getRemarkC() : "");
                    }
                    //中文关键词
                    else if(j==8){
                        str.append(degree.getKeywordC() != null ? degree.getKeywordC() : "");
                    }
                    //英文关键词
                    else if (j==9){
                        str.append(degree.getKeywordE()!=null ? degree.getKeywordE() : "");
                    }
                    //参考文献
                    else if (j==10){
                        str.append(degree.getReferText()!=null ? degree.getReferText() :"");
                    }
                    //二级参考文献
                    else if (j==11){
                        str.append(degree.getReferids_sec_text()!=null ? degree.getReferids_sec_text() : "");
                    }
                    //相关文献
                    else if (j==12){
                        str.append(degree.getRelateText()!=null ? degree.getRelateText() :"");
                    }
                    //被引文章
                    else if (j==13){
                        str.append(degree.getStrbyidsText()!=null ? degree.getStrbyidsText() : "");
                    }
                    //全文路径
                    else if(j==14){
                        str.append(degree.getFulltextaddress() != null ? degree.getFulltextaddress() : "");
                    }

                    cell = getCell(sheet, i + 1, j);
                    setText(cell, String.valueOf(str));
                    cell.setCellStyle(style);

                }
            }else if ("3".equals(type)){
                fileName="会议论文";
                Meeting meet = (Meeting) list.get(i);
                for (int j = 0; j < titles.size(); j++) {
                    StringBuilder str = new StringBuilder();
                    //标题
                    if (j==0){
                        str.append(meet.getTitleC()!=null ? meet.getTitleC() : "");
                    }
                    //作者
                    else if (j==1){
                        str.append(meet.getFirstWriter()!=null ? meet.getFirstWriter() : "");
                    }
                    //作者单位
                    else if(j==2){
                        str.append(meet.getFirstOrgan()!=null ? meet.getFirstOrgan() : "");
                    }
                    //会议文献
                    else if (j==3){
                        str.append(meet.getHymeetingrecordname()!=null ? meet.getHymeetingrecordname() : "");
                    }
                    //会议名称
                    else if (j==4){
                        str.append(meet.getMedia_c()!=null ? meet.getMedia_c() : "");
                    }
                    //会议日期
                    else if (j==5){
                        str.append(meet.getHymeetingdate()!=null ? meet.getHymeetingdate() : "");
                    }
                    //会议地点
                    else if (j==6){
                        str.append(meet.getHymeetingplace()!=null ? meet.getHymeetingplace() : "");
                    }
                    //摘要
                    else if (j==7){
                        str.append(meet.getRemarkC()!=null ? meet.getRemarkC() : "");
                    }
                    //关键词
                    else if (j==8){
                        str.append(meet.getKeywordC() != null ? meet.getKeywordC() : "");
                    }
                    //分类
                    else if (j==9){
                        str.append(meet.getClasstypes()!=null ? meet.getClasstypes() : "");
                    }
                    //参考文献
                    else if (j==10){
                        str.append(meet.getReferText()!=null ? meet.getReferText() : "");
                    }
                    //二级参考文献
                    else if (j==11){
                        str.append(meet.getReferids_sec_text()!=null ? meet.getReferids_sec_text() : "");
                    }
                    //相关文献
                    else if (j==12){
                        str.append(meet.getRelateText()!=null ? meet.getRelateText() :"");
                    }
                    //被引文章
                    else if (j==13){
                        str.append(meet.getStrbyidsText()!=null ? meet.getStrbyidsText() : "");
                    }
                    //全文路径
                    else if (j==14){
                        str.append(meet.getFulltextaddress() != null ? meet.getFulltextaddress()  :"");
                    }
                    cell = getCell(sheet, i + 1, j);
                    setText(cell, String.valueOf(str));
                    cell.setCellStyle(style);
                }
            }else if ("7".equals(type)){
                fileName="专著";
                Focus focus = (Focus) list.get(i);
                for (int j = 0; j < titles.size(); j++) {
                    StringBuilder str = new StringBuilder();
                    //标题
                    if (j==0){
                        str.append(focus.getTitleC() != null ?  focus.getTitleC():"");
                    }
                    //作者
                    else if (j==1){
                        str.append(focus.getFirstWriter() != null ? focus.getFirstWriter() : "");
                    }
                    //出版社
                    else if (j==2){
                        str.append(focus.getTspress() != null ? focus.getTspress() : "");
                    }
                    //出版时间
                    else if (j==3){
                        str.append(focus.getTspubdate() != null ? focus.getTspubdate() : "");
                    }
                    //出版地
                    else  if (j==4){
                        str.append(focus.getTsprovinces()!=null ? focus.getTsprovinces() : "");
                    }
                    //图书名
                    else if (j==5){
                        str.append(focus.getTsseriesname()!=null ? focus.getTsseriesname() : "");
                    }
                    //页数
                    else if (j==6){
                        str.append(focus.getPagecount()!=null ? focus.getPagecount() : "");
                    }
                    //纸书定价
                    else if (j==7){
                        str.append(focus.getTsprice()!=null ? focus.getTsprice() : "");
                    }
                    //isbn号
                    else if(j==8){
                        str.append(focus.getTsisbn()!=null ? focus.getTsisbn() : "");
                    }
                    //内容简介
                    else if (j==9){
                        str.append(focus.getRemarkC()!=null ? focus.getRemarkC() : "");
                    }
                    //关键词
                    else if (j==10){
                        str.append(focus.getKeywordC()!=null ? focus.getKeywordC() : "");
                    }
                    //分类
                    else  if (j==11){
                        str.append(focus.getClasstypes()!=null ? focus.getClasstypes() : "");
                    }
                    //引证文献
                    else if (j==12){
                        str.append(focus.getStrbyidsText()!= null ? focus.getStrbyidsText() : "");
                    }
                    //相关文献
                    else if (j==13){
                        str.append(focus.getRelateText()!=null ? focus.getRelateText() : "");
                    }
                    //全文路径
                    else  if (j==14){
                        str.append(focus.getFulltextaddress()!=null ? focus.getFulltextaddress() : "");
                    }
                    cell = getCell(sheet, i + 1, j);
                    setText(cell, String.valueOf(str));
                    cell.setCellStyle(style);
                }
            }else if ("10".equals(type)){
                fileName="政策法规";
                Policies policies = (Policies) list.get(i);
                for (int j = 0; j < titles.size(); j++) {
                    StringBuilder str = new StringBuilder();
                    //标题
                    if (j==0){
                      str.append(policies.getTitleC()!=null ? policies.getTitleC() : "");
                    }
                    //分类内容
                    else if(j==1){
                        str.append(policies.getMedia_c()!=null ? policies.getMedia_c() : "");
                    }
                    //颁布年份
                    else if (j==2){
                        str.append(policies.getYears()!=null ? policies.getYears() : "");
                    }
                    //颁发部门
                    else if(j==3){
                        str.append(policies.getFirstOrgan()!=null ? policies.getFirstOrgan() : "");
                    }
                    //文摘
                    else if(j==4){
                        str.append(policies.getRemarkC()!=null ? policies.getRemarkC() : "");
                    }
                    //全文路径
                    else if(j==5){
                        str.append(policies.getFulltextaddress()!=null ? policies.getFulltextaddress() :"");
                    }
                    cell = getCell(sheet, i + 1, j);
                    setText(cell, String.valueOf(str));
                    cell.setCellStyle(style);
                }
            }

        }
        FileOutputStream fileOut = new FileOutputStream("E:\\works\\sichuan\\"+fileName+".xls");
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }
}