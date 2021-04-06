package team.software.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import team.software.service.FilmService;

/**
 * @author huao
 * @ClassName FilmController.java
 * @Description TODO
 * @createTime 2021年04月06日 08:18:00
 */
@RestController(value = "team.software.controller.FilmController")
public class FilmController {

    @Autowired
    private FilmService filmService;
}
