package com.xinsteel.sgs.schedule;

import com.xinsteel.sgs.service.ProvincialExpresswayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Autowired
    ProvincialExpresswayService provincialExpresswayService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void task1(){
        provincialExpresswayService.saveOrUpdateOrderInfoAndTransportInfo();
    }

}
