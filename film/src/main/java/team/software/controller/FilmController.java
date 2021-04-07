package team.software.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.software.bean.ResultMap;
import team.software.service.FilmService;

import java.util.Map;

/**
 * @author huao
 * @ClassName FilmController.java
 * @Description TODO
 * @createTime 2021年04月06日 08:18:00
 */
@CrossOrigin
@RestController(value = "team.software.controller.FilmController")
public class FilmController {

    @Autowired
    private FilmService filmService;

    /**
     * 挑选影片功能
     * @param param 参数
     * @return  结果集
     */
    @RequestMapping(value = "movieSearch",method = RequestMethod.POST)
    public ResultMap movieSearch(@RequestParam Map<String,String> param){
        return this.filmService.movieSearch(param);
    }


    /**
     * 查询影片集合
     * @param param 页码参数
     * @return 结果集
     */
    @RequestMapping(value = "findFilm",method = RequestMethod.POST)
    public  ResultMap findFilm(@RequestParam Map<String, String> param){
        return this.filmService.findFilm(param);
    }

    /**
     * 查询热门电影
     * @param param 页码参数
     * @return 结果集
     */
    @RequestMapping(value = "findHotFilm",method = RequestMethod.POST)
    public ResultMap findHotFilm(@RequestParam Map<String, String> param){
        return this.filmService.findHotFilm(param);
    }
}
