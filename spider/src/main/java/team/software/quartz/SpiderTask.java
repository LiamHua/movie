package team.software.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import team.software.service.SpiderService;

/**
 * @author huao
 * @ClassName SpiderTask.java
 * @Description 爬虫任务
 * @createTime 2021年04月05日 17:47:00
 */
@Configuration
public class SpiderTask {

    @Autowired
    private SpiderService spiderService;

    public void spider(){
        System.out.println("爬虫任务开始");
        try {
            this.spiderService.spiderStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void solveData(){
        System.out.println("数据处理开始");
        try {
            this.spiderService.solveData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
