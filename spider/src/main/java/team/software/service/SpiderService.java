package team.software.service;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.software.bean.*;
import team.software.mapper.SpiderMapper;
import team.software.util.BaseUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huao
 * @ClassName SpiderService.java
 * @Description TODO
 * @createTime 2021年04月02日 17:10:00
 */
@Service(value = "team.software.service.SpiderService")
public class SpiderService {

    @Resource
    private SpiderMapper spiderMapper;

    private final static String PREFIX = "http://www.btwuji.com/html/gndy/dyzz";

    /**
     * 创建html bean
     * @param url 电影详细信息页面地址
     * @return bean
     */
    private HtmlBean htmlBean(String url){
        HtmlBean bean = new HtmlBean();
        bean.setUrl(url);
        bean.setCreate_time(BaseUtil.getNowTime());
        bean.setState("0");
        return bean;
    }

    private String getHtml(String url) throws IOException {
        WebClient webClient = new WebClient();
        webClient.addRequestHeader("Referer","https://movie.douban.com/");
        webClient.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
        webClient.addRequestHeader("Accept", "*/*");
        webClient.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage linkPage = webClient.getPage(url);
        //html页面
        String html = linkPage.asXml().toString();
        webClient.close();
        return html;
    }

    /**
     * 爬取链接数据
     */
    public void spiderStart() throws Exception {
        String page = PREFIX+"/list_23_";
        int flag = 0;
        //数据库最近一次插入的页面链接
        String lastUrl = this.spiderMapper.queryLastUrl();
        while (true){
            List<HtmlBean> urlList = new ArrayList<>();
            flag++;
            //从新的的页面开始爬数据
            String pageLink = page + flag + ".html";
            String pageLinkRegex = "<a href=\"/html/gndy/dyzz(.+?.html)\" class=\"ulink\">";
		    Pattern pageLinkPattern = Pattern.compile(pageLinkRegex);
            String html = getHtml(pageLink);
            Matcher linkMatcher = pageLinkPattern.matcher(html);
            while (linkMatcher.find()) {
                String url = PREFIX+linkMatcher.group(1).trim();
                //最新的页面和数据库最后一次插入的页面一致，说明是最新数据
                if (!BaseUtil.isEmpty(lastUrl) && lastUrl.equals(url)){
                    return;
                }
                urlList.add(htmlBean(url));
            }
            this.spiderMapper.addHtml(urlList);
        }
    }

    public void solveData() throws InterruptedException {
        int i = 0;
        List<HtmlBean> htmlList = this.spiderMapper.queryUnSolveHtml();
        for (HtmlBean htmlBean : htmlList){
            try {
                this.solveSpiderData(htmlBean);
                i=0;
            }catch (Exception e){
                i++;
                e.printStackTrace();
                Thread.sleep(10000);
            }
            int wait = new Random().nextInt(6) + 5;
            Thread.sleep(wait*1000);
            if (i > 3){
                Thread.sleep(30000);
            }else if (i > 5){
                Thread.sleep(60000);
            }
        }
    }

    /**
     * 处理爬取的链接
     * 将处理后的数据写入数据库
     */
    public void solveSpiderData(HtmlBean bean) throws IOException {
        String url = bean.getUrl();
        //电影译名
        String translatedTermReg = "<br/>.*?◎译　　名(.+?)<br/>";
        //电影片名
        String nameReg = "<br/>.*?◎片　　名(.+?)<br/>";
        //电影年代
        String decadeReg = "<br/>.*?◎年　　代(.+?)<br/>";
        //电影字幕
        String subtitleReg = "<br/>.*?◎字　　幕(.+?)<br/>";
        String html = this.getHtml(url);
        Map<String,String> param = new HashMap<>();
        nameParameter(translatedTermReg,"translated_term",html,param);
        nameParameter(nameReg,"name",html,param);
        nameParameter(decadeReg,"decade",html,param);
        nameParameter(subtitleReg,"subtitle",html,param);
        linkParameter(html,param);
        String name = param.get("translated_term");
        String CNReg = "^[\\u4e00-\\u9fa5].*";
        if (!name.matches(CNReg)){
            name = param.get("name");
        }
        param.put("html_id",bean.getId());
        this.doubanParameter(name,param);
    }

    private void nameParameter(String regex,String field,String html,Map<String,String> param){
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String value = "";
        while (matcher.find()) {
            try{
                value = matcher.group(1).trim().replaceAll((char)12288+"","").replaceAll((char)160+"", "").replaceAll("  "," ");
            }catch (Exception e){
                break;
            }
        }
        if (!BaseUtil.isEmpty(value)){
            param.put(field,value);
            return;
        }
        String reg = "";
        if ("translated_term".equals(field)){
            reg = "◎译　　名(.+?)</div>";
        }else if ("name".equals(field)){
            reg = "◎片　　名(.+?)</div>";
        }else if ("decade".equals(field)){
            reg = "◎年　　代(.+?)</div>";
        }else if ("subtitle".equals(field)){
            reg = "◎字　　幕(.+?)</div>";
        }
        pattern = Pattern.compile(reg,Pattern.DOTALL);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            try{
                value = matcher.group(1).trim().replaceAll((char)12288+"","").replaceAll((char)160+"", "").replaceAll("  "," ");
                param.put(field,value);
            }catch (Exception e){
                break;
            }
        }
    }

    /**
     * 设置参数
     * @param regex 正则表达式
     * @param field 字段名称
     * @param html 爬取的页面
     * @param param 参数map
     */
    private void setParameter(String regex,String field,String html, Map<String,String> param){
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String release_time = "";
        while (matcher.find()) {
            try{
                String value = matcher.group(1).trim().replaceAll((char)12288+"","").replaceAll((char)160+"", "").replaceAll("  "," ");
                if ("info".equals(field)){
                    value = value.replaceAll("\r|\n|", "").replaceAll("  ","");
                }else if ("length".equals(field)){
                    value += "分钟";
                }else if ("release_time".equals(field)){
                    release_time += "/"+value;
                }else if ("language".equals(field)){
                    value = value.replaceAll("</span>","").replaceAll("\\s","");
                }
                param.put(field,value);
            }catch (Exception e){
                break;
            }
        }
        if ("release_time".equals(field)){
            try {
                param.put(field,release_time.substring(1));
            }catch (Exception e){
                param.put(field,null);
            }
        }
    }

    private void doubanParameter(String filmName,Map<String,String> param) throws IOException {
        //搜索页面
        String html = this.getHtml("https://www.douban.com/search?q="+filmName);
//        System.out.println(html);
        String regex = "<a class=\"nbg\" href=\"(.+?)\" target=\"_blank\"";
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String infoLink = "";
        while (matcher.find()){
            infoLink = matcher.group(1).trim();
            break;
        }
        //电影详情页面界面
        String infoHtml = this.getHtml(infoLink);
//        System.out.println(infoHtml);
        String nameReg = "<span property=\"v:itemreviewed\">(.+?)</span>";
        String translatedTermReg = "又名:</span>(.+?)<br/>";
        String infoReg = "<span property=\"v:summary\".*?>(.+?)</span>";
        String doubanReg = "<strong class=\"ll rating_num\" property=\"v:average\">(.+?)</strong>";
        String release_timeReg = "<span property=\"v:initialReleaseDate\" content=\"(.+?)\">";
        String awardidReg = "<a href=\"https://movie.douban.com/subject/(\\d+)/awards/\">";
        String lengthReg = "<span property=\"v:runtime\" content=\"(\\d+)\">";
        String languageReg = "语言:(.+?)<br/>";
        String starringReg = "<a href=\"/celebrity/(\\d+)/\" rel=\"v:starring\">(.+?)</a>";
        String directorReg = "<a href=\"/celebrity/(\\d+)/\" rel=\"v:directedBy\">(.+?)</a>";
        String scriptwriterReg = "<a href=\"/celebrity/(\\d+)/\">(.+?)</a>";
        this.coverParameter(infoHtml,param);
        this.setParameter(nameReg,"name",infoHtml,param);
        this.setParameter(translatedTermReg,"translated_term",infoHtml,param);
        this.setParameter(infoReg,"info",infoHtml,param);
        this.setParameter(doubanReg,"douban",infoHtml,param);
        this.setParameter(release_timeReg,"release_time",infoHtml,param);
        this.setParameter(awardidReg,"awardid",infoHtml,param);
        this.setParameter(lengthReg,"length",infoHtml,param);
        this.setParameter(languageReg,"language",infoHtml,param);
        List<CategoryBean> categoryList = this.categoryParameter(infoHtml);
        List<AreaBean> areaList = this.areaParameter(infoHtml);
        List<TagBean> tagList = this.tagParameter(infoHtml);
        List<StarBean> directorList = this.starParameter(infoHtml,directorReg);
        List<StarBean> starringList = this.starParameter(infoHtml,starringReg);
        List<StarBean> scriptwriterList = this.starParameter(infoHtml,scriptwriterReg);
        List<AwardBean> awardList = this.awardsParamete(param.get("awardid"));

        List<String> categoryIdList = this.categoryIdList(categoryList);
        List<String> areaIdList = this.areaIdList(areaList);
        List<String> tagIdList = this.tagIdList(tagList);
        List<String> directorIdList = this.starList(directorList);
        List<String> scriptwriterIdList = this.starList(scriptwriterList);
        List<String> starringIdList = this.starList(starringList);
        awardList = this.awardIdList(awardList);

        //得到电影id
        String film_id = this.queryFilmId(param);
        if (BaseUtil.isEmpty(film_id)){
            return;
        }
        //非事务方法调用事务方法，会导致事务方法发生异常时不会回滚，使用代理对象解决此问题
        SpiderService proxyService = (SpiderService) AopContext.currentProxy();
        try{
            proxyService.solveFilm(film_id,param.get("html_id"),categoryIdList,areaIdList,tagIdList,directorIdList,scriptwriterIdList,starringIdList,awardList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String queryFilmId(Map<String, String> param) {
        String html_id = param.get("html_id");
        String film_id = this.spiderMapper.queryFilmId(html_id);
        if (BaseUtil.isEmpty(film_id)){
            param.put("upload_time",BaseUtil.getNowTime());
            param.put("hot","0");
            param.put("collect","0");
            try {
                this.spiderMapper.addFilm(param);
            }catch (Exception e){
                return null;
            }
            film_id = this.spiderMapper.queryFilmId(html_id);
        }
        return film_id;
    }

    /**
     * 将电影信息写入数据库
     * @param film_id
     * @param categoryIdList
     * @param areaIdList
     * @param tagIdList
     * @param directorIdList
     * @param scriptwriterIdList
     * @param starringIdList
     */
    @Transactional(rollbackFor = Exception.class)
    public void solveFilm(String film_id,String html_id, List<String> categoryIdList, List<String> areaIdList,
                          List<String> tagIdList, List<String> directorIdList, List<String> scriptwriterIdList,
                          List<String> starringIdList,List<AwardBean> awardList) {
        Map<String,Object> param = new HashMap<>();
        List<String> stateList = new ArrayList<>();
        stateList.add(html_id);
        if (BaseUtil.isEmptyList(directorIdList) || BaseUtil.isEmptyList(scriptwriterIdList)){
            this.spiderMapper.setHtmlStarErrorState(stateList);
            return;
        }
        param.put("film_id",film_id);
        param.put("categoryList",categoryIdList);
        param.put("areaList",areaIdList);
        param.put("tagList",tagIdList);
        param.put("directorList",directorIdList);
        param.put("scriptwriterList",scriptwriterIdList);
        param.put("starringList",starringIdList);
        this.spiderMapper.solveCategory(param);
        this.spiderMapper.solveArea(param);
        this.spiderMapper.solveTag(param);
        this.spiderMapper.solveDirector(param);
        this.spiderMapper.solveScriptwriter(param);
        this.spiderMapper.solveStarring(param);
        if (!BaseUtil.isEmptyList(awardList)){
            List<AwardInfoBean> awardInfoBeans = new ArrayList<>();
            for (AwardBean awardBean : awardList){
                String award_id = awardBean.getAward_id();
                List<String> contents = awardBean.getAward_content();
                for (String content : contents){
                    AwardInfoBean bean = new AwardInfoBean();
                    bean.setFilm_id(film_id);
                    bean.setAward_id(award_id);
                    bean.setAward_content(content);
                    awardInfoBeans.add(bean);
                }
            }
            param.put("awardList",awardInfoBeans);
            this.spiderMapper.solveAward(param);
        }
        this.spiderMapper.setHtmlState(stateList);
    }

    private List<AwardBean> awardIdList(List<AwardBean> awardList) {
        if (BaseUtil.isEmptyList(awardList)){
            return null;
        }
        List<AwardBean> beans = this.spiderMapper.queryAwardByName(awardList);
        //若查询结果为空或，表明数据库中无这些奖项，将这些奖项数据全部写入数据库
        if (beans == null || beans.size() == 0){
            this.spiderMapper.addAward(awardList);
            //得到新插入的奖项数据的主键id
            List<AwardBean> list = this.spiderMapper.queryAwardByName(awardList);
            Map<String,AwardBean> map = new HashMap<>();
            for (AwardBean bean : list){
                map.put(bean.getName(),bean);
            }
            for (AwardBean bean : awardList){
                AwardBean awardBean = map.get(bean.getName());
                bean.setId(awardBean.getId());
            }
            return awardList;
        }
        Map<String,AwardBean> map = new HashMap<>();
        for (AwardBean bean : beans){
            map.put(bean.getName(),bean);
        }
        //原来记录数据库中不存在的奖项数据
        List<AwardBean> beanList = new ArrayList<>();
        for (AwardBean bean : awardList){
            AwardBean awardBean = map.get(bean.getName());
            //该奖项数据不存在
            if (awardBean == null){
                beanList.add(bean);
            }
        }
        if (BaseUtil.isEmptyList(beanList)){
            for (AwardBean bean : awardList){
                AwardBean awardBean = map.get(bean.getName());
                bean.setAward_id(awardBean.getId());
            }
            return awardList;
        }
        //将没有的奖项信息插入数据库
        this.spiderMapper.addAward(beanList);
        //得到新插入的奖项数据的主键id
        List<AwardBean> list = this.spiderMapper.queryAwardByName(beanList);
        for (AwardBean bean : list){
            map.put(bean.getName(),bean);
        }
        for (AwardBean bean : awardList){
            AwardBean awardBean = map.get(bean.getName());
            bean.setAward_id(awardBean.getId());
        }
        return awardList;
    }

    private List<String> categoryIdList(List<CategoryBean> categoryList) {
        List<String> categoryIdList = new ArrayList<>();
        if (BaseUtil.isEmptyList(categoryList)){
            return null;
        }
        //数据库查询已有的类别
        List<CategoryBean> beans = this.spiderMapper.queryCategoryByName(categoryList);
        if (beans == null || beans.size() == 0){
            //若查询结果为空或，表明数据库中无这些类别，将这些类别数据全部写入数据库
            this.spiderMapper.addCategory(categoryList);
            //得到新插入的类别数据的主键id
            List<CategoryBean> list = this.spiderMapper.queryCategoryByName(categoryList);
            for (CategoryBean bean : list){
                categoryIdList.add(bean.getId());
            }
            return categoryIdList;
        }
        Map<String,CategoryBean> map = new HashMap<>();
        for (CategoryBean bean : beans){
            map.put(bean.getName(),bean);
        }
        //原来记录数据库中不存在的类别数据
        List<CategoryBean> beanList = new ArrayList<>();
        for (CategoryBean bean : categoryList){
            CategoryBean categoryBean = map.get(bean.getName());
            //该类别数据不存在
            if (categoryBean == null){
                beanList.add(bean);
            }else {
                categoryIdList.add(categoryBean.getId());
            }
        }
        if (BaseUtil.isEmptyList(beanList)){
            return categoryIdList;
        }
        //将没有的类别信息插入数据库
        this.spiderMapper.addCategory(beanList);
        //得到新插入的类别数据的主键id
        List<CategoryBean> list = this.spiderMapper.queryCategoryByName(beanList);
        for (CategoryBean bean : list){
            categoryIdList.add(bean.getId());
        }
        return categoryIdList;
    }

    /**
     * 获得地区名对应的主键id并返回
     * @param areaList 一部电影的所有制片国家/地区名
     * @return 地区主键id
     */
    private List<String> areaIdList(List<AreaBean> areaList){
        List<String> areaIdList = new ArrayList<>();
        if (BaseUtil.isEmptyList(areaList)){
            return null;
        }
        //数据库查询已有的地区
        List<AreaBean> beans = this.spiderMapper.queryAreaByName(areaList);
        if (beans == null || beans.size() == 0){
            //若查询结果为空或，表明数据库中无这些地区，将这些地区数据全部写入数据库
            this.spiderMapper.addArea(areaList);
            //得到新插入的地区数据的主键id
            List<AreaBean> list = this.spiderMapper.queryAreaByName(areaList);
            for (AreaBean bean : list){
                areaIdList.add(bean.getId());
            }
            return areaIdList;
        }
        Map<String,AreaBean> map = new HashMap<>();
        for (AreaBean bean : beans){
            map.put(bean.getName(),bean);
        }
        //原来记录数据库中不存在的地区数据
        List<AreaBean> beanList = new ArrayList<>();
        for (AreaBean bean : areaList){
            AreaBean areaBean = map.get(bean.getName());
            //该地区数据不存在
            if (areaBean == null){
                beanList.add(bean);
            }else {
                areaIdList.add(areaBean.getId());
            }
        }
        if (BaseUtil.isEmptyList(beanList)){
            return areaIdList;
        }
        //将没有的地区信息插入数据库
        this.spiderMapper.addArea(beanList);
        //得到新插入的地区数据的主键id
        List<AreaBean> list = this.spiderMapper.queryAreaByName(beanList);
        for (AreaBean bean : list){
            areaIdList.add(bean.getId());
        }
        return areaIdList;
    }

    private List<String> tagIdList(List<TagBean> tagList){
        List<String> tagIdList = new ArrayList<>();
        if (BaseUtil.isEmptyList(tagList)){
            return null;
        }
        //数据库查询已有的地区
        List<TagBean> beans = this.spiderMapper.queryTagByName(tagList);
        if (beans == null || beans.size() == 0){
            //若查询结果为空，表明数据库中无这些标签，将这些标签数据全部写入数据库
            this.spiderMapper.addTag(tagList);
            //得到新插入的标签数据的主键id
            List<TagBean> list = this.spiderMapper.queryTagByName(tagList);
            for (TagBean bean : list){
                tagIdList.add(bean.getId());
            }
            return tagIdList;
        }
        Map<String,TagBean> map = new HashMap<>();
        for (TagBean bean : beans){
            map.put(bean.getName(),bean);
        }
        //原来记录数据库中不存在的标签数据
        List<TagBean> beanList = new ArrayList<>();
        for (TagBean bean : tagList){
            TagBean tagBean = map.get(bean.getName());
            //该标签数据不存在
            if (tagBean == null){
                beanList.add(bean);
            }else {
                tagIdList.add(tagBean.getId());
            }
        }
        if (BaseUtil.isEmptyList(beanList)){
            return tagIdList;
        }
        //将没有的标签信息插入数据库
        this.spiderMapper.addTag(beanList);
        //得到新插入的标签数据的主键id
        List<TagBean> list = this.spiderMapper.queryTagByName(beanList);
        for (TagBean bean : list){
            tagIdList.add(bean.getId());
        }
        return tagIdList;
    }

    private List<String> starList(List<StarBean> starList){
        List<String> starIdList = new ArrayList<>();
        if (BaseUtil.isEmptyList(starList)){
            return null;
        }
        //数据库查询已有的人物
        List<StarBean> beans = this.spiderMapper.queryStarByName(starList);
        if (beans == null || beans.size() == 0){
            //若查询结果为空，表明数据库中无这些人物，将这些人物数据全部写入数据库
            this.spiderMapper.addStar(starList);
            //得到新插入的人物数据的主键id
            List<StarBean> list = this.spiderMapper.queryStarByName(starList);
            for (StarBean bean : list){
                starIdList.add(bean.getId());
            }
            return starIdList;
        }
        Map<String,StarBean> map = new HashMap<>();
        for (StarBean bean : beans){
            String CN_name = bean.getCN_name();
            String EN_name = bean.getEN_name();
            if (!BaseUtil.isEmpty(CN_name)){
                map.put(CN_name,bean);
            }else if (!BaseUtil.isEmpty(EN_name)){
                map.put(EN_name,bean);
            }
        }
        //原来记录数据库中不存在的人物数据
        List<StarBean> beanList = new ArrayList<>();
        for (StarBean bean : starList){
            StarBean CNBean = map.get(bean.getCN_name());
            StarBean ENBean = map.get(bean.getEN_name());
            //该人物数据不存在
            if (CNBean == null && ENBean == null){
                beanList.add(bean);
            }else {
                if (CNBean == null){
                    starIdList.add(ENBean.getId());
                }else {
                    starIdList.add(CNBean.getId());
                }
            }
        }
        if (BaseUtil.isEmptyList(beanList)){
            return starIdList;
        }
        //将没有的人物信息插入数据库
        this.spiderMapper.addStar(beanList);
        //得到新插入的人物数据的主键id
        List<StarBean> list = this.spiderMapper.queryStarByName(beanList);
        for (StarBean bean : list){
            starIdList.add(bean.getId());
        }
        return starIdList;
    }

    private List<AwardBean> awardsParamete(String id) throws IOException {
        List<AwardBean> beans = new ArrayList<>();
        if (BaseUtil.isEmpty(id)){
            return null;
        }
        String url = "https://movie.douban.com/subject/"+id+"/awards/";
        String html = this.getHtml(url);
        String regex = "<div class=\"article\">(.+?)<div class=\"aside\">";
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String value = "";
        while (matcher.find()){
            value = matcher.group(1).trim().replaceAll((char)12288+"","").replaceAll((char)160+"", "").replaceAll("\r|\n|", "").replaceAll("  ","").replaceAll("<li/>","<li></li>");
        }
        String regex1 = "<div class=\"awards\">(.+?)</ul></div>";
        Pattern pattern1 = Pattern.compile(regex1,Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(value);
        String awardnameReg = "<div class=\"hd\"><h2><a href=\".+?/\">(.+?)</a><span class=\"year\">";
        String yearReg = "<span class=\"year\">(.+?)</span></h2>";
        String detailReg = "<ul class=\"award\">(.+?)</ul>";
        Pattern awardnamePattern = Pattern.compile(awardnameReg,Pattern.DOTALL);
        Pattern yearPattern = Pattern.compile(yearReg,Pattern.DOTALL);
        Pattern detailPattern = Pattern.compile(detailReg,Pattern.DOTALL);
        while (matcher1.find()){
            AwardBean awardBean = new AwardBean();
            value = matcher1.group(1).trim()+"</ul>";
            String awardname = "";
            String year = "";
            Matcher awardnameMatcher = awardnamePattern.matcher(value);
            Matcher yearMatcher = yearPattern.matcher(value);
            Matcher detailMatcher = detailPattern.matcher(value);
            while (awardnameMatcher.find()){
                awardname = awardnameMatcher.group(1).trim();
            }
            while (yearMatcher.find()){
                year = yearMatcher.group(1).trim();
            }
            awardname = awardname+year;
            awardBean.setName(awardname);
            String detail = "";
            List<String> contentList = new ArrayList<>();
            while (detailMatcher.find()){
                detail = detailMatcher.group(1).trim();
                String contentReg = "<li>(.+?)</li>";
                Pattern contentPattern = Pattern.compile(contentReg,Pattern.DOTALL);
                Matcher contentMatcher = contentPattern.matcher(detail);
                String starReg = "<a href=\".+?/\" target=\"_blank\">(.+?)</a>";
                Pattern starPaattern = Pattern.compile(starReg,Pattern.DOTALL);
                Matcher starMatcher = starPaattern.matcher(detail);
                String content = "";
                String star = "";
                while (contentMatcher.find()){
                    content = contentMatcher.group(1).trim();
                    break;
                }
                while (starMatcher.find()){
                    star += starMatcher.group(1).trim();
                }
                content = content+star;
                contentList.add(content);
            }
            awardBean.setAward_content(contentList);
            beans.add(awardBean);
        }
        return beans;
    }

    private void coverParameter(String html,Map<String,String> param){
        String regex1 = "data-image=\"(https://.+?.doubanio.com/view/photo/s_ratio_poster/public/.+?)\"";
        Pattern pattern = Pattern.compile(regex1,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String cover = "";
        while (matcher.find()){
            cover = matcher.group(1).trim();
            break;
        }
        param.put("cover",cover);
    }

    private List<AreaBean> areaParameter(String html){
        List<AreaBean> beans = new ArrayList<>();
        String regex = "制片国家/地区:(.+?)<br/>";
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String areas = "";
        while (matcher.find()){
            areas = matcher.group(1).trim().replaceAll("\r|\n|\\s", "").replaceAll("</span>","");
        }
        for (String area : areas.split("/")){
            AreaBean bean = new AreaBean();
            bean.setName(area);
            beans.add(bean);
        }
        return beans;
    }

    /**
     * 暂时不用，避免被封
     * @param html
     * @return
     * @throws IOException
     */
    private List<StarBean> starParameter(String html,String regex){
        List<StarBean> beans = new ArrayList<>();
        String url = "https://movie.douban.com/celebrity/";
//        String regex = "<a href=\"/celebrity/(\\d+)/\" rel=\"v:starring\">";
//        String regex = "<a href=\"/celebrity/(\\d+)/\" rel=\"v:starring\">(.+?)</a>";
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()){
            StarBean bean = new StarBean();
            String name = matcher.group(2).trim();
            bean.setDouban_id(matcher.group(1).trim());
            if (name.matches(".*?[\\u4e00-\\u9fa5].*?")){
                bean.setCN_name(name);
            }else {
                bean.setEN_name(name);
            }

//            String douban_id = matcher.group(1).trim();
//            bean.setDouban_id(douban_id);
//            String starHtml = this.getHtml(url+douban_id+"/");
//            String starringReg = "<a class=\"nbg\" title=\"(.+?)\" href";
//            Pattern starringPattern = Pattern.compile(starringReg,Pattern.DOTALL);
//            Matcher starringMatcher = starringPattern.matcher(starHtml);
//            while (starringMatcher.find()){
//                String name = starringMatcher.group(1).trim();
//                String nameRegex = "([\\u4e00-\\u9fa5].*[\\u4e00-\\u9fa5])(.*)";
//                Pattern namePattern = Pattern.compile(nameRegex);
//                Matcher nameMatcher = namePattern.matcher(name);
//                while (nameMatcher.find()){
//                    bean.setCN_name(nameMatcher.group(1));
//                    bean.setEN_name(nameMatcher.group(2).substring(1));
//                }
//            }
            beans.add(bean);
        }

        return beans;
    }

    private List<TagBean> tagParameter(String html){
        List<TagBean> beans = new ArrayList<>();
        String regex = "<a href=\"/tag/(.+?)\" class=\"\">";
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()){
            TagBean bean = new TagBean();
            String tag = matcher.group(1);
            bean.setName(tag);
            beans.add(bean);
        }
        return beans;
    }

    private List<CategoryBean> categoryParameter(String html){
        List<CategoryBean> beans = new ArrayList<>();
        String regex = "<span property=\"v:genre\">(.+?)</span>";
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()){
            CategoryBean bean = new CategoryBean();
            String category = matcher.group(1).trim().replaceAll("\r|\n|\\s", "").replaceAll("</span>","");
            bean.setName(category);
            beans.add(bean);
        }
        return beans;
    }

    private void linkParameter(String html,Map<String,String> param){
        String regex1 = "href=\"(magnet.+?)\"";
        String regex2 = "<a href=\"(ftp://.+?)\">";
        Pattern pattern = Pattern.compile(regex1,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String link = "";
        while (matcher.find()){
            link = matcher.group(1).trim();
            break;
        }
        if (BaseUtil.isEmpty(link)){
            pattern = Pattern.compile(regex2,Pattern.DOTALL);
            matcher = pattern.matcher(html);
            while (matcher.find()){
                link = matcher.group(1).trim();
                break;
            }
        }
        param.put("link",link);
    }

}
