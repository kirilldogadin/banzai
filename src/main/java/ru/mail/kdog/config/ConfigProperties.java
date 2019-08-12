package ru.mail.kdog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ru.mail.kdog.dto.MonitorContext;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.Duration;


/**
 * Содержит настройки
 *
 *
 */
//TODO как бин иницировать в фйле конфига?
    //TODO инжектить проперти из файла + @RefreshScope
//@Service
@ConfigurationProperties(prefix = "app")
@Setter
@Getter
public class ConfigProperties {

    //TODO файл передлать на yaml
    //TODO Проверть как аргумент и как env
    //TODO аругменты по умолчанию
    private String dirIn = ".";
    private String dirOutSuccess = "./success";
    private String dirOutWrong = "./wrong";

    public File dirOutSuccessFile;
    public File dirOutWrongFile;
    public File dirInFile;
    public Duration monitorPeriod;

    @Bean
    public MonitorContext monitorContext(){
        return MonitorContext.builder()
                .dirIn(dirInFile)
                .dirOutSuccess(dirOutSuccessFile)
                .dirOutWrong(dirOutWrongFile)
                .build();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    @PostConstruct
    public void init(){
        //TODO здесь рожать Bean MonitorContext
     dirInFile = new File(dirIn);
     dirOutSuccessFile = new File(dirOutSuccess);
     dirOutSuccessFile = new File(dirOutWrong);
    }


}
