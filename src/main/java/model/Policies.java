package model;

import lombok.Data;

/**
 * @ClassName Policies
 * @Description 政策法规 type= 10
 * @Author Innocence
 * @Date 2020/7/15 14:06
 * @Version 1.0
 */
@Data
public class Policies extends AbstractArticle {

    //分类内容
    private String media_c;

    //效力级别
    private String  fgEffect;

    //时效性
    private String  fgTimeliness;

    //颁发日期
    private String fgPubData;

    //实施日期
    private String fgImpData;
}