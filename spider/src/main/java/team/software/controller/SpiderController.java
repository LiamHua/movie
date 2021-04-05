package team.software.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import team.software.bean.ResultMap;
import team.software.service.SpiderService;

/**
 * @author huao
 * @ClassName SpiderController.java
 * @Description TODO
 * @createTime 2021年04月03日 00:33:00
 */
@RestController(value = "team.software.controller.SpiderController")
public class SpiderController {

    @Autowired
    private SpiderService spiderService;

}
