package ru.mail.kdog.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;
import ru.mail.kdog.repository.EntryRepository;
import ru.mail.kdog.service.abstr.FileSystemService;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MonitorServiceTests extends BaseTest {

    //TODO создаваь файлы и дирекории для тестов и подчищать после
    @Autowired
    FileSystemServiceImpl dirObserver;

    @Autowired
    MonitorServiceImpl monitorService;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    FileSystemService fileSystemService;

    @After
    public void returnFilesBack() {
        File fromSuccess = new File(DIR_OUT_SUCCESS_URI);
        File fromWrong = new File(DIR_OUT_WRONG_URI);
        File to = new File(IN_URI);
        moveFile(fromSuccess, to);
        moveFile(fromWrong, to);
    }

    public void moveFile(File from, File to) {
        fileSystemService.getListFilesFromDirAsync(from)
                .doOnNext(file -> fileSystemService.moveFile(file, to))
                .blockLast();
    }

    @Test
    public void asyncHandleDirTest() {
        monitorService.asyncHandleDir(monitorContext);
        Assert.assertTrue(entryRepository.count() > 0);
        Assert.assertTrue(Files.exists(Paths.get(FILE1_OUT_URI_SUCCESS)));
        Assert.assertTrue(Files.exists(Paths.get(FILE2_OUT_URI_SUCCESS)));
    }

}
