package ru.mail.kdog.service.abstr;

import reactor.core.Disposable;
import ru.mail.kdog.dto.MonitorContext;

public interface MonitorService {
    Disposable asyncHandleDir(MonitorContext monitorContext);
}
