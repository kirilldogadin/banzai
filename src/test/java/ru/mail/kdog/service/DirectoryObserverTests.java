package ru.mail.kdog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;
import ru.mail.kdog.service.DirectoryObserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectoryObserverTests extends BaseTest {

	@Autowired
	DirectoryObserver dirObsever;

	//Todo добавить validate
	@Test
	public void getListFilesFromDirTest() throws IOException {
		dirObsever.getListFilesFromDir(Paths.get(IN_URI))
				.forEach(System.out::println);
	}
	@Test
	public void getListFilesFromDirTestAsync() throws IOException {
		dirObsever.getListFilesFromDirAsync(new File(IN_URI))
				.subscribe(System.out::println);
	}


}
