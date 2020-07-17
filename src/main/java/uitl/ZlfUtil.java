package uitl;

import com.cqvip.vipcloud.model.dto.PageObject;
import com.cqvip.vipcloud.model.dto.function.VipSearchByObject;
import com.cqvip.vipcloud.model.dto.result.ArticleField;
import com.cqvip.vipcloud.model.enums.VipSearchEnum;
import com.cqvip.vipcloud.service.VipSearchByObjectService;

import static uitl.GetInfoFromZlfUtil.RULES;

/**
 * @ClassName ZlfUtil
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/14 15:39
 * @Version 1.0
 */
public class ZlfUtil {

    public static void getArtcilesFromZlf(){
        VipSearchByObject obj = new VipSearchByObject();
        obj.setObjectName(VipSearchEnum.ARTICLE);
        obj.setPageNum(1);
        obj.setPageSize(10);
        obj.setRules("(type:1) AND "+RULES);
        PageObject<ArticleField> list = VipSearchByObjectService.resultByObject(obj, ArticleField.class);
        System.out.println("list = " + list);
    }

    public static void main(String[] args) {

        getArtcilesFromZlf();

    }
}