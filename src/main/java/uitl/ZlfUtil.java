package uitl;

import com.cqvip.vipcloud.model.dto.PageObject;
import com.cqvip.vipcloud.model.dto.function.VipSearchByObject;
import com.cqvip.vipcloud.model.dto.result.ArticleField;
import com.cqvip.vipcloud.model.enums.VipSearchEnum;
import com.cqvip.vipcloud.service.VipSearchByObjectService;

/**
 * @ClassName ZlfUtil
 * @Description TODO
 * @Author Innocence
 * @Date 2020/7/14 15:39
 * @Version 1.0
 */
public class ZlfUtil {

    public static final String RULES="(title_c:藏传佛教 OR keyword_c:藏传佛教 OR title_e:藏传佛教 OR keyword_e:藏传佛教 OR remark_e:藏传佛教 OR remark_c:藏传佛教)"
            + " OR (title_c:lamaism OR keyword_c:lamaism OR title_e:lamaism OR keyword_e:lamaism OR remark_e:lamaism OR remark_c:lamaism)"
            + " OR (title_c:tibetan buddhism OR keyword_c:tibetan buddhism OR title_e:tibetan buddhism OR keyword_e:tibetan buddhism "
            + "OR remark_e:tibetan buddhism OR remark_c:tibetan buddhism)";

    public static void getArtcilesFromZlf(){
        VipSearchByObject obj = new VipSearchByObject();
        obj.setObjectName(VipSearchEnum.ARTICLE);
        obj.setPageNum(10);
        obj.setPageSize(1);
        obj.setRules("(type:2) AND "+RULES);
        PageObject<ArticleField> list = VipSearchByObjectService.resultByObject(obj, ArticleField.class);
        System.out.println("list = " + list);
    }

    public static void main(String[] args) {
        getArtcilesFromZlf();
    }
}