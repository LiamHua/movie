package team.chenxin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.chenxin.bean.Collect;
import team.chenxin.service.CollectService;

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

    @Autowired
    private CollectService collectService;

    @GetMapping("/getCollection/{favorite_id}")
    public ResponseEntity<List<Collect>> getColletionsByFaid(@PathVariable int favorite_id)
    {
        List<Collect> collects=collectService.getColletionsByFaid(favorite_id);
        return ResponseEntity.ok(collects);
    }

    @PostMapping("/addCollection")
    public ResponseEntity<Void> addCollection(@RequestParam("favoriteid_id")int fa_id,
                                              @RequestParam("film_id")int film_id)
    {

        collectService.addCollection(fa_id,film_id,);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/copyCollection")
    public ResponseEntity<Void> copyCollection(@RequestParam("favoriteid_id")int fa_id,
                                               @RequestParam("film_id")int film_id)
    {
        collectService.copyCollection(fa_id,film_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteCollection")
    public ResponseEntity<Void> deleteCollection(@RequestParam("favoriteid_id")int fa_id,
                                                 @RequestParam("film_id")int film_id)
    {
        collectService.deleteCollection(fa_id,film_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/moveCollection/{favorite_id}")
    public ResponseEntity<Void> moveCollection(@RequestParam("favorite_id")int fa_id,
                                               @RequestParam("film_id")int film_id)
    {
        collectService.moveCollection(fa_id,film_id);
        return ResponseEntity.ok().build();
    }
}
