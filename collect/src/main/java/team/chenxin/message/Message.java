package team.chenxin.message;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @Description TODO 自定义返回消息
 * @PACKAGE_NAME: team.chenxin.message
 * @NAME: Message
 * @USER: Chenxin
 * @DATE: 2021/4/7
 * @DAY_NAME_FULL: 星期三
 * @PROJECT_NAME: movie
 **/
@Data
@Repository
public class Message implements Serializable {
    private String status="fail";
    private String msg;
}
