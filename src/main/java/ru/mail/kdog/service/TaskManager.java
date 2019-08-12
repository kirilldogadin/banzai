package ru.mail.kdog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

/**
 * управления тасками,
 * Период мониторинга
 * старт/пауза/отмена
 * вывод информации в консоль?
 */
@Service
public class TaskManager {

    @Autowired
    ThreadPoolTaskScheduler monitoringTaskScheduler;

    public void monitoringStart(Runnable task, PeriodicTrigger periodicTrigger){
        monitoringTaskScheduler.schedule(task, periodicTrigger);
    }
}
