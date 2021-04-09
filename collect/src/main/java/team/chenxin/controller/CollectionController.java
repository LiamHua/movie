package team.chenxin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.chenxin.bean.Collect;
import team.chenxin.bean.FilmPage;
import team.chenxin.message.Message;
import team.chenxin.service.CollectService;
import team.chenxin.utils.TimeUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO 用户电影收藏
 * @PACKAGE_NAME: team.chenxin.collect
 * @NAME: Collection
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
@RequestMapping("/collect")
@RestController
public class CollectionController {

    @Resource
    private CollectService collectService;

    @Resource
    private Message message;


    /**
    * @Description: 根据收藏id获取电影列表
    * @Param: [favorite_id]
    * @return: org.springframework.http.ResponseEntity<java.util.List<team.chenxin.bean.Collect>>
    * @Date: 2021/4/7
    */
    @GetMapping("/getCollection/{favorite_id}")
    public ResponseEntity<List<FilmPage>> getColletionsByFaid(@PathVariable(value = "favorite_id") int favorite_id)
    {
        List<FilmPage> filmPages=collectService.getFilmByFaid(favorite_id);
        return ResponseEntity.ok(filmPages);
    }

    /**
    * @Description: 添加电影到收藏
    * @Param: [fa_id, film_id]
    * @return: org.springframework.http.ResponseEntity<team.chenxin.message.Message>
    * @Date: 2021/4/7
    */
    @PostMapping("/addCollection")
    public ResponseEntity<Message> addCollection(@RequestParam(value = "favorite_id")int fa_id,
                                                 @RequestParam(value = "film_id")int film_id)
    {
        if (collectService.getCollectionByFaiIddFilmId(fa_id, film_id))
        {
            message.setStatus("fail");
            message.setMsg("电影已存在");
            return ResponseEntity.ok(message);
        }

        collectService.addCollection(fa_id,film_id,TimeUtil.getTimeNow());
        message.setStatus("succeed");
        message.setMsg("添加成功");
        return ResponseEntity.ok(message);
    }

    /**
    * @Description: 将电影复制到目标收藏夹
    * @Param: [fa_id, film_id]
    * @return: org.springframework.http.ResponseEntity<team.chenxin.message.Message>
    * @Date: 2021/4/7
    */
    @PostMapping("/copyCollection")
    public ResponseEntity<Message> copyCollection(@RequestParam("favorite_id")int fa_id,
                                               @RequestParam("film_id")int film_id)
    {
        if (collectService.getCollectionByFaiIddFilmId(fa_id, film_id))
        {
            message.setStatus("fail");
            message.setMsg("电影已存在");
            return ResponseEntity.ok(message);
        }
        collectService.copyCollection(fa_id,film_id,TimeUtil.getTimeNow());
        message.setStatus("succeed");
        message.setMsg("复制成功");
        return ResponseEntity.ok(message);
    }

    /**
    * @Description: 删除指定电影
    * @Param: [fa_id, film_id]
    * @return: org.springframework.http.ResponseEntity<team.chenxin.message.Message>
    * @Date: 2021/4/7
    */
    @PostMapping("/deleteCollection")
    public ResponseEntity<Message> deleteCollection(@RequestParam("favorite_id")int fa_id,
                                                 @RequestParam("film_id")int film_id)
    {
        if (collectService.getCollectionByFaiIddFilmId(fa_id, film_id))
        {
            collectService.deleteCollection(fa_id,film_id);
            message.setStatus("succeed");
            message.setMsg("电影删除");
        } else
        {
            message.setStatus("fail");
            message.setMsg("电影不存在");
        }
        return ResponseEntity.ok(message);


    }

    /**
    * @Description: 移动收藏到指定收藏夹
    * @Param: [fa_id, film_id]
    * @return: org.springframework.http.ResponseEntity<team.chenxin.message.Message>
    * @Date: 2021/4/7
    */
    @PostMapping("/moveCollection")
    public ResponseEntity<Message> moveCollection(@RequestParam("favorite_id")int fa_id,
                                               @RequestParam("film_id")int film_id)
    {
        if (collectService.getCollectionByFaiIddFilmId(fa_id, film_id))
        {
            message.setStatus("fail");
            message.setMsg("电影已存在");
        }else {
            collectService.moveCollection(fa_id, film_id);
            message.setStatus("succeed");
            message.setMsg("移动成功");
        }
        return ResponseEntity.ok(message);
    }
}
