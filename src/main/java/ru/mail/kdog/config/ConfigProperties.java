package ru.mail.kdog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.File;


/**
 * Содержит настройки
 *
 *
 */
//TODO как бин иницировать в фйле конфига?
    //TODO инжектить проперти из файла + @RefreshScope
@Service
//@ConfigurationProperties(prefix = "app")
//@PropertySource("classpath:application.properties")
public class ConfigProperties {
//    @Value("${app.dirin}")
    private String dirIn;
    private String dirOutSuccess;
    private String dirOutWrong;

    public File outSuccessDirFile;
    public File outWrongDirFile;
    public File inDirFile;

    @PostConstruct
    public void init(){
//     inDirFile = new File(dirIn);
//     outSuccessDirFile = new File(dirOutSuccess);
//     outSuccessDirFile = new File(dirOutWrong);
    }

    public File getOutSuccessDirFile() {
        return outSuccessDirFile;
    }

    public void setOutSuccessDirFile(File outSuccessDirFile) {
        this.outSuccessDirFile = outSuccessDirFile;
    }

    public File getOutWrongDirFile() {
        return outWrongDirFile;
    }

    public void setOutWrongDirFile(File outWrongDirFile) {
        this.outWrongDirFile = outWrongDirFile;
    }

    public File getInDirFile() {
        return inDirFile;
    }

    public void setInDirFile(File inDirFile) {
        this.inDirFile = inDirFile;
    }

    public String getDirIn() {
        return dirIn;
    }

    public void setDirIn(String dirIn) {
        this.dirIn = dirIn;
    }

    public String getDirOutSuccess() {
        return dirOutSuccess;
    }

    public void setDirOutSuccess(String dirOutSuccess) {
        this.dirOutSuccess = dirOutSuccess;
    }

    public String getDirOutWrong() {
        return dirOutWrong;
    }

    public void setDirOutWrong(String dirOutWrong) {
        this.dirOutWrong = dirOutWrong;
    }


}
