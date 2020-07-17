package run;

import model.AbstractArticle;
import uitl.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

import static run.ExportTest.getDegree;

/**
 * @ClassName Thread1
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/17 10:42
 * @Version 1.0
 */
public class Thread2 implements Runnable {

    //学位论文
    private static List<String> degreeList;

    static {
        degreeList = new ArrayList<>();
        degreeList.add("标题");
        degreeList.add("作者");
        degreeList.add("导师");
        degreeList.add("授予单位");
        degreeList.add("学科专业");
        degreeList.add("授予学位");
        degreeList.add("学位年度");
        degreeList.add("摘要");
        degreeList.add("中文关键词");
        degreeList.add("英文关键词");
        degreeList.add("参考文献");
        degreeList.add("二级参考文献");
        degreeList.add("相关文献");
        degreeList.add("被引文章");
        degreeList.add("全文路径");
    }

    @Override
    public void run() {
        List<AbstractArticle> degree = getDegree();
        try {
            ExcelUtil.toExcel(degreeList,degree,"2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}