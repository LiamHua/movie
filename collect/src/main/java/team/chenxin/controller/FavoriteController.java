package team.chenxin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.chenxin.bean.Favorite;
import team.chenxin.message.Message;
import team.chenxin.service.FavoriteService;
import team.chenxin.utils.IDUtil;
import team.chenxin.utils.TimeUtil;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private FavoriteService favoriteService;

    @Resource
    private Message message;

        @GetMapping("/listAllFavorite/user_id/{user_id}")
    public ResponseEntity<List<Favorite>> listAllFavorites(@PathVariable(value = "user_id") int userId)
    {
        List<Favorite> favorites = favoriteService.listAllFavorites(userId);
        return ResponseEntity.ok(favorites);
    }


    @PostMapping("/addFavorite")
    public ResponseEntity<Message> addFavorite( @RequestBody Favorite favorite)
    {
        System.out.println(favorite);
        if (favoriteService.getFavorite(favorite.getFavorite_id(), favorite.getUser_id()))
        {
            message.setStatus("fail");
            message.setMsg("收藏已存在");
            return ResponseEntity.ok(message);
        } else
        {
            favorite.setCreate_time(TimeUtil.getTimeNow());
            favorite.setFavorite_id(IDUtil.next());
            favoriteService.addFavorite(favorite);
            message.setStatus("succeed");
            message.setMsg("添加成功");
            return ResponseEntity.ok(message);
        }
    }


    @DeleteMapping("/deleteFavorite/{favorite_id}/user/{user_id}")
    public ResponseEntity<Message> deleteFavorite(@PathVariable(value = "favorite_id")int fa_id,
                                                  @PathVariable(value = "user_id")int user_id)
    {
        if (favoriteService.getFavorite(fa_id,user_id))
        {
            favoriteService.deleteFavorite(fa_id);
            message.setStatus("succeed");
            message.setMsg("删除成功");
            return ResponseEntity.ok(message);
        } else
        {
            message.setStatus("fail");
            message.setMsg("删除失败请添加页面");
            return ResponseEntity.ok(message);
        }
    }

    @PutMapping("/modifyfavorite")
    public ResponseEntity<Message> modifyFavorite(@RequestParam(value = "favorite_name")String name,
                                                  @RequestParam(value = "favorite_id") int fa_id,
                                                  @RequestParam(value = "user_id")int user_id)
    {
        if (favoriteService.getFavorite(fa_id,user_id))
        {
            favoriteService.modifyFavorite(fa_id,name);
            message.setStatus("succeed");
            message.setMsg("修改成功");
            return ResponseEntity.ok(message);
        } else
        {
            message.setStatus("fail");
            message.setMsg("修改失败");
            return ResponseEntity.ok(message);
        }
    }

}
