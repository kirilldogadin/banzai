package ru.mail.kdog.service.abstr;

import ru.mail.kdog.dto.MonitorContext;

import java.util.concurrent.ScheduledFuture;

public interface MonitorTaskManager {
    ScheduledFuture<?> monitorTaskStart(MonitorContext monitorContext);
}
