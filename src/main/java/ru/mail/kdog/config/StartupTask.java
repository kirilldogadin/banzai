package ru.mail.kdog.config;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.mail.kdog.dto.MonitorContext;
import ru.mail.kdog.service.MonitorTaskManager;

@Component
public class StartupTask {

    final
    MonitorTaskManager taskManager;

    final
    MonitorContext monitorContext;

    final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public StartupTask(MonitorTaskManager taskManager, MonitorContext monitorContext, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.taskManager = taskManager;
        this.monitorContext = monitorContext;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        taskManager.monitoringTaskStartReactor(monitorContext);
    }
}