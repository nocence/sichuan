package model;

import lombok.Data;

/**
 * @ClassName ZlfArticles
 * @Description 期刊文章 type=1；会议
 * @Author Innocence
 * @Date 2020/5/22 10:16
 * @Version 1.0
 */
@Data
public class MediaInfo extends AbstractArticle{

    //中英文期刊名
    private String media_c;
    private String media_e;

    //期刊年期卷，页数信息
    private String mediasQk;




}