package ru.mail.kdog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.mail.kdog.dto.MonitorContext;
import ru.mail.kdog.service.MonitorTaskManagerImpl;

@Slf4j
@Profile({"prod","dev"})
@Component
public class StartupTask {

    private final MonitorTaskManagerImpl taskManager;
    private final MonitorContext monitorContext;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public StartupTask(MonitorTaskManagerImpl taskManager, MonitorContext monitorContext, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.taskManager = taskManager;
        this.monitorContext = monitorContext;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Run task by app start event");
        taskManager.monitorTaskStart(monitorContext);
    }
}