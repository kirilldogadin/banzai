package ru.mail.kdog.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Отвечает за работу с файловой системой
 * обход директории и перемещение файлов
 */
@Service
public class FileSystemService {

    /**
     * поиск файлов в директории
     *
     * @param path
     * @return
     * @throws IOException
     */
    public Stream<Path> getListFilesFromDir(Path path) throws IOException {
        return Files.list(path)
//                .filter(Files::isRegularFile)
                //todo фильтрация условий возможно разметки
                .filter(FileSystemService::validate);
    }

    //TODO  а если файлов миллионы? ОТВЕТ - тут мы получаем СПИСОК файлов, а не саи файлы, так ведь?
    //TODO надо проверить, как быстро вернется ответ из миллиона файлов. или спросить
    //если только список файлов то все ок
    //по порядку читать файлы и отправлять их в очередь? по отдельности?
    //в общем нужно асинхронное чтение это точно иначе этот поток увиснет намертво да и весь процесс точно
    public Flux<File> getListFilesFromDirAsync(File file) {
        return Flux.fromArray(Objects.requireNonNull(file.listFiles()));
    }

    /**
     * @param from полный путь до файла вместе с именем
     * @param to   новый полный путь до файла вместе с именем
     *             отличие от метода  moveFile(File file, File outDir) в описании метода
     */
    @SneakyThrows
    public Path moveFile(Path from, Path to) {
        return Files.move(from, to);
    }

    //todo вроде как не нужег
    public Stream<String> getListFilesFromDirAsStreamString(Path path) throws IOException {
        return getListFilesFromDir(path)
                .map(Path::getFileName)
                .map(Path::toString);
    }

    /**
     * @param file   файл который перемещаем
     * @param outDir директория, куда перемещаем
     *               отличие от метода  moveFile(Path from, Path to) в том ,
     *               что outDir это только имя директории куда перемещаем
     *               а в Path to полное имя файла (Директория + имя файла)
     */
    public void moveFile(File file, File outDir) {
        moveFile(file.toPath(), getOutPath(file, outDir));
    }

    public Path getOutPath(File file, File outDir) {
        return Paths.get(outDir.getPath() + "\\" + file.getName());
    }

    public static boolean validate(Path path) {
        return true;
    }
}
