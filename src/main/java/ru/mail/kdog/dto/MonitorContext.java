package ru.mail.kdog.dto;

import lombok.*;

import java.beans.ConstructorProperties;
import java.io.File;
import java.time.Duration;

/**
 * объект содержащий настройки
 * директория мониторинга
 * директироия обработыннх и тд
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MonitorContext {
    public final File dirIn;
    public final File dirOutSuccess;
    public final File dirOutWrong;
    public Duration monitorPeriod;

    public MonitorContext(File dirIn, File dirOutSuccess, File dirOutWrong) {
        this.dirIn = dirIn;
        this.dirOutSuccess = dirOutSuccess;
        this.dirOutWrong = dirOutWrong;
    }
}