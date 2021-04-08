package team.software.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.software.bean.*;
import team.software.mapper.FilmMapper;
import team.software.util.BaseUtil;

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

    /**
     * 处理前端传来的搜索参数
     * @param param 搜索参数
     * @return 处理过后的搜索参数
     */
    public Map<String,Object> solveSearchParam(Map<String,String> param){
        Map<String,Object> searchParam = new HashMap<>();
        //类别
        String genre = param.get("genres");
        if (!BaseUtil.isEmpty(genre)){
            List<String> genres = Arrays.asList(genre.split(","));
            searchParam.put("genres",genres);
        }
        //地区
        String area = param.get("area");
        if (!BaseUtil.isEmpty(area)){
            List<String> areas = Arrays.asList(area.split(","));
            searchParam.put("areas",areas);
        }
        //标签
        String tag = param.get("tag");
        if (!BaseUtil.isEmpty(tag)){
            List<String> tags = Arrays.asList(area.split(","));
            searchParam.put("tags",tags);
        }
        //年代
        String decade = param.get("decade");
        if (!BaseUtil.isEmpty(decade)){
            param.put("decade",decade);
        }
        //顺序 例: 热度最高，评分最高，最多收藏，最近上映
        String sort = "";
        if (!BaseUtil.isEmpty(param.get("hot"))){
            sort = "hot";
        }else if (!BaseUtil.isEmpty(param.get("douban"))){
            sort = "douban";
        }else if (!BaseUtil.isEmpty(param.get("collect"))){
            sort = "collect";
        }else if (!BaseUtil.isEmpty(param.get("releaseDate"))){
            sort = "release_time";
        }
        searchParam.put("sort",sort);
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
    public ResultMap findFilm(Map<String, String> param){
        ResultMap resultMap = new ResultMap();
        String film_id = param.get("id");
        if (BaseUtil.isEmpty(film_id)){
            resultMap.setCode("500");
            return resultMap;
        }
        FilmDetailBean bean = this.filmMapper.queryFilmDetail(param);
        if (BaseUtil.isEmpty(bean)){
            resultMap.setCode("500");
            return resultMap;
        }
        List<StarBean> directorList = this.filmMapper.queryFilmDirector(film_id);
        List<StarBean> scriptwriterList = this.filmMapper.queryFilmScriptwriter(film_id);
        List<StarBean> starList = this.filmMapper.queryFilmStar(film_id);
        bean.setDirectorList(directorList);
        bean.setScriptwriterList(scriptwriterList);
        bean.setStarList(starList);
        resultMap.setCode("200");
        resultMap.setMsg("请求成功");
        resultMap.setData(bean);

        return resultMap;
    }

    /**
     * 查询热门影片 按照热度降序分页排列
     * @param param 页码
     * @return 结果集
     */
    public ResultMap findHotFilm(Map<String, String> param){
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
        List<FilmDetailBean> list = this.filmMapper.queryHotFilm(search);
        PageInfo<FilmDetailBean> pageInfo = new PageInfo<>(list);
        list = pageInfo.getList();
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
}
