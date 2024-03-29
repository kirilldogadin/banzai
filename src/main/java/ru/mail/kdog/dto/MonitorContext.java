package ru.mail.kdog.dto;

import lombok.*;

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
@EqualsAndHashCode
@ToString
public class MonitorContext {
    public final File dirIn;
    public final File dirOutSuccess;
    public final File dirOutWrong;
    public Duration monitorPeriod;

}