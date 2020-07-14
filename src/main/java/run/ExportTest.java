package run;

import model.BusinessResourceOrderRespVO;
import uitl.ExcelUtil;

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
    public static void main(String[] args) {
        List<String> title=new ArrayList();
        title.add("订单编号");
        title.add("买家");

        List<BusinessResourceOrderRespVO> orderRespVOS = new ArrayList<BusinessResourceOrderRespVO>();
        for (int i = 0; i < 10; i++) {
            BusinessResourceOrderRespVO vo = new BusinessResourceOrderRespVO();
            vo.setOrderCode(String.valueOf(i));
            vo.setSupplierName("test"+String.valueOf(i));
            orderRespVOS.add(vo);
        }
        try{
            ExcelUtil.toExcel(title,orderRespVOS);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}