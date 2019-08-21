package ru.mail.kdog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

/**
 * управления тасками,
 * Период мониторинга
 * старт/пауза/отмена
 * вывод информации в консоль?
 */
public abstract class TaskManager {

    @Autowired
    ThreadPoolTaskScheduler monitoringTaskScheduler;

    ScheduledFuture<?> taskStart(Runnable task, PeriodicTrigger periodicTrigger){
        return monitoringTaskScheduler.schedule(task, periodicTrigger);
    }

    /**
     * запускает новую задачу
     * ДО окончания старой
     * @param task задача
     * @param duration периодичность
     * @return future задачи
     */
    ScheduledFuture<?> taskStartFixed(Runnable task, Duration duration ){
        return monitoringTaskScheduler.scheduleAtFixedRate(task,duration);
    }

    /**
     * Запускает новую задачу
     * ПОСЛЕ окончания старой
     * @param task runnable задача
     * @param duration Duration задержки между проверками
     * @return future задачи
     */
   ScheduledFuture<?> taskStartDelay(Runnable task, Duration duration ){
       return monitoringTaskScheduler.scheduleWithFixedDelay(task,duration);
    }

}
