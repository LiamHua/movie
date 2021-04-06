package team.software.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.Date;

/**
 * @author huao
 * @ClassName QuartzConfig.java
 * @Description 定时器
 * @createTime 2021年04月05日 14:13:00
 */
@Configuration
public class QuartzConfig {

    /**
     * 爬虫任务
     * 配置定时任务的任务实例
     * @param  job 任务
     */
    @Bean(name = "spiderJobDetail")
    public MethodInvokingJobDetailFactoryBean spiderJobDetail(SpiderTask job) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(job);
        // 需要执行的方法
        jobDetail.setTargetMethod("spider");
        return jobDetail;
    }

    /**
     * 配置爬虫任务定时器
     * @param spiderJobDetail 任务实例
     * @return
     */
    @Bean(name = "spiderTrigger")
    public SimpleTriggerFactoryBean spiderTrigger(JobDetail spiderJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(spiderJobDetail);
        // 设置任务启动延迟 10秒后启动
        trigger.setStartDelay(10*1000);
        // 设置定时任务启动时间
        trigger.setStartTime(new Date());
        // 每5分钟执行一次
        trigger.setRepeatInterval(5*60*1000);
        return trigger;
    }


    /**
     * 配置定时 数据处理任务的任务实例
     * @param  job 任务
     */
    @Bean(name = "solveDataJobDetail")
    public MethodInvokingJobDetailFactoryBean secondJobDetail(SpiderTask job) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(job);
        // 需要执行的方法
        jobDetail.setTargetMethod("solveData");
        return jobDetail;
    }

    /**
     * 配置数据处理定时器
     * @param solveDataJobDetail 任务实例
     * @return
     */
    @Bean(name = "solveDataTrigger")
    public SimpleTriggerFactoryBean solveDataTrigger(JobDetail solveDataJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(solveDataJobDetail);
        // 设置任务启动延迟
        trigger.setStartDelay(0);
        // 设置定时任务启动时间
        trigger.setStartTime(new Date());
        // 每2小时执行一次
        trigger.setRepeatInterval(2*60*60*1000);
        return trigger;
    }

    /**
     *  定义 任务，传入 triggers
     * @param triggers
     * @return
     */
    @Bean
    public SchedulerFactoryBean scheduler(Trigger... triggers){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        // 设置 triggers
        factoryBean.setTriggers(triggers);
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        factoryBean.setOverwriteExistingJobs(true);
        // 延时启动，应用启动3秒后
        factoryBean.setStartupDelay(3);
        return factoryBean;
    }
}

