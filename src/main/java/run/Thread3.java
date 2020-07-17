package run;

import model.AbstractArticle;
import uitl.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

import static run.ExportTest.getMeeting;

/**
 * @ClassName Thread3
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/17 10:42
 * @Version 1.0
 */
public class Thread3 implements Runnable{
    private static List<String> meetingList;
    static {
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
    }
    @Override
    public void run() {
        List<AbstractArticle> meeting = getMeeting();
        try {
            ExcelUtil.toExcel(meetingList,meeting,"3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}