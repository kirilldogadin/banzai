package ru.mail.kdog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskManagerTest {

    @Autowired
    TaskManager taskManager;

    @Test
    public void scheduleTest() throws InterruptedException {
        Runnable task = () -> System.out.println("test");
        var periodicTrigger = new PeriodicTrigger(1000, TimeUnit.MILLISECONDS);
        taskManager.monitoringStart(task , periodicTrigger);
        Thread.sleep(5000L);
    }
}
