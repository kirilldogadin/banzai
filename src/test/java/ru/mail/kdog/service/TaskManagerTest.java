package ru.mail.kdog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskManagerTest extends BaseTest {

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
