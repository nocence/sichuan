package model;

import lombok.Data;


/**
 * @ClassName RelatedArticles
 * @Description 保存相关文献信息
 * @Author Innocence
 * @Date 2020/5/25 15:26
 * @Version 1.0
 */
@Data
public class RelatedArticles {

    private String lngid;

    //"规范前作者"
    private String showWriter;

    //"中文标题")
    private String titleC;

    //("所属媒体中文名")
    private String mediaC;

    //("年")
    private String years;

    //("期")
    private String num;

    //("开始页")
    private String beginPage;

    //("结束页")
    private String endPage;

    //机构
    private String firstOrgan;

    //会议文献
    private String hymeetingrecordname;
}