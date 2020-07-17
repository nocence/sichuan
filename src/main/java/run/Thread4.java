package run;

import model.AbstractArticle;
import uitl.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

import static run.ExportTest.getFocus;

/**
 * @ClassName Thread4
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/17 10:43
 * @Version 1.0
 */
public class Thread4 implements Runnable{
    private static List<String> focusList;
    static {
        focusList = new ArrayList<>();
        focusList.add("标题");
        focusList.add("作者");
        focusList.add("出版社");
        focusList.add("出版时间");
        focusList.add("出版地");
        focusList.add("图书名");
        focusList.add("页数");
        focusList.add("纸书定价");
        focusList.add("isbn号");
        focusList.add("内容简介");
        focusList.add("关键词");
        focusList.add("分类");
        focusList.add("引证文献");
        focusList.add("相关文献");
        focusList.add("全文路径");
    }
    @Override
    public void run() {
        List<AbstractArticle> focus = getFocus();
        try {
            ExcelUtil.toExcel(focusList,focus,"7");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}