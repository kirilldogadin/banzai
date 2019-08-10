package ru.mail.kdog.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;
import ru.mail.kdog.dto.Entry;
import ru.mail.kdog.repository.EntryRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MonitorServiceTests extends BaseTest {

    //TODO создаваь файлы и дирекории для тестов и подчищать после

    @Autowired
    FileSystemService dirObsever;

    @Autowired
    MonitorService monitorService;

    @Autowired
    EntryRepository entryRepository;

    //Todo добавить validate
    @Test
    public void getListFilesFromDirTestOld() throws IOException {
        Stream<Entry> join = monitorService.asyncHandleFilesOld(Paths.get(IN_URI))
                //вывод в в лямбде поэтому его не видно
//                .thenAccept(entryStream -> entryStream.forEach(System.out::println))
                .join();
    }

    @Test
    public void getListFilesFromDirTestNew() throws IOException {
        monitorService.loadListFiles(new File(IN_URI))
                .subscribe(System.out::println);
    }

    @Test
    public void asyncHandleDirTest() throws IOException {
        monitorService.asyncHandleDir(new File(IN_URI));
        Assert.assertTrue(entryRepository.count() == 2L);
    }

    @Test
    public void asyncHandleDirFullTest(){
        var monitorContext = new MonitorService.MonitorContext(new File(IN_URI),
                new File(DIR_OUT_SUCCESS_URI),
                new File(DIR_OUT_WRONG_URI));
        monitorService.asyncHandleDir(monitorContext);
        Assert.assertTrue(entryRepository.count() > 0);
        Assert.assertTrue(Files.exists(Paths.get(FILE1_OUT_URI_SUCCESS)));
        Assert.assertTrue(Files.exists(Paths.get(FILE2_OUT_URI_SUCCESS)));
    }

}
