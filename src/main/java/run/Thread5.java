package run;

import model.AbstractArticle;
import uitl.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

import static run.ExportTest.getPolicies;

/**
 * @ClassName Thread5
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/17 10:43
 * @Version 1.0
 */
public class Thread5 implements Runnable{
    private static List<String> policiesList;
    static {
        policiesList = new ArrayList<>();
        policiesList.add("标题");
        policiesList.add("分类内容");
        policiesList.add("颁布年份");
        policiesList.add("颁发部门");
        policiesList.add("文摘");
        policiesList.add("全文路径");
    }
    @Override
    public void run() {
        List<AbstractArticle> policies = getPolicies();
        try {
            ExcelUtil.toExcel(policiesList,policies,"10");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}