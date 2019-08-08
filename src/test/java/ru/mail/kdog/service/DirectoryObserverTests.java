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
import ru.mail.kdog.service.DirectoryObserver;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Tест создает копии файлов и подчищает файлы после
 * TODO создать файловую структуру если её нет?
 * для корректной работы необходимо наличие структуры в файловой системе файлов описанной в BaseTest
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DirectoryObserverTests extends BaseTest {

	@Autowired
	DirectoryObserver dirObsever;

	@Before
	@SneakyThrows
	public void initFiles(){
		Files.copy(Paths.get(FILE1_URI),Paths.get(FILE1_URI_COPIED), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(Paths.get(FILE2_URI),Paths.get(FILE2_URI_COPIED), StandardCopyOption.REPLACE_EXISTING);
	}

	@After
	@SneakyThrows
	public void destructFiles(){
		Files.deleteIfExists(Paths.get(FILE1_URI_COPIED));
		Files.deleteIfExists(Paths.get(FILE2_URI_COPIED));

		Files.deleteIfExists(Paths.get(FILE1_OUT_URI_SUCCESS));
		Files.deleteIfExists(Paths.get(FILE2_OUT_URI_WRONG));
	}

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

	@Test
	public void moveFileSuccesTest(){
		//BaseTest? или наверх
		dirObsever.moveFile(Paths.get(FILE1_URI_COPIED),Paths.get(FILE1_OUT_URI_SUCCESS));
		Assert.assertTrue(Files.exists(Paths.get(FILE1_OUT_URI_SUCCESS)));
	}

	@Test
	public void moveFileWrongTest(){
		dirObsever.moveFile(Paths.get(FILE2_URI_COPIED),Paths.get(FILE2_OUT_URI_WRONG));
		Assert.assertTrue(Files.exists(Paths.get(FILE2_OUT_URI_WRONG)));
	}


}
