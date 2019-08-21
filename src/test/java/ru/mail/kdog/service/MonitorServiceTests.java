package ru.mail.kdog.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;
import ru.mail.kdog.repository.EntryRepository;

import javax.transaction.Transactional;
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

    @Test
    public void asyncHandleDirTest(){
        monitorService.asyncHandleDir(monitorContext);
        Assert.assertTrue(entryRepository.count() > 0);
        Assert.assertTrue(Files.exists(Paths.get(FILE1_OUT_URI_SUCCESS)));
        Assert.assertTrue(Files.exists(Paths.get(FILE2_OUT_URI_SUCCESS)));
    }

}
