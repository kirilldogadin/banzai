package ru.mail.kdog.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mail.kdog.dto.Entry;
import ru.mail.kdog.mapper.FileMapper;
import ru.mail.kdog.repository.EntryRepository;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

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

    //TODO вот это лучше сделать реактивно
    //или через очередь т.к. файлов может быть миллионы - то есть надо брать файлы и класть в очередь
    //типичная задача читатель писатель
    //TODO да и брать ВСЕ файлы воообще космически дорого, А если память кончится?
    public Stream<Entry> handleAllFiles(Path path) throws IOException {
        return fileSystemService.getListFilesFromDir(path)
                .map(Path::toFile)
                .map(this::convert);
    }

    //TODO разделить пулы Executors
    //TODO добавить очередь и подпись на очередь
    @Deprecated
    public CompletableFuture<Stream<Entry>> asyncHandleFilesOld(Path path) {

        var future = CompletableFuture.supplyAsync(() ->
        {
            try {
                return fileSystemService.getListFilesFromDir(path);
            } catch (IOException e) {
            }
            return null;
        });

        return future.thenApply(pathStream -> pathStream.map(path1 -> {
            return fileMapper.fileToPojo(path.toFile());
        }));

    }

    /**
     * сначала получить список файлов из директории
     * замапить список файлов в сущности
     *
     * @param dir directory
     * @return
     * @throws IOException
     */
    //Todo название
    public Flux<Entry> loadListFiles(File dir) throws IOException {
        //todo разобраться является ли мап асинхронным
        return fileSystemService.getListFilesFromDirAsync(dir)
                //TODO вот тут можно разбить обраотку файлов на группы!!
                //буфферы паузы или что-то подобное, или просто батчаем(по 50-100 штук)
                //TODO обработка файлов может быть параллельной!!!??? из xml в сущности
                .flatMap(file -> fileMapper.fileToDto(file));
    }

    /***
     * 1. Взять список файлов из папки
     * 2. замапить файлы в объекты
     * 3. сохранить в репозиторий
     * @param dir дирекория мониторинга
     * @throws IOException
     */
    public void asyncHandleDir(File dir) throws IOException {
        fileSystemService.getListFilesFromDirAsync(dir)
                .flatMap(fileMapper::fileToDto)
                .subscribe(entryRepository::save);
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
    public void asyncHandleDir(MonitorContext monitorContext) {
        Flux.just(monitorContext)
                .flatMap(monitorContext1 -> fileSystemService.getListFilesFromDirAsync(monitorContext.getDirIn()))
                //можно или тут или к след методу добавить обработку ошибки файловой системы
//                .onBackpressureBuffer() //TODO проверить с ним и без На нагрузке
                .map(file ->
                        Mono.justOrEmpty(file)
                                .flatMap(fileMapper::fileToDto)
                                //doOnNext  и прочие методы попоробовать для peek каждого элемента)
                                .doOnSuccess(entry -> {
                                    fileSystemService.moveFile(file,
                                            monitorContext.dirOutSuccess);
                                })
                                //TODO можно завязать логику не на ошибку, а проверку условную
                                //TODO то есть вместо ошибки можно сделать метод valid
                                .onErrorResume(JAXBException.class, throwable -> {
                                    //Todo добавить условие для ошибки, если это ошибкавалидация то тогда перемещаем файл, если это ошибка файлового сервиса, то другое
                                    //TODO !!!!!!!!!!1 ТУТ ВОЗНИКАЕТ ВТОРАЯ ОШИБКА И ПОТОК ПРЕРЫВАЕТСЯ
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

    private Entry convert(File file) {
        return fileMapper.fileToPojo(file);
    }

    /**
     * объект содержащий настройки
     * директория мониторинга
     * директироия обработыннх и тд
     */
    @AllArgsConstructor
    @Getter
    public static class MonitorContext {
        File dirIn;
        File dirOutSuccess;
        File dirOutWrong;
    }
}
