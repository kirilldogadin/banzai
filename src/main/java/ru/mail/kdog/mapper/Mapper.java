package ru.mail.kdog.mapper;

import reactor.core.publisher.Mono;

/**
 * базовы интерфейс маппера для отражения одного типа в другой
 * @param <I> input
 * @param <O> output
 */
public interface Mapper<I, O> {
    Mono<O> fileToMonoDto(I file);
    O fileToDto(I file);
}
