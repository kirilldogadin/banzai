package ru.mail.kdog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.mail.kdog.dto.MonitorContext;
import ru.mail.kdog.service.abstr.MonitorTaskManager;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class MonitorTaskManagerImpl extends TaskManager implements MonitorTaskManager {

    private final MonitorServiceImpl monitorService;

    public MonitorTaskManagerImpl(MonitorServiceImpl monitorService) {
        this.monitorService = monitorService;
    }

    public void monitoringTaskStartReactor(MonitorContext monitorContext) {
        Flux.interval(Duration.ZERO, monitorContext.getMonitorPeriod(), Schedulers.elastic())
                .map(aLong -> monitorService.asyncHandleDir(monitorContext))
                .blockLast();
    }

    @Override
    public ScheduledFuture<?> monitorTaskStart(MonitorContext monitorContext) {
        log.info("Start task. " + monitorContext.toString());
        return taskStartDelay(() -> monitorService.asyncHandleDir(monitorContext),
                monitorContext.monitorPeriod);
    }


}
