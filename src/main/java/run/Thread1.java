package run;

import com.cqvip.vipcloud.model.dto.PageObject;
import com.cqvip.vipcloud.model.dto.function.VipSearchByObject;
import com.cqvip.vipcloud.model.dto.result.ArticleField;
import com.cqvip.vipcloud.model.enums.VipSearchEnum;
import com.cqvip.vipcloud.service.VipSearchByObjectService;
import model.AbstractArticle;
import uitl.ExcelUtil;
import uitl.GetInfoFromZlfUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static uitl.GetInfoFromZlfUtil.RULES;
import static uitl.GetInfoFromZlfUtil.SZIE;

/**
 * @ClassName Thread1
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/17 10:42
 * @Version 1.0
 */
public class Thread1 implements Runnable {
    private static List<String> mediaList;
    private static List<String> yearsList;
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

        yearsList = new ArrayList<>();
        yearsList.add("2000");
        yearsList.add("2001");
        yearsList.add("2002");
        yearsList.add("2003");
        yearsList.add("2004");
        yearsList.add("2005");
        yearsList.add("2006");
        yearsList.add("2007");
        yearsList.add("2008");
        yearsList.add("2009");
        yearsList.add("2010");
        yearsList.add("2011");
        yearsList.add("2012");
        yearsList.add("2013");
        yearsList.add("2014");
        yearsList.add("2015");
        yearsList.add("2016");
        yearsList.add("2017");
        yearsList.add("2018");
        yearsList.add("2019");
        yearsList.add("2020");
    }
    @Override
    public void run() {



        List<AbstractArticle> media = getMediaInfo();
        HashSet<AbstractArticle> set = new HashSet<>(media);
        ArrayList<AbstractArticle> list = new ArrayList<>(set);
        try {
            ExcelUtil.toExcel(mediaList,list,"1");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<AbstractArticle> getMediaInfo(){
        GetInfoFromZlfUtil zlfUtil = new GetInfoFromZlfUtil();
        List<ArticleField> all = new ArrayList<>();
        for (String year:yearsList) {
            Integer total = getTotal(year);
            int pageNum = total / SZIE;
            for (int i = 1; i < pageNum+1; i++) {
                List<ArticleField> articles = getMediaArticles(year, i);
                all.addAll(articles);
                if (all.size()>=12100)break;
            }
        }
        List<AbstractArticle> list = zlfUtil.loadArticles(all, "1");
        return list;
    }

    /**
     * 单独给期刊文献写一个方法，因为借口有限制，同一个条件获取的数据最多只有五千条
     * @param years
     * @param num
     * @return
     */
    public static List<ArticleField> getMediaArticles(String years,Integer num){
        String year = "(type:1) AND (years:"+years+") AND ";
        String newRules=year+RULES;
        VipSearchByObject obj = new VipSearchByObject();
        obj.setObjectName(VipSearchEnum.ARTICLE);
        obj.setPageNum(num);
        obj.setPageSize(SZIE);
        obj.setRules(newRules);
        PageObject<ArticleField> list = VipSearchByObjectService.resultByObject(obj, ArticleField.class);
        return list.getRows();
    }

    public static Integer getTotal(String years){
        String year = "(type:1) AND (years:"+years+") AND ";
        String newRules=year+RULES;
        VipSearchByObject obj = new VipSearchByObject();
        obj.setObjectName(VipSearchEnum.ARTICLE);
        obj.setPageNum(1);
        obj.setPageSize(1);
        obj.setRules(newRules);
        PageObject<ArticleField> list = VipSearchByObjectService.resultByObject(obj, ArticleField.class);
        return list.getTotal();
    }

}