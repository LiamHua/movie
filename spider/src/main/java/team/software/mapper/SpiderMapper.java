package team.software.mapper;

import org.springframework.stereotype.Repository;
import team.software.bean.*;

import java.util.List;
import java.util.Map;

/**
 * @author huao
 * @ClassName SpiderMapper.java
 * @Description TODO
 * @createTime 2021年04月02日 17:03:00
 */
@Repository(value = "team.software.mapper.SpiderMapper")
public interface SpiderMapper {

    String queryLastUrl();

    void addHtml(List<HtmlBean> urlList);

    List<AreaBean> queryAreaByName(List<AreaBean> areaList);

    void addArea(List<AreaBean> areaList);

    List<TagBean> queryTagByName(List<TagBean> tagList);

    void addTag(List<TagBean> tagList);

    void addStar(List<StarBean> starList);

    List<StarBean> queryStarByName(List<StarBean> starList);

    List<HtmlBean> queryUnSolveHtml();

    List<CategoryBean> queryCategoryByName(List<CategoryBean> categoryList);

    void addCategory(List<CategoryBean> categoryList);

    List<AwardBean> queryAwardByName(List<AwardBean> awardList);

    void addAward(List<AwardBean> awardList);

    String queryFilmId(String html_id);

    void addFilm(Map<String, String> param);

    void solveCategory(Map<String, Object> param);

    void solveArea(Map<String, Object> param);

    void solveTag(Map<String, Object> param);

    void solveDirector(Map<String, Object> param);

    void solveScriptwriter(Map<String, Object> param);

    void solveStarring(Map<String, Object> param);

    void solveAward(Map<String, Object> param);

    void setHtmlState(List<String> stateList);

    void setHtmlStarErrorState(List<String> stateList);
}
