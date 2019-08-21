package ru.mail.kdog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ru.mail.kdog.dto.MonitorContext;
import ru.mail.kdog.entity.Entry;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.Duration;


/**
 * Содержит настройки
 */
//TODO как бин иницировать в фйле конфига?
//TODO инжектить проперти из файла + @RefreshScope
//@Service
@ConfigurationProperties(prefix = "app")
@Setter
@Getter
public class ConfigProperties {

    //TODO файл передлать на yaml
    private String dirIn = ".";
    private String dirOutSuccess = "./success";
    private String dirOutWrong = "./wrong";

    public File dirOutSuccessFile;
    public File dirOutWrongFile;
    public File dirInFile;
    public Duration monitorPeriod = Duration.ofMinutes(15);

    @Bean
    public MonitorContext monitorContext() {
        return MonitorContext.builder()
                .dirIn(dirInFile)
                .dirOutSuccess(dirOutSuccessFile)
                .dirOutWrong(dirOutWrongFile)
                .monitorPeriod(monitorPeriod)
                .build();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(Entry.class);
    }

    @Bean
    public Unmarshaller unmarshaller(JAXBContext jaxbContext) throws JAXBException {
        return jaxbContext.createUnmarshaller();
    }



    @PostConstruct
    public void init() {
        dirInFile = new File(dirIn);
        dirOutSuccessFile = new File(dirOutSuccess);
        dirOutWrongFile = new File(dirOutWrong);
    }


}
