package com.example.demo.schedule;

import com.example.demo.utils.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author wangnan
 * @Date 2019/4/4/004 2019-04 17:19
 * @Param []
 * @return
 **/
@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduledController {
    private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    public static int count = 0;

    /**
     * 每分钟执行一次的定时任务
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void pushDataScheduled() {
        log.info("定时任务开始!");
        count++;
        System.out.println(count);
        log.info("定时任务结束!");
    }
}
