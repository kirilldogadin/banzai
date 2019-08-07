package ru.mail.kdog.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    final DirectoryObserver directoryObserver;
    final FileMapper fileMapper;
    final EntryRepository entryRepository;

    public MonitorService(DirectoryObserver directoryObserver, FileMapper fileMapper, EntryRepository entryRepository) {
        this.directoryObserver = directoryObserver;
        this.fileMapper = fileMapper;
        this.entryRepository = entryRepository;
    }

    //TODO вот это лучше сделать реактивно
    //или через очередь т.к. файлов может быть миллионы - то есть надо брать файлы и класть в очередь
    //типичная задача читатель писатель
    //TODO да и брать ВСЕ файлы воообще космически дорого, А если память кончится?
    public Stream<Entry> handleAllFiles(Path path) throws IOException {
        return directoryObserver.getListFilesFromDir(path)
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
                return directoryObserver.getListFilesFromDir(path);
            } catch (IOException e) {
            }
            return null;
        });
        return future.thenApply(pathStream -> pathStream.map(path1 -> {
            try {
                return fileMapper.fileToPojoExceptionally(path.toFile());
            } catch (JAXBException e) {
            }
            return null;
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
        return directoryObserver.getListFilesFromDirAsync(dir)
                //TODO вот тут можно разбить обраотку файлов на группы!!
                //буфферы паузы или что-то подобное, или просто батчаем(по 50-100 штук)
                //TODO обработка файлов может быть параллельной!!!??? из xml в сущности
                .map(fileMapper::fileToPojo);
    }

    /***
     * полная обработка:
     * 1. Взять список файлов из папки
     * 2. замапить файлы в объекты
     * 3. сохранить в репозиторий
     * @param dir дирекория мониторинга
     * @throws IOException
     */
    public void asyncHandleDir(File dir) throws IOException {
        directoryObserver.getListFilesFromDirAsync(dir)
                .map(fileMapper::fileToPojo)
                .subscribe(entryRepository::save);
    }

    /**
     * @param inputDir
     */
    public void asyncHandleDir2(File inputDir) {

    }

    //Todo обработать ошибку реактивно
    private Entry convert(File file) {
        try {
            return fileMapper.fileToPojoExceptionally(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
