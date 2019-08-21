package ru.mail.kdog.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.mail.kdog.service.abstr.FileSystemService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Отвечает за работу с файловой системой
 * обход директории и перемещение файлов
 */
@Service
public class FileSystemServiceImpl implements FileSystemService {

    @Override
    public Flux<File> getListFilesFromDirAsync(File file) {
        return Flux.fromArray(Objects.requireNonNull(file.listFiles()));
    }

    /**
     * @param from полный путь до файла вместе с именем
     * @param to   новый полный путь до файла вместе с именем
     *             отличие от метода  moveFile(File file, File outDir) в описании метода
     */
    @SneakyThrows
    @Override
    public Path moveFile(Path from, Path to) {
        return Files.move(from, to);
    }

    /**
     * @param file   файл который перемещаем
     * @param outDir директория, куда перемещаем
     *               отличие от метода  moveFile(Path from, Path to) в том ,
     *               что outDir это только имя директории куда перемещаем
     *               а в Path to полное имя файла (Директория + имя файла)
     */
    @Override
    public void moveFile(File file, File outDir) {
        moveFile(file.toPath(), getFileOutPath(file, outDir));
    }

    /**
     * @param file файл
     * @param outDir директория to куда перемещаем файл
     * @return итоговый Path
     */
    @Override
    public Path getFileOutPath(File file, File outDir) {
        return Paths.get(outDir.getPath() + "\\" + file.getName());
    }

}
