package ru.mail.kdog.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;
import ru.mail.kdog.service.DirectoryObserver;
import ru.mail.kdog.dto.Entry;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileMapperTests extends BaseTest {

	@Autowired
	DirectoryObserver directoryObserver;

	@Autowired
	FileMapper fileMapper;

	//Todo добавить validate
	@Test
	public void fileToPojo() throws IOException, JAXBException {

		var file = new File(FILE1_URI);
		Entry entry = fileMapper.fileToPojo(file);
		System.out.println(entry);
	}



}
