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
 * @DAY_NAME_FULL: ζζδΊ
 * @PROJECT_NAME: movie
 **/
@Data
@Repository
public class Collect implements Serializable {

    int id;
    int film_id;
    int favorite_id;
    String collect_time;
}
