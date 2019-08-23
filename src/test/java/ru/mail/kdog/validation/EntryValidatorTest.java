package ru.mail.kdog.validation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;
import ru.mail.kdog.entity.Entry;
import ru.mail.kdog.service.MonitorTaskManagerImpl;
import ru.mail.kdog.service.abstr.FileSystemService;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntryValidatorTest extends BaseTest {

    @Autowired
    Validator<Entry> entryValidator;

    @Test
    public void validationTest(){
        Entry entry = new Entry();
        entry.setCreationDate("2014-01-01 00:00:00");
        entryValidator.valid(entry);
    }

}
