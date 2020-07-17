package run;

import model.AbstractArticle;
import uitl.ExcelUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static run.ExportTest.getMedia;

/**
 * @ClassName Thread1
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/17 10:42
 * @Version 1.0
 */
public class Thread1 implements Runnable {
    private static List<String> mediaList;
    static {
        mediaList = new ArrayList<>();
        mediaList.add("标题");
        mediaList.add("作者（中文）");
        mediaList.add("机构");
        mediaList.add("出处(中文)");
        mediaList.add("出处(英文)");
        mediaList.add("中文摘要");
        mediaList.add("英文摘要");
        mediaList.add("关键词");
        mediaList.add("分类");
        mediaList.add("相关文献");
        mediaList.add("全文路径");
    }
    @Override
    public void run() {
        List<AbstractArticle> media = getMedia();
        HashSet<AbstractArticle> set = new HashSet<>(media);
        ArrayList<AbstractArticle> list = new ArrayList<>(set);
        try {
            ExcelUtil.toExcel(mediaList,list,"1");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}