package model;

import lombok.Data;

/**
 * @ClassName Focus
 * @Description 专著 type = 7
 * @Author Innocence
 * @Date 2020/7/15 14:00
 * @Version 1.0
 */
@Data
public class Focus extends AbstractArticle{
    //出版社
    private String tspress;

    //出版日期
    private String tspubdate;

    //出版地
    private String tsprovinces;

    //丛书名
    private String tsseriesname;

    //纸书定价
    private String tsprice;

    //isbn号
    private String tsisbn;

    //页数
    private String pagecount;


}
