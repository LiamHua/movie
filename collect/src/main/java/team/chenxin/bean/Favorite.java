package team.chenxin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class Favorite implements Serializable {
    int id;
    int userId;
    String favoriteName;
    String createTime;

}
