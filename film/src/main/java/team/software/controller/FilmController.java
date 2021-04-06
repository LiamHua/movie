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
}
