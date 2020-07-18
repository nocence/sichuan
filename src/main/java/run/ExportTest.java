package run;

import com.cqvip.vipcloud.model.dto.result.ArticleField;
import com.sun.org.apache.bcel.internal.generic.RET;
import model.AbstractArticle;
import uitl.ExcelUtil;
import uitl.GetInfoFromZlfUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExportTest
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/14 16:39
 * @Version 1.0
 */
public class ExportTest {

    //期刊文献
    private static List<String> mediaList;
    //学位论文
    private static List<String> degreeList;
    //会议论文
    private static List<String> meetingList;
    //专著
    private static List<String> focusList;
    //政策法规
    private static List<String> policiesList;


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

        meetingList = new ArrayList<>();
        meetingList.add("标题");
        meetingList.add("作者");
        meetingList.add("作者单位");
        meetingList.add("会议文献");
        meetingList.add("会议名称");
        meetingList.add("会议日期");
        meetingList.add("会议地点");
        meetingList.add("摘要");
        meetingList.add("关键词");
        meetingList.add("分类");
        meetingList.add("参考文献");
        meetingList.add("二级参考文献");
        meetingList.add("相关文献");
        meetingList.add("被引文章");
        meetingList.add("全文路径");

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

        policiesList = new ArrayList<>();
        policiesList.add("标题");
        policiesList.add("分类内容");
        policiesList.add("颁布年份");
        policiesList.add("颁发部门");
        policiesList.add("文摘");
        policiesList.add("全文路径");

    }



    /**
     * 获取学位文献
     * @author Innocence
     * @date 2020/7/16
     * @return java.util.List<model.AbstractArticle>
     */
    public static List<AbstractArticle> getDegree() {
        GetInfoFromZlfUtil zlfUtil = new GetInfoFromZlfUtil();
        List<ArticleField> total = new ArrayList<>();
        for (int i = 1; i < 18; i++) {
            List<ArticleField> articles = zlfUtil.getArticles(i, "2");
            total.addAll(articles);
        }
        List<AbstractArticle> list = zlfUtil.loadArticles(total, "2");
        return list;
    }

    /**
     * 获取会议论文
     * @author Innocence
     * @date 2020/7/16
     * @return java.util.List<model.AbstractArticle>
     */
    public static List<AbstractArticle> getMeeting(){
        GetInfoFromZlfUtil zlfUtil = new GetInfoFromZlfUtil();
        List<ArticleField> total = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            List<ArticleField> articles = zlfUtil.getArticles(i, "3");
            total.addAll(articles);
        }
        List<AbstractArticle> list = zlfUtil.loadArticles(total, "3");
        return list;
    }

    /**
     * 获取专著
     * @author Innocence
     * @date 2020/7/16
     * @return java.util.List<model.AbstractArticle>
     */
    public static List<AbstractArticle> getFocus(){
        GetInfoFromZlfUtil zlfUtil = new GetInfoFromZlfUtil();
        List<ArticleField> total = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            List<ArticleField> articles = zlfUtil.getArticles(i, "7");
            total.addAll(articles);
        }
        List<AbstractArticle> list = zlfUtil.loadArticles(total, "7");
        return list;
    }

    /**
     * 获取政策法规
     * @author Innocence
     * @date 2020/7/16
     * @return java.util.List<model.AbstractArticle>
     */
    public static List<AbstractArticle> getPolicies(){
        GetInfoFromZlfUtil zlfUtil = new GetInfoFromZlfUtil();
        List<ArticleField> total = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            List<ArticleField> articles = zlfUtil.getArticles(i, "10");
            total.addAll(articles);
        }
        List<AbstractArticle> list = zlfUtil.loadArticles(total, "10");
        return list;
    }

    public static void main(String[] args) {
        new Thread(new Thread1()).start();
//        new Thread(new Thread2()).start();
//        new Thread(new Thread3()).start();
//        new Thread(new Thread4()).start();
//        new Thread(new Thread5()).start();
    }
}