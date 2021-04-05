package team.software;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import team.software.service.SpiderService;

import javax.annotation.Resource;

/**
 * @author huao
 * @ClassName InitApplicationRunner.java
 * @Description TODO
 * @createTime 2021年04月02日 17:10:00
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {

    @Resource
    private SpiderService spiderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        this.spiderService.spiderStart();
    }
}
