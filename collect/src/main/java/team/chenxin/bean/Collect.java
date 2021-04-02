package team.chenxin.bean;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.bean
 * @NAME: Collect
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
@Data
public class Collect implements Serializable {

    int id;
    int film_id;
    int favorite_id;
    int collect_time;
}
