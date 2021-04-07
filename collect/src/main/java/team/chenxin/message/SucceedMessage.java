package team.chenxin.message;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.message
 * @NAME: SucceedMessage
 * @USER: Chenxin
 * @DATE: 2021/4/7
 * @DAY_NAME_FULL: 星期三
 * @PROJECT_NAME: movie
 **/
@Data
@Repository
public class SucceedMessage implements Serializable {
    private String status="succeed";
    private String msg;
}
