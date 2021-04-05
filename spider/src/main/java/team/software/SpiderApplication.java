package team.software;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author huao
 * @ClassName SpiderApplication.java
 * @Description 启动类
 * @createTime 2021年04月02日 17:05:00
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@MapperScan(value = "team.software.mapper")
@EnableCaching
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
public class SpiderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpiderApplication.class, args);
    }
}
