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
    public ResponseEntity<List<Collect>> getColletionsByFaid(@PathVariable("favrite_id")int f_id)
    {
        List<Collect> collects=collectService.getColletionsByFaid(f_id);
        return ResponseEntity.ok(collects);
    }

    @PostMapping("/addCollection/{favorite_id}")
    public ResponseEntity<Void> addCollection(@PathVariable("favoriteid_id")int id)
    {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/copyCollection")
    public ResponseEntity<Void> copyCollection(@RequestParam("film_id")int film_id,
                                 @RequestParam("favorite_id")int favorit_id)
    {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteCollecticon/{film_id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable("film_id")int film_id)
    {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/moveCollection/{favorite_id}")
    public ResponseEntity<Void> moveCollection(@PathVariable("favorite_id")int film_id)
    {
        return ResponseEntity.ok().build();
    }
}
