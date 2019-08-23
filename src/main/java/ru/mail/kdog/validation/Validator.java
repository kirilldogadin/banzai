package ru.mail.kdog.validation;

/**
 * Валидатор полей объекта после маппинга
 * Например NotNull, MinSize и прочее
 * @param <I> валидирумеый объект
 */
public interface Validator<I> {
    void valid(I subject);
}
