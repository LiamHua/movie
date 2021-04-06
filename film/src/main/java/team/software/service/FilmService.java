package team.software.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.software.bean.FilmBean;
import team.software.bean.ResultMap;
import team.software.mapper.FilmMapper;
import team.software.util.BaseUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //年代
        String decade = param.get("decade");
        if (!BaseUtil.isEmpty(decade)){
            param.put("decade",decade);
        }
        //标签
        String tag = param.get("tag");
        if (!BaseUtil.isEmpty(tag)){
            List<String> tags = Arrays.asList(area.split(","));
            searchParam.put("tags",tags);
        }
        Map<String,String> orders = new HashMap<>();
        return searchParam;
    }

    public ResultMap movieSearch(Map<String,Object> param){
        Map<String,Object> search = param.get("search")
        ResultMap resultMap = new ResultMap();
        String page = (String) param.get("page");
        //默认第一页
        Integer pno = 1;
        if (!BaseUtil.isEmpty(page)){
            pno = Integer.parseInt(page);
        }
        //每页的数据条数
        Integer pageSize = 20;
        PageHelper.startPage(pno,pageSize);
        List<FilmBean> list = this.filmMapper.queryFilm(param);
        PageInfo<FilmBean> pageInfo = new PageInfo<>(list);


        return resultMap;
    }
}
