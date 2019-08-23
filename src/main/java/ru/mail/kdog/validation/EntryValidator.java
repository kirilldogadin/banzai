package ru.mail.kdog.validation;

import lombok.extern.slf4j.Slf4j;
import ru.mail.kdog.entity.Entry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Требования в задание не определены, но на реальной системе они явно будут
 * Например NotNull, MinSize и прочее
 * Или формат даты
 */
@Slf4j
public class EntryValidator implements Validator<Entry> {
    private final DateTimeFormatter dateTimeFormatter;

    public EntryValidator(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    /**
     * метод реализован для примера
     *
     * @param entry cущность для проверки
     */
    @Override
    public void valid(Entry entry) {
        LocalDateTime.parse(entry.getCreationDate(), dateTimeFormatter);
    }


}
