package model;

import lombok.Data;

/**
 * @ClassName DegreeInfo
 * @Description 学位论文 type=2
 * @Author Innocence
 * @Date 2020/7/15 13:49
 * @Version 1.0
 */
@Data
public class DegreeInfo extends AbstractArticle{

    //学科专业
    private String bsspeciality;

    //学位
    private String bsdegree;

    //导师姓名
    private String bstutorsname;

}