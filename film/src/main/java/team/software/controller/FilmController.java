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

    @RequestMapping(value = "genres",method = RequestMethod.POST)
    public ResultMap genres(){
        return this.filmService.genres();
    }

    @RequestMapping(value = "areas",method = RequestMethod.POST)
    public ResultMap areas(){
        return this.filmService.areas();
    }


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
     * 查询影片详情
     * @param param 页码参数
     * @return 结果集
     */
    @RequestMapping(value = "queryMovie",method = RequestMethod.POST)
    public  ResultMap queryMovie(@RequestParam Map<String, String> param){
        return this.filmService.queryMovie(param);
    }

    @RequestMapping(value = "queryFilmAward",method = RequestMethod.POST)
    public  ResultMap queryFilmAward(@RequestParam Map<String, String> param){
        return this.filmService.queryFilmAward(param);
    }
}
