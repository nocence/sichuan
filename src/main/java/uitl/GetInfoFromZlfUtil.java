package uitl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cqvip.vipcloud.model.dto.PageObject;
import com.cqvip.vipcloud.model.dto.function.VipSearchByObject;
import com.cqvip.vipcloud.model.dto.result.ArticleField;
import com.cqvip.vipcloud.model.enums.VipSearchEnum;
import com.cqvip.vipcloud.service.VipSearchByObjectService;
import com.google.common.hash.Hashing;
import model.*;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ZlfGetMediaService
 * @Description 获取智立方接口数据
 * @Author Innocence
 * @Date 2020/5/22 17:08
 * @Version 1.0
 */
public class GetInfoFromZlfUtil {

    public static final String RULES="((title_c:藏传佛教 OR keyword_c:藏传佛教 OR title_e:藏传佛教 OR keyword_e:藏传佛教 OR remark_e:藏传佛教 OR remark_c:藏传佛教)"
            + " OR (title_c:lamaism OR keyword_c:lamaism OR title_e:lamaism OR keyword_e:lamaism OR remark_e:lamaism OR remark_c:lamaism)"
            + " OR (title_c:tibetan buddhism OR keyword_c:tibetan buddhism OR title_e:tibetan buddhism OR keyword_e:tibetan buddhism "
            + "OR remark_e:tibetan buddhism OR remark_c:tibetan buddhism))";

    public static final Integer SZIE=100;

    public static final String MACTHER ="[\\[\\d\\]]";

    /**
     * 获取文章列表
     * @author Innocence
     * @date 2020/7/15
     * @param num, type 
     * @return java.util.List<com.cqvip.vipcloud.model.dto.result.ArticleField>
     */
    public List<ArticleField> getArticles(Integer num,String type){
        VipSearchByObject obj = new VipSearchByObject();
        obj.setObjectName(VipSearchEnum.ARTICLE);
        obj.setPageNum(num);
        obj.setPageSize(SZIE);
        if ("3".equals(type)){
            obj.setRules("(type:"+type+") AND (language:1)"+RULES);
        }else{
            obj.setRules("(type:"+type+") AND "+RULES);
        }
        PageObject<ArticleField> list = VipSearchByObjectService.resultByObject(obj, ArticleField.class);
        List<ArticleField> rows = list.getRows();
        return rows;
    }


    /**
     * 加载数据到文献
     * @param list
     * @return
     */
    public <T extends AbstractArticle> List<T> loadArticles(List<ArticleField> list, String type){
        if (list.isEmpty() || list.size()<=0){
            throw new NullPointerException("原始文献列表不能为空");
        }
        List artList=null;
        if ("1".equals(type)){
            artList=new ArrayList<MediaInfo>();
            for (ArticleField a:list) {
                MediaInfo media = new MediaInfo();
                media.setYears(a.getYears());
                media.setTitleC(a.getTitle_c());
                media.setMedia_c(a.getMedia_c());
                media.setMedia_e(a.getMedia_e());
                media.setMediasQk(a.getMedias_qk());
                String showwriter = a.getShowwriter();
                if (!StringUtils.isBlank(showwriter)){
                    media.setShowwriter(showwriter.replaceAll(MACTHER,"").trim());
                }
                media.setShowOrgan(a.getShoworgan().replaceAll(MACTHER,"").trim());
                media.setAuthorE(a.getAuthor_e());
                media.setRemarkC(a.getRemark_c());
                media.setRemarkE(a.getRemark_e());
                media.setKeywordC(a.getKeyword_c().replaceAll(MACTHER,"").trim());
                media.setClasstypes(a.getClasstypes().replaceAll(MACTHER,"").trim());
                media.setId(a.getId());
                media.setVolumn(a.getVolumn());
                media.setSpecialnum(a.getSpecialnum());
                media.setSubjectnum(a.getSubjectnum());
                media.setGch(a.getGch());
                media.setVol(a.getVol());
                media.setNum(a.getNum());
                String mediaFull = getFull(media);
                media.setFulltextaddress(mediaFull);

                String relateids = a.getRelateids();
                if (!StringUtils.isBlank(relateids)){
                    List<String> ids = getIds(relateids);
                    String rules="lngid:"+ids.get(0);
                    for (int i=1;i<ids.size();i++) {
                        rules+=" OR lngid:"+ids.get(i);
                    }
                    JSONArray array = getRelateArticle(ids.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getMediaC()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if (relatedArticles.contains("null")){
                        relateText  =  relateText.replace("null","");
                    }
                    media.setRelateText(relateText);
                }
                artList.add(media);
            }
        }else if ("2".equals(type)){
            artList=new ArrayList<DegreeInfo>();
            for (ArticleField a:list){
                DegreeInfo degree = new DegreeInfo();
                degree.setTitleC(a.getTitle_c());
                degree.setFirstWriter(a.getFirstwriter());
                degree.setBstutorsname(a.getBstutorsname());
                degree.setFirstOrgan(a.getFirstorgan());
                degree.setBsspeciality(a.getBsspeciality());
                degree.setBsdegree(a.getBsdegree());
                degree.setRemarkC(a.getRemark_c());
                degree.setYears(a.getYears());
                degree.setKeywordC(a.getKeyword_c().replaceAll(MACTHER,"").trim());
                degree.setKeywordE(a.getKeyword_e());
                degree.setClasstypes(a.getClasstypes().replaceAll(MACTHER,"").trim());
                degree.setFulltextaddress(a.getFulltextaddress());
                //参考文献
                String referids_real = a.getReferids_real();
                if (!StringUtils.isBlank(referids_real)){
                    List<String> otherIds = getOtherIds(referids_real);
                    String rules="lngid:"+otherIds.get(0);
                    for (int i=1;i<otherIds.size();i++) {
                        rules+=" OR lngid:"+otherIds.get(i);
                    }
                    JSONArray array = getRelateArticle(otherIds.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    degree.setReferText(relateText);
                }
                //二级参考文献
                String referids_sec = a.getReferids_sec();
                if (!StringUtils.isBlank(referids_sec)){
                    List<String> otherIds = getOtherIds(referids_sec);
                    String rules="lngid:"+otherIds.get(0);
                    for (int i=1;i<otherIds.size();i++) {
                        rules+=" OR lngid:"+otherIds.get(i);
                    }
                    JSONArray array = getRelateArticle(otherIds.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getFirstOrgan()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    degree.setReferids_sec_text(relateText);
                }
                //相关文献
                String relateids = a.getRelateids();
                if (!StringUtils.isBlank(relateids)){
                    List<String> ids = getIds(relateids);
                    String rules="lngid:"+ids.get(0);
                    for (int i=1;i<ids.size();i++) {
                        rules+=" OR lngid:"+ids.get(i);
                    }
                    JSONArray array = getRelateArticle(ids.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getMediaC()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    degree.setRelateText(relateText);
                }
                //被引文章
                String strbyids = a.getStrbyids();
                if (!StringUtils.isBlank(strbyids)){
                    List<String> otherIds = getOtherIds(strbyids);
                    String rules="lngid:"+otherIds.get(0);
                    for (int i=1;i<otherIds.size();i++) {
                        rules+=" OR lngid:"+otherIds.get(i);
                    }
                    JSONArray array = getRelateArticle(otherIds.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getFirstOrgan()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    degree.setStrbyidsText(relateText);
                }
                artList.add(degree);
            }
        }else if ("3".equals(type)){
            artList=new ArrayList<Meeting>();
            for (ArticleField a:list){
                Meeting meeting = new Meeting();
                meeting.setTitleC(a.getTitle_c());
                meeting.setFirstWriter(a.getFirstwriter().replaceAll(MACTHER,"").trim());
                meeting.setFirstOrgan(a.getFirstorgan());
                meeting.setMedia_c(a.getMedia_c());
                meeting.setHymeetingrecordname(a.getHymeetingrecordname());
                meeting.setHymeetingdate(a.getHymeetingdate());
                meeting.setHymeetingplace(a.getHymeetingplace());
                meeting.setRemarkC(a.getRemark_c());
                meeting.setKeywordC(a.getKeyword_c().replaceAll(MACTHER,"").trim());
                meeting.setClasstypes(a.getClasstypes().replaceAll(MACTHER,"").trim());
                meeting.setFulltextaddress(a.getFulltextaddress());
                //参考文献
                String referids_real = a.getReferids_real();
                if (!StringUtils.isBlank(referids_real)){
                    List<String> otherIds = getOtherIds(referids_real);
                    String rules="lngid:"+otherIds.get(0);
                    for (int i=1;i<otherIds.size();i++) {
                        rules+=" OR lngid:"+otherIds.get(i);
                    }
                    JSONArray array = getRelateArticle(otherIds.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    meeting.setReferText(relateText);
                }
                //二级参考文献
                String referids_sec = a.getReferids_sec();
                if (!StringUtils.isBlank(referids_sec)){
                    List<String> otherIds = getOtherIds(referids_sec);
                    String rules="lngid:"+otherIds.get(0);
                    for (int i=1;i<otherIds.size();i++) {
                        rules+=" OR lngid:"+otherIds.get(i);
                    }
                    JSONArray array = getRelateArticle(otherIds.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getFirstOrgan()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    meeting.setReferids_sec_text(relateText);
                }
                //相关文献
                String relateids = a.getRelateids();
                if (!StringUtils.isBlank(relateids)){
                    List<String> ids = getIds(relateids);
                    String rules="lngid:"+ids.get(0);
                    for (int i=1;i<ids.size();i++) {
                        rules+=" OR lngid:"+ids.get(i);
                    }
                    JSONArray array = getRelateArticle(ids.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getMediaC()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    meeting.setRelateText(relateText);
                }
                //被引文章
                String strbyids = a.getStrbyids();
                if (!StringUtils.isBlank(strbyids)){
                    List<String> otherIds = getOtherIds(strbyids);
                    String rules="lngid:"+otherIds.get(0);
                    for (int i=1;i<otherIds.size();i++) {
                        rules+=" OR lngid:"+otherIds.get(i);
                    }
                    JSONArray array = getRelateArticle(otherIds.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getFirstOrgan()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    meeting.setStrbyidsText(relateText);
                }
                artList.add(meeting);
            }
        }else if ("7".equals(type)){
            artList=new ArrayList<Focus>();
            for (ArticleField a:list){
                Focus focus = new Focus();
                focus.setTitleC(a.getTitle_c());
                focus.setFirstWriter(a.getFirstwriter());
                focus.setTspress(a.getTspress());
                focus.setTspubdate(a.getTspubdate());
                focus.setTsprovinces(a.getTsprovinces());
                focus.setTsseriesname(a.getTsseriesname());
                focus.setPagecount(a.getPagecount());
                focus.setTsprice(a.getTsprice());
                focus.setTsisbn(a.getTsisbn());
                focus.setRemarkC(a.getRemark_c());
                focus.setKeywordC(a.getKeyword_c().replaceAll(MACTHER,"").trim());
                focus.setClasstypes(a.getClasstypes().replaceAll(MACTHER,"").trim());
                focus.setFulltextaddress(a.getFulltextaddress());

                //参考文献
                String referids_real = a.getReferids_real();
                if (!StringUtils.isBlank(referids_real)){
                    List<String> otherIds = getOtherIds(referids_real);
                    String rules="lngid:"+otherIds.get(0);
                    for (int i=1;i<otherIds.size();i++) {
                        rules+=" OR lngid:"+otherIds.get(i);
                    }
                    JSONArray array = getRelateArticle(otherIds.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    focus.setReferText(relateText);
                }
                //二级参考文献
                String referids_sec = a.getReferids_sec();
                if (!StringUtils.isBlank(referids_sec)){
                    List<String> otherIds = getOtherIds(referids_sec);
                    String rules="lngid:"+otherIds.get(0);
                    for (int i=1;i<otherIds.size();i++) {
                        rules+=" OR lngid:"+otherIds.get(i);
                    }
                    JSONArray array = getRelateArticle(otherIds.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getFirstOrgan()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    focus.setReferids_sec_text(relateText);
                }
                //相关文献
                String relateids = a.getRelateids();
                if (!StringUtils.isBlank(relateids)){
                    List<String> ids = getIds(relateids);
                    String rules="lngid:"+ids.get(0);
                    for (int i=1;i<ids.size();i++) {
                        rules+=" OR lngid:"+ids.get(i);
                    }
                    JSONArray array = getRelateArticle(ids.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getMediaC()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    focus.setRelateText(relateText);
                }
                //被引文章
                String strbyids = a.getStrbyids();
                if (!StringUtils.isBlank(strbyids)){
                    List<String> otherIds = getOtherIds(strbyids);
                    String rules="lngid:"+otherIds.get(0);
                    for (int i=1;i<otherIds.size();i++) {
                        rules+=" OR lngid:"+otherIds.get(i);
                    }
                    JSONArray array = getRelateArticle(otherIds.size(), rules);
                    List<RelatedArticles> relatedArticles = loadRelated(array);
                    String relateText=null;
                    for (RelatedArticles re:relatedArticles) {
                        relateText+=re.getShowWriter()+","+re.getTitleC()+","+re.getFirstOrgan()+","+re.getYears()+","+re.getNum()
                                +","+re.getBeginPage()+"-"+re.getBeginPage()+";";
                    }
                    if(relateText!=null && relateText.contains("null")){
                        relateText = relateText.replace("null", "");
                    }
                    focus.setStrbyidsText(relateText);
                }
                artList.add(focus);
            }
        }else if ("10".equals(type)){
            artList=new ArrayList<Policies>();
            for (ArticleField a:list){
                Policies policies = new Policies();
                policies.setTitleC(a.getTitle_c());
                policies.setMedia_c(a.getMedia_c());
                policies.setYears(a.getYears());
                policies.setFirstOrgan(a.getFirstorgan());
                policies.setRemarkC(a.getRemark_c());
                policies.setFulltextaddress(a.getFulltextaddress());
                artList.add(policies);
            }
        }
        return artList;
    }

    /**
     * 获取关联文献
     * @param rule
     * @return
     */
    public JSONArray getRelateArticle(Integer rows,String rule)  {
        VipSearchByObject search = new VipSearchByObject();
        search.setObjectName(VipSearchEnum.ARTICLE);
        search.setPageNum(1);
        search.setPageSize(rows);
        search.setRules(rule);
        String s = VipSearchByObjectService.resultByJson(search, ArticleField.class);
        JSONArray objects = JSONObject.parseArray(s);
        return objects;
    }

    /**
     * 装载关联文献
     * @param arrs
     * @return
     */
    public List<RelatedArticles> loadRelated(JSONArray  arrs){
        if (arrs==null){
            return null;
        }
        ArrayList<RelatedArticles> list = new ArrayList<>();
        for (Object json:arrs) {
            RelatedArticles related = new RelatedArticles();
            JSONObject obj = JSONObject.parseObject(json.toString());
            related.setLngid(obj.getString("_id"));
            related.setShowWriter(obj.getString("showwriter"));
            related.setTitleC(obj.getString("title_c"));
            related.setMediaC(obj.getString("media_c"));
            related.setYears(obj.getString("years"));
            related.setNum(obj.getString("num"));
            related.setBeginPage(obj.getString("beginpage"));
            related.setEndPage(obj.getString("endpage"));
            related.setFirstOrgan(obj.getString("firstorgan"));
            related.setHymeetingrecordname(obj.getString("hymeetingrecordname"));
            list.add(related);
        }
        return list;
    }

    /**
     * 关联文献id字符串处理
     * @param s
     * @return
     */
    public List<String> getIds(String s){
        String[] split = s.split(";");
        List<String > list = new ArrayList<>();
        for (String str:split) {
            String sub = str.substring(str.indexOf("@")+1);
            String[] split1 = sub.split(",");
            for (String str1:split1) {
                list.add(str1);
            }
        }
        return list;
    }

    /**
     * 非关联文献 ids获取
     * @author Innocence
     * @date 2020/7/15
     * @param s
     * @return java.util.List<java.lang.String>
     */
    public List<String> getOtherIds(String s){
        String[] split = s.split(";");
        List<String > list = new ArrayList<>();
        for (String str:split){
            list.add(str);
        }
        return list;
    }

    public String getFull(MediaInfo  media){
        String s = mediaFull(media);
        if(StringUtils.isBlank(s)){
            return null;
        }
        String s1 = evaluateQK(s);
        if (StringUtils.isBlank(s1)){
            return null;
        }
        String year = specialStrByZk(media.getYears());

        return year+s1;
    }

    /** 1、
     * 拼接 期刊文章全文
     * @author Innocence
     * @date 2020/7/17
     * @param media
     * @return java.lang.String
     */
    public String mediaFull(MediaInfo media){
        String full="/";
        String volumn = media.getVolumn();
        if (StringUtils.isBlank(volumn))return null;
        String specialnum = media.getSpecialnum();
        if(StringUtils.isBlank(specialnum))return null;
        String subjectnum = media.getSubjectnum();
        if (StringUtils.isBlank(subjectnum))return null;
        String gch = media.getGch();
        if (StringUtils.isBlank(gch))return null;
        String vol = media.getVol();
        if(StringUtils.isBlank(vol)){
            vol="000";
        }
        if (vol.length()==1){
            vol="00"+vol;
        }else if (vol.length()==2){
            vol="0"+vol;
        }
        String num = media.getNum();
        if(StringUtils.isBlank(num))return null;
        if (num.length()==1){
            num="00"+num;
        }else if (num.length()==2){
            num="0"+num;
        }
        String id = media.getId();
        full+=volumn+"/"+specialnum+subjectnum+"/"+gch+"/"+vol+"/"+num+"/"+id+".pdf";
        return full;
    }

    /**
     * 2
     * @author Innocence
     * @date 2020/7/17
     * @param source
     * @return java.lang.String
     */
    private String evaluateQK(String source){
        if(StringUtils.isBlank(source) ){
            return null;
        }
        Pattern pattern = Pattern.compile("([A-Za-z0-9]+)\\.[pdfPDF]{3}");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            String lngid = matcher.group(1);
            return "/" + getPartOfHash(lngid) + "/" + lngid + ".pdf";
        }
        return null;
    }

    private static String getPartOfHash(String lngid){
        String n = lngid.toLowerCase() + ".pdf";
        String hashHex = Hashing.murmur3_128().hashString(n, StandardCharsets.UTF_8).toString().toUpperCase();

        String l = StringUtils.substring(hashHex,0,2);
        String r = StringUtils.substring(hashHex,2,3);

        if (r!=null) {
            int numR;
            try {
                //是数字
                numR = Integer.parseInt(r);
                r = String.valueOf(numR % 5);
            } catch (NumberFormatException e) {
                r = r.replace("D","A")
                        .replace("E","B")
                        .replace("F","C");
            }

        }
//        Integer numR = Convert.toInteger(r,null);
//        //是数字
//        if(numR != null){
//            r = Convert.toString(numR % 5);
//        }else{
//            r = r.replace("D","A")
//                    .replace("E","B")
//                    .replace("F","C");
//        }
        return l + r;
    }

    private String specialStr(String path){
        String lastChar = path.substring(path.length()-1);
        if ("/".equals(lastChar)||"\\".equals(lastChar)) {
            return path;
        }
        return path+"/";
    }

    /**  拼接年份
     * 功能描述
     * @author Innocence
     * @date 2020/7/17
     * @param year
     * @return java.lang.String
     */
    private String specialStrByZk(String year){
        int yearNum = Integer.parseInt(year);
        if (yearNum<1989) {
            return "before1989";
        }else {
            return yearNum+"pdf";
        }
    }

}