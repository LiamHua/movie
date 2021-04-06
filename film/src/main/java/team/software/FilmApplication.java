package team.software;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author huao
 * @ClassName FilmApplication.java
 * @Description TODO
 * @createTime 2021年04月06日 08:13:00
 */
@SpringBootApplication
@MapperScan(value = "team.software.mapper")
@EnableCaching
@EnableTransactionManagement
public class FilmApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilmApplication.class,args);
    }
}
