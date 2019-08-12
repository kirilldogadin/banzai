package ru.mail.kdog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.kdog.BaseTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskManagerTest extends BaseTest {

    @Autowired
    MonitorTaskManager taskManager;

    @Test
    public void scheduleTriggerTest() throws InterruptedException {
        Runnable task = () -> System.out.println("test");
        var periodicTrigger = new PeriodicTrigger(1000, TimeUnit.MILLISECONDS);
        taskManager.taskStart(task , periodicTrigger);
        Thread.sleep(1000L);
    }

    @Test
    public void scheduleFixedTest() throws InterruptedException {
        Runnable task = () -> System.out.println("test");
        Duration duration = Duration.ofSeconds(3);
        taskManager.taskStartFixed(task,duration);
        Thread.sleep(1000L);
    }

    @Test
    public void scheduleDelayTest() throws InterruptedException {
        Runnable task = () -> System.out.println("test");
        Duration duration = Duration.ofSeconds(3);
        taskManager.taskStartDelay(task,duration);
        Thread.sleep(1000L);
    }

    @Test
    public void monitoringTaskStartReactorTest(){
        taskManager.monitoringTaskStartReactor(monitorContext);
    }

}
