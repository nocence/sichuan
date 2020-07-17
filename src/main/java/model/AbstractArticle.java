package model;

import lombok.Data;

/**
 * @ClassName AbstactArticle
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/15 13:26
 * @Version 1.0
 */
@Data
public abstract class AbstractArticle {

    //("中文标题")
    private String titleC;

    //("英文标题")
    private String titleE;

    //("中文关键词")
    private String keywordC;

    //("英文关键词")
    private String keywordE;

    //("中文摘要")
    private String remarkC;

    //("英文摘要")
    private String remarkE;

    //("领域")
    private String classtypes;

    //("第一作者")
    private String firstWriter;

    //作者
    private String showwriter;

    //("英文作者")
    private String authorE;

    //("英文机构")
    private String organE;

    //("第一机构")
    private String firstOrgan;

    //所有机构
    private String showOrgan;

    //("主题")
    private String subjects;

    //("地区")
    private String areas;

    //年
    private String years;

    //("相关文献id")
    private String relateids;

    //相关文献信息（需要拼接）
    private String relateText;

    //参考文献id串
    private String referids_real;

    //参考文献信息（需要拼接）
    private String referText;

    //二级参考文献id串
    private String referids_sec;

    //二级参考文献信息
    private String  referids_sec_text;

    //被引文章id串
    private String strbyids;

    //被引文章信息
    private String strbyidsText;

    private String id;

    private String volumn;

    private String specialnum;

    private String subjectnum;

    private String gch;

    private String vol;

    private String num;

    private String fulltextaddress;


    @Override
    public int hashCode(){
        String in = titleC;
        return in.hashCode();
    }

}