package team.software.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.software.bean.*;
import team.software.mapper.FilmMapper;
import team.software.util.BaseUtil;
import team.software.util.RedisUtil;

import java.util.*;

/**
 * @author huao
 * @ClassName FilmService.java
 * @Description TODO
 * @createTime 2021年04月06日 08:16:00
 */
@Service(value = "team.software.service.FilmService")
public class FilmService {

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private RedisUtil redisUtil;

    public ResultMap genres() {
        List<BaseBean> genresList = (List<BaseBean>) this.redisUtil.get("genres");
        if (BaseUtil.isEmptyList(genresList)){
            genresList = this.filmMapper.queryGenres();
            this.redisUtil.set("genres",genresList);
        }
        return new ResultMap().OK(genresList);
    }

    public ResultMap areas() {
        List<BaseBean> areasList = (List<BaseBean>) this.redisUtil.get("areas");
        if (BaseUtil.isEmptyList(areasList)){
            String[] area = {"中国大陆","欧美","美国","中国香港","中国台湾","日本","韩国","英国","法国","德国"
                    ,"意人利","西班牙","印度","泰国","俄罗斯","伊朗","加拿大","澳大利亚","爱尔兰","瑞典","巴西","丹麦"};
            areasList = this.filmMapper.queryAreas(Arrays.asList(area));
            this.redisUtil.set("areas",areasList);
        }
        return new ResultMap().OK(areasList);
    }

    /**
     * 处理前端传来的搜索参数
     * @param param 搜索参数
     * @return 处理过后的搜索参数
     */
    public Map<String,Object> solveSearchParam(Map<String,String> param){
        if (param == null || param.size() == 0){
            return null;
        }
        Map<String,Object> searchParam = new HashMap<>();
        //类别
        String genre = param.get("genres");
        if (!BaseUtil.isEmpty(genre)){
            List<String> genres = Arrays.asList(genre.split(","));
            searchParam.put("genres",genres);
        }
        //地区
        String area = param.get("areas");
        if (!BaseUtil.isEmpty(area)){
            List<String> areas = Arrays.asList(area.split(","));
            searchParam.put("areas",areas);
        }
        //标签
        String tag = param.get("tags");
        if (!BaseUtil.isEmpty(tag)){
            List<String> tags = Arrays.asList(area.split(","));
            searchParam.put("tags",tags);
        }
        //年代
        String decade = param.get("decade");
        if (!BaseUtil.isEmpty(decade)){
            searchParam.put("decade",decade);
        }
        searchParam.put("sort",param.get("sort"));
        return searchParam;
    }

    /**
     * 挑选影片功能
     * @param param 参数
     * @return 结果集
     */
    public ResultMap movieSearch(Map<String,String> param){
        Map<String,Object> search = solveSearchParam(param);
        ResultMap resultMap = new ResultMap();
        String page = param.get("page");
        //默认第一页
        Integer pno = 1;
        if (!BaseUtil.isEmpty(page)){
            pno = Integer.parseInt(page);
        }
        //每页的数据条数
        Integer pageSize = 20;
        PageHelper.startPage(pno,pageSize);
        List<FilmBean> list = this.filmMapper.queryFilm(search);
        PageInfo<FilmBean> pageInfo = new PageInfo<>(list);
        list = pageInfo.getList();
        for (FilmBean bean : list){
            String directors = bean.getDirectors();
            String casts = bean.getCasts();
            if (!BaseUtil.isEmpty(directors)){
                bean.setDirectors(directors.replaceAll(",","/"));
            }
            if (!BaseUtil.isEmpty(casts)) {
                bean.setCasts(casts.replaceAll(",", "/"));
            }
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("data",list);
        dataMap.put("count", pageInfo.getTotal());
        dataMap.put("nowPage", pageInfo.getPageNum());
        dataMap.put("totalPages",pageInfo.getPages());
        resultMap.setCode("200");
        resultMap.setMsg("请求成功");
        resultMap.setData(dataMap);
        return resultMap;
    }

    /**
     * 影片展示数据
     * @param param 页码参数
     * @return 结果集
     */
    public ResultMap queryMovie(Map<String, String> param){
        ResultMap resultMap = new ResultMap();
        String film_id = param.get("id");
        if (BaseUtil.isEmpty(film_id)){
            resultMap.setCode("500");
            return resultMap;
        }
        FilmDetailBean filmDetailBean = this.filmMapper.queryFilmDetail(param);
        if (BaseUtil.isEmpty(filmDetailBean)){
            resultMap.setCode("500");
            return resultMap;
        }
        String info = filmDetailBean.getInfo();
        info = info.replaceAll("<br/>","</p><p>");
        filmDetailBean.setInfo(info);
        String category = filmDetailBean.getCategory().replaceAll(","," / ");
        filmDetailBean.setCategory(category);
        String area = filmDetailBean.getAreas().replaceAll(","," / ");
        filmDetailBean.setAreas(area);
        String translatedTerm = filmDetailBean.getTranslatedTerm().replaceAll("/"," / ");
        filmDetailBean.setTranslatedTerm(translatedTerm);
        List<StarBean> directorList = this.filmMapper.queryFilmDirector(film_id);
        List<StarBean> scriptwriterList = this.filmMapper.queryFilmScriptwriter(film_id);
        List<StarBean> starList = this.filmMapper.queryFilmStar(film_id);
        filmDetailBean.setDirectorList(directorList);
        filmDetailBean.setScriptwriterList(scriptwriterList);
        filmDetailBean.setStarList(starList);
        this.filmMapper.updateFilmHot(film_id);
        List<AwardBean> beanList = this.filmMapper.queryFilmAward(film_id);
        Map<String, List<String>> map = new HashMap<>();
        Map<String,AwardBean > dataMap = new HashMap<>();
        for (AwardBean bean : beanList){
            String award_id = bean.getAwardId();
            List<String> content = map.get(award_id);
            if (BaseUtil.isEmptyList(content)){
                content = new ArrayList<>();
            }
            content.add(bean.getAwardContent());
            map.put(award_id,content);
            dataMap.put(award_id,bean);
        }
        List<AwardBean> dataList = new ArrayList<>();
        Iterator<Map.Entry<String, AwardBean>> it= dataMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, AwardBean> entry=it.next();
            String award_id = entry.getKey();
            AwardBean bean = entry.getValue();
            bean.setAwardContentList(map.get(award_id));
            bean.setAwardContent(null);
            dataList.add(bean);
        }
        filmDetailBean.setAwardContentList(dataList);
        resultMap.setCode("200");
        resultMap.setMsg("请求成功");
        resultMap.setData(filmDetailBean);
        return resultMap;
    }

}
