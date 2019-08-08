package ru.mail.kdog.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class DirectoryObserver {

    /**
     * поиск файлов в директории
     * @param path
     * @throws IOException
     * @return
     */
    public Stream<Path> getListFilesFromDir(Path path) throws IOException {
        return Files.list(path)
//                .filter(Files::isRegularFile)
                //todo фильтрация условий возможно разметки
                .filter(DirectoryObserver::validate);
    }
    //TODO  а если файлов миллионы? ОТВЕТ - тут мы получаем СПИСОК файлов, а не саи файлы, так ведь?
    //TODO надо проверить, как быстро вернется ответ из миллиона файлов. или спросить
    //если только список файлов то все ок
    //по порядку читать файлы и отправлять их в очередь? по отдельности?
    //в общем нужно асинхронное чтение это точно иначе этот поток увиснет намертво да и весь процесс точно
    public Flux<File> getListFilesFromDirAsync(File file) throws IOException {
        return Flux.fromArray(Objects.requireNonNull(file.listFiles()));
    }

    @SneakyThrows
    public Path moveFile(Path from, Path to) {
       return Files.move(from,to);
    }


    //todo вроде как не нужег
    public Stream<String> getListFilesFromDirAsStreamString(Path path) throws IOException {
        return getListFilesFromDir(path)
                .map(Path::getFileName)
                .map(Path::toString);
    }

    public static boolean validate(Path path) {
        return true;
    }
}
