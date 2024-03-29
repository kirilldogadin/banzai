package ru.mail.kdog.service;

import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Tест создает копии файлов и подчищает файлы после
 * для корректной работы необходимо наличие структуры в файловой системе файлов описанной в BaseTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FileSystemServiceImplTests extends BaseTest {

    @Autowired
    FileSystemServiceImpl fileService;

    @Before
    public void initFiles() throws IOException {
        Files.copy(Paths.get(FILE1_URI), Paths.get(FILE1_URI_COPIED), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Paths.get(FILE2_URI), Paths.get(FILE2_URI_COPIED), StandardCopyOption.REPLACE_EXISTING);
    }

    @After
    public void destructFiles() throws IOException {
        Files.deleteIfExists(Paths.get(FILE1_URI_COPIED));
        Files.deleteIfExists(Paths.get(FILE2_URI_COPIED));

        Files.deleteIfExists(Paths.get(FILE1_COPIED_OUT_URI_SUCCESS));
        Files.deleteIfExists(Paths.get(FILE2_OUT_URI_WRONG));
    }

    @Test
    public void getListFilesFromDirTestAsync() {
        fileService.getListFilesFromDirAsync(new File(IN_URI))
                .subscribe(System.out::println);
    }

    @Test
    public void moveFileSuccessTest() {
        fileService.moveFile(Paths.get(FILE1_URI_COPIED), Paths.get(FILE1_COPIED_OUT_URI_SUCCESS));
        Assert.assertTrue(Files.exists(Paths.get(FILE1_COPIED_OUT_URI_SUCCESS)));
    }

    @Test
    public void moveFileWrongTest() {
        fileService.moveFile(Paths.get(FILE2_URI_COPIED), Paths.get(FILE2_OUT_URI_WRONG));
        Assert.assertTrue(Files.exists(Paths.get(FILE2_OUT_URI_WRONG)));
    }

    @Test
    public void getOutPathTest() {
        var file = new File(FILE1_URI);
        var outDir = new File(DIR_OUT_SUCCESS_URI);
        var outPath = fileService.getFileOutPath(file, outDir);
        var rightOutPath = Paths.get(DIR_OUT_SUCCESS_URI + FILE1_NAME);
        Assert.assertEquals(outPath, rightOutPath);
    }

    @Test
    public void moveFile() {
        fileService.moveFile(new File(FILE1_URI_COPIED), new File(DIR_OUT_SUCCESS_URI));
        Assert.assertTrue(Files.exists(Paths.get(FILE1_COPIED_OUT_URI_SUCCESS)));
    }

}
