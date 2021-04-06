package team.chenxin.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.bean
 * @NAME: Favorite
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
@Data
public class Favorite implements Serializable {
    int id;
    int user_id;
    int favorite_id;
    String favorite_name;
    int create_time;

}
