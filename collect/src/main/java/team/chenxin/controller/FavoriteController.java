package team.chenxin.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO 用户收藏
 * @PACKAGE_NAME: team.chenxin.collect
 * @NAME: Favorite
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/

@RequestMapping("/favorite")
@RestController
public class FavoriteController {


    @GetMapping("/getAllFavorite")
    public String GetAllFavorites()
    {
        return "favorite";
    }


    @PostMapping("/addfavorite")
    public String AddFavorite(@RequestParam("favorite_name")String name)
    {
        return "favorite/get";
    }


    @DeleteMapping("/deletefavorite/{favorite_id}")
    public String DeleteFavorite(@PathVariable("favorite_id")int id)
    {
        return "favorite/get";
    }

    @PutMapping("/modifyfavorite")
    public String ModifyFavrite()
    {
        return "modif";
    }

}
