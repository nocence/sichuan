package model;

import lombok.Data;

/**
 * @ClassName Meeting
 * @Description 会议论文 type = 3 政策法规
 * @Author Innocence
 * @Date 2020/7/15 14:05
 * @Version 1.0
 */
@Data
public class Meeting extends AbstractArticle{

    //会议名称
    private String media_c;

    //会议记录（会议文献）
    private String hymeetingrecordname;

    //会议日期
    private String hymeetingdate;

    //会议地点
    private String hymeetingplace;

}