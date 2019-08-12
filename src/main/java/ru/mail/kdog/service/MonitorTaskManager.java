package ru.mail.kdog.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import ru.mail.kdog.dto.MonitorContext;

@Service
public class MonitorTaskManager extends TaskManager {

    //Сделать очередь задач
    //Сущность задачи У задачи можно видеть статус
    //Статус задачи (в процессе) - кол-во файлов , кол-во обработки за раз/ кол-во обработанных файлов
    //2 режима работы приложения - с подробным отчетом(больше памяти) и без сохранения статуса (кол-во файлов)
    //после каждого шага изменять статус задачи/файла
    //файлы на обработку
    //статус каждого шага парсинг/валидация, перемещение / сохранение в репозиторий !!! (с указанием id)!!!!

    private final MonitorService monitorService;

    public MonitorTaskManager(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    //Todo rename
    public void monitoringTaskStartReactor(MonitorContext monitorContext) {
        //TODO reactor scheduler??? сюда можно передавать шедулер из конфига или браь из рекактора? Schedulers
        //Todo по умолчанию был parallel
        //TODO не рабоает, во-первых сначала ждет , А потом запускает, во-вторых, НЕ ПЕРЕМЕЩАЕТ файлы
        Flux.interval(monitorContext.getMonitorPeriod(), Schedulers.elastic())
                .map(aLong -> monitorService.asyncHandleDir(monitorContext))
                .blockLast();
    }
}
