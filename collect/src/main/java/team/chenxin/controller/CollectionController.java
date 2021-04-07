package team.chenxin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.chenxin.bean.Collect;
import team.chenxin.message.Message;
import team.chenxin.message.SucceedMessage;
import team.chenxin.service.CollectService;
import team.chenxin.utils.TimeUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO 用户收藏
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


    @GetMapping("/getCollection/{favorite_id}")
    public ResponseEntity<List<Collect>> getColletionsByFaid(@PathVariable(value = "favorite_id") long favorite_id)
    {
        List<Collect> collects=collectService.getColletionsByFaid(favorite_id);
        return ResponseEntity.ok(collects);
    }

    @PostMapping("/addCollection")
    public ResponseEntity<Message> addCollection(@RequestParam(value = "favorite_id")long fa_id,
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

    @PostMapping("/copyCollection")
    public ResponseEntity<Message> copyCollection(@RequestParam("favorite_id")long fa_id,
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

    @PostMapping("/deleteCollection")
    public ResponseEntity<Message> deleteCollection(@RequestParam("favorite_id")long fa_id,
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

    @PostMapping("/moveCollection")
    public ResponseEntity<Message> moveCollection(@RequestParam("favorite_id")long fa_id,
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
