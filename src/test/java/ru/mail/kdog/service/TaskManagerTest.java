package ru.mail.kdog.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;
import ru.mail.kdog.service.abstr.FileSystemService;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskManagerTest extends BaseTest {

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

    @Autowired
    MonitorTaskManagerImpl taskManager;

    @Test
    public void scheduleFixedTest() throws InterruptedException {
        Runnable task = () -> System.out.println("test");
        Duration duration = Duration.ofSeconds(3);
        taskManager.taskStartFixed(task,duration);
        Thread.sleep(duration.toMillis());
    }

    public void monitoringReactorTest(){
        taskManager.monitoringTaskStartReactor(monitorContext);
    }

    @Test
    public void monitoringSchedulerStartTest() throws InterruptedException {
        ScheduledFuture<?> scheduledFuture = taskManager.monitorTaskStart(monitorContext);
        Thread.sleep(monitorContext.monitorPeriod.toMillis());
        scheduledFuture.cancel(false);
        taskManager.monitoringTaskScheduler.shutdown();
    }

}
