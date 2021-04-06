package team.chenxin;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin
 * @NAME: Main
 * @USER: Chenxin
 * @DATE: 2021/4/6
 * @DAY_NAME_FULL: 星期二
 * @PROJECT_NAME: movie
 **/
@SpringBootApplication
@MapperScan("team.chenxin.dao")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
