package ru.mail.kdog.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;
import ru.mail.kdog.dto.Entry;
import ru.mail.kdog.repository.EntryRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MonitorServiceTests extends BaseTest {

    private final String BASE_PATH_URI = ".\\src\\test\\resources\\in";

    @Autowired
    DirectoryObserver dirObsever;

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
        Assert.assertTrue(entryRepository.count() > 0);
    }


//    //Todo добавить validate


}
