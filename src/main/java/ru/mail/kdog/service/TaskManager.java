package ru.mail.kdog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * управления тасками,
 * Период мониторинга
 * старт/пауза/отмена
 * вывод информации в консоль?
 */
public abstract class TaskManager {
    //TODO создать наследника который рабоает уже содрежит задачи и паттерн стартегия
    //TODO в наследнике не передаются Runnable ну или передаюся спец тип задач
    //TODO создать свой тип задачи?

    @Autowired
    ThreadPoolTaskScheduler monitoringTaskScheduler;

    void taskStart(Runnable task, PeriodicTrigger periodicTrigger){
        monitoringTaskScheduler.schedule(task, periodicTrigger);
    }


    //TODO 2 последних метода через стратегию реализоваь
    /**
     * запускает новую задачу
     * ДО окончания старой -TODO проверить
     * @param task задача
     * @param duration периодичность
     */
    void taskStartFixed(Runnable task, Duration duration ){
        monitoringTaskScheduler.scheduleAtFixedRate(task,duration);
    }

    /**
     * Запускает новую задачу
     * ПОСЛЕ окончания старой - TODO проверить
     * @param task
     * @param duration
     */
   void taskStartDelay(Runnable task, Duration duration ){
        monitoringTaskScheduler.scheduleWithFixedDelay(task,duration);
    }

}
