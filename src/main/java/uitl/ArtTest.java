package uitl;

import com.cqvip.vipcloud.model.dto.PageObject;
import com.cqvip.vipcloud.model.dto.function.VipSearchByObject;
import com.cqvip.vipcloud.model.dto.result.ArticleField;
import com.cqvip.vipcloud.model.enums.VipSearchEnum;
import com.cqvip.vipcloud.service.VipSearchByObjectService;

import static uitl.GetInfoFromZlfUtil.RULES;

/**
 * @ClassName ArtTest
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/17 9:45
 * @Version 1.0
 */
public class ArtTest {
    public static void main(String[] args) {
        VipSearchByObject obj = new VipSearchByObject();
        obj.setObjectName(VipSearchEnum.ARTICLE);
        obj.setPageNum(1);
        obj.setPageSize(1);
        obj.setRules("lngid:666929537");
        PageObject<ArticleField> list = VipSearchByObjectService.resultByObject(obj, ArticleField.class);
        System.out.println("list = " + list);
    }
}