package team.chenxin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.chenxin.bean.Favorite;
import team.chenxin.message.Message;
import team.chenxin.service.FavoriteService;
import team.chenxin.utils.IDUtil;
import team.chenxin.utils.TimeUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description TODO 用户收藏夹
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

    /**
    * @Description: 获取收藏夹列表
    * @Param: [userId]
    * @return: org.springframework.http.ResponseEntity<java.util.List<team.chenxin.bean.Favorite>>
    * @Date: 2021/4/7
    */
    @GetMapping("/listAllFavorite")
    public ResponseEntity<List<Favorite>> listAllFavorites( HttpServletRequest request)
    {
        int user_id = (int) request.getAttribute("user_id");
        List<Favorite> favorites = favoriteService.listAllFavorites(user_id);
        return ResponseEntity.ok(favorites);
    }

    /**
    * @Description: 添加收藏夹
    * @Param: [favorite]
    * @return: org.springframework.http.ResponseEntity<team.chenxin.message.Message>
    * @Date: 2021/4/7
    */

    @PostMapping("/addFavorite")
    public ResponseEntity<Message> addFavorite( @RequestParam(value = "favorite_name") String name,
                                                HttpServletRequest request)
    {
            Favorite favorite = new Favorite();
            favorite.setCreateTime(TimeUtil.getTimeNow());
            favorite.setUserId((int) request.getAttribute("user_id"));
            if(favoriteService.getFavorite(name,favorite.getUserId()))
            {
                message.setStatus("fail");
                message.setMsg("收藏已经存在");
                return ResponseEntity.ok(message);
            }
            favorite.setFavoriteName(name);
            favoriteService.addFavorite(favorite);
            message.setStatus("succeed");
            message.setMsg("添加成功");
        return ResponseEntity.ok(message);
    }

    /**
    * @Description: 删除收藏夹
    * @Param: [fa_id, user_id]
    * @return: org.springframework.http.ResponseEntity<team.chenxin.message.Message>
    * @Date: 2021/4/7
    */
    @DeleteMapping("/deleteFavorite/{favorite_id}")
    public ResponseEntity<Message> deleteFavorite(@PathVariable(value = "favorite_id")int fa_id,
                                                  HttpServletRequest request)
    {
        int user_id = (int) request.getAttribute("user_id");
        if (favoriteService.getFavoriteById(fa_id,user_id))
        {
            favoriteService.deleteFavorite(fa_id);
            message.setStatus("succeed");
            message.setMsg("删除成功");
        } else
        {
            message.setStatus("fail");
            message.setMsg("删除失败请添加页面");
        }
        return ResponseEntity.ok(message);
    }

    /**
    * @Description: 修改收藏夹
    * @Param: [name, fa_id, user_id]
    * @return: org.springframework.http.ResponseEntity<team.chenxin.message.Message>
    * @Date: 2021/4/7
    */
    @PutMapping("/modifyfavorite")
    public ResponseEntity<Message> modifyFavorite(@RequestParam(value = "favorite_name")String name,
                                                  @RequestParam(value = "favorite_id") int fa_id,
                                                  HttpServletRequest request)
    {
        int user_id = (int) request.getAttribute("user_id");
        if (favoriteService.getFavoriteById(fa_id,user_id))
        {
            favoriteService.modifyFavorite(fa_id,name);
            message.setStatus("succeed");
            message.setMsg("修改成功");
        } else
        {
            message.setStatus("fail");
            message.setMsg("修改失败");
        }
        return ResponseEntity.ok(message);
    }

}
