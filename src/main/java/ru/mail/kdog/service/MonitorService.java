package ru.mail.kdog.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mail.kdog.dto.MonitorContext;
import ru.mail.kdog.mapper.FileMapper;
import ru.mail.kdog.repository.EntryRepository;

import javax.xml.bind.JAXBException;
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
public class MonitorService {

    final FileSystemService fileSystemService;
    final FileMapper fileMapper;
    final EntryRepository entryRepository;

    public MonitorService(FileSystemService fileSystemService, FileMapper fileMapper, EntryRepository entryRepository) {
        this.fileSystemService = fileSystemService;
        this.fileMapper = fileMapper;
        this.entryRepository = entryRepository;
    }


    /**
     * Полная обработка
     * 1. Взять список файлов из папки
     * 2. замапить файлы в объекты
     * 3 в зависимости от того как прошел маппинг
     * Валидация
     * 4. сохранить в репозиторий
     * 5. переместить в директорию обработанных файлов
     * TODO 4 и 5 можно делать параллельно - как
     * ИНАЧЕ
     * 5.B в случае не успеха в директорию для не успешно обработанных файлов
     * 6. Ошибки перемещание запись в лог (как и все остальные ошибки)
     *
     * @param monitorContext конфиг //TODO добавить метод с забитыми как фп
     * @return
     */
    //TODO как вариант можно добавить ПОЛЕ успешности обработки - ??
    //TODO если успешно, то делаем перенос и сохранение, если нет
    //TODO ВМЕСТО поля проверку на валид - конвертация и есть валидация?
    //TODO добавить валидацию полей в конвертацию?
    //TODO самое главное VAVR там паттерн матчинг
    //TODO вот тут можно разбить обраотку файлов на группы!!
    //TODO буфферы паузы или что-то подобное, или просто батчаем(по 50-100 штук)
    //TODO обработка файлов может быть параллельной!!!??? из xml в сущности
    public void asyncHandleDir(MonitorContext monitorContext) {
        Flux.just(monitorContext)
                .flatMap(monitorContext1 -> fileSystemService.getListFilesFromDirAsync(monitorContext.getDirIn()))
                //можно или тут или к след методу добавить обработку ошибки файловой системы
//                .onBackpressureBuffer() //TODO проверить с ним и без На нагрузке
                .map(file ->
                        Mono.justOrEmpty(file)
                                .flatMap(fileMapper::fileToDto)
                                .doOnSuccess(entry -> {
                                    fileSystemService.moveFile(file,
                                            monitorContext.dirOutSuccess);
                                })
                                //TODO можно завязать логику не на ошибку, а проверку условную
                                //TODO то есть вместо ошибки можно сделать метод valid
                                //Todo добавить условие для ошибки, если это ошибкавалидация то тогда перемещаем файл, если это ошибка файлового сервиса, то другое
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
