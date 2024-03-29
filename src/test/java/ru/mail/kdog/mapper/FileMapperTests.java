package ru.mail.kdog.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;
import ru.mail.kdog.service.FileSystemServiceImpl;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileMapperTests extends BaseTest {

    @Autowired
    FileSystemServiceImpl fileSystemService;

    @Autowired
    MapperFile fileMapper;

    @Test
    public void fileToPojo() {
        var file = new File(FILE1_URI);
        fileMapper.fileToMonoDto(file)
                .subscribe(System.out::println);
    }

}
