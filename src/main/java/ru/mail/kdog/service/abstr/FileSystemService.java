package ru.mail.kdog.service.abstr;

import reactor.core.publisher.Flux;

import java.io.File;
import java.nio.file.Path;

public interface FileSystemService {

    Flux<File> getListFilesFromDirAsync(File file);

    Path moveFile(Path from, Path to);

    void moveFile(File file, File outDir);

    Path getFileOutPath(File file, File outDir);
}
