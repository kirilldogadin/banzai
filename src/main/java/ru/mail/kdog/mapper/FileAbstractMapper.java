package ru.mail.kdog.mapper;

import ru.mail.kdog.entity.BaseEntity;

import java.io.File;

/**
 * сужает типы маппера до
 * @param <F> файл
 * @param <D> сущность-результат
 */
public abstract class FileAbstractMapper<F extends File, D extends BaseEntity> implements Mapper<F, D> {
}
