package team.chenxin;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin
 * @NAME: Main
 * @USER: Chenxin
 * @DATE: 2021/4/6
 * @DAY_NAME_FULL: ζζδΊ
 * @PROJECT_NAME: movie
 **/
@SpringBootApplication
@MapperScan("team.chenxin.dao")
@ServletComponentScan
public class CollectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectApplication.class, args);
    }
}
