package ru.mail.kdog.service;

import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mail.kdog.dto.MonitorContext;
import ru.mail.kdog.entity.Entry;
import ru.mail.kdog.mapper.Mapper;
import ru.mail.kdog.mapper.MapperFile;
import ru.mail.kdog.repository.EntryRepository;
import ru.mail.kdog.service.abstr.FileSystemService;
import ru.mail.kdog.service.abstr.MonitorService;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

/**
 * Класс содержит основную логику приложения
 * с определенной периодичностью
 * мониторит заданную директорию на наличие файлов определенного XML-формата. При
 * обнаружении файла подходящего формата приложение сохраняет его содержимое в
 * аналогичную по структуре таблицу в PostgreSQL и перемещает файл в каталог
 * обработанных файлов.
 * <p>
 * в случае неудачи файл перемещается в другую директорию
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    private final FileSystemService fileSystemService;
    private final Mapper<File, Entry> fileMapper;
    private final EntryRepository entryRepository;

    public MonitorServiceImpl(FileSystemServiceImpl fileSystemService, MapperFile fileMapper, EntryRepository entryRepository) {
        this.fileSystemService = fileSystemService;
        this.fileMapper = fileMapper;
        this.entryRepository = entryRepository;
    }

    /**
     * Полная обработка
     * 1. Взять список файлов из папки
     * 2. замапить файлы в объекты
     * Если маппинг успешен, то
     * 3.1 сохранить в репозиторий
     * 3.2 переместить в директорию обработанных файлов
     * ИНАЧЕ
     * 4.B в случае не успеха в директорию для не успешно обработанных файлов
     *
     * @param monitorContext конфиг
     * @return Disposable
     */
    @Override
    public Disposable asyncHandleDir(MonitorContext monitorContext) {
        return Flux.just(monitorContext)

                .flatMap(monitorContext1 -> fileSystemService.getListFilesFromDirAsync(monitorContext.getDirIn()))
                .log()
                .map(file ->
                        Mono.justOrEmpty(file)
                                .flatMap(fileMapper::fileToMonoDto)
                                .doOnSuccess(entry -> fileSystemService.moveFile(file,
                                        monitorContext.dirOutSuccess))
                                .onErrorResume(JAXBException.class, throwable -> {
                                    fileSystemService.moveFile(file, monitorContext.dirOutWrong);
                                    return Mono.empty();
                                })
                                .onErrorResume(IOException.class, e -> {
                                    System.out.println();
                                    return Mono.empty();
                                })
                )
                .subscribe(entryMono -> entryMono.subscribe(entryRepository::save));
    }
}
