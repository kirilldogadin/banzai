package ru.mail.kdog.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntryRepositoryTest extends BaseTest {

	@Autowired
	EntryRepository entryRepository;

	//Todo добавить validate
	@Test
	public void testConnection() throws IOException {
		entryRepository.count();
	}



}
