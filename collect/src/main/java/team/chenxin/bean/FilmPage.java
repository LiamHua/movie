package team.chenxin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO 电影页面
 * @PACKAGE_NAME: team.chenxin.bean
 * @NAME: FilmPage
 * @USER: Chenxin
 * @DATE: 2021/4/9
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
@Data
@Repository
@AllArgsConstructor
@NoArgsConstructor
public class FilmPage {
    int id;
    int filmId;
    int favoriteId;
    String collectTime;
    String name;
    String cover;
}
