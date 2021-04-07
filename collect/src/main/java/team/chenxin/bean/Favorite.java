package team.chenxin.bean;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Repository;

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
@Repository
@Getter
public class Favorite implements Serializable {
    int id;
    int user_id;
    long favorite_id;
    String favorite_name;
    String create_time;

}
