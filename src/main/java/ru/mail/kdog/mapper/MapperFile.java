package ru.mail.kdog.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.mail.kdog.entity.Entry;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

@Service
public class MapperFile extends FileAbstractMapper<File, Entry> {

    private final Unmarshaller jaxbUnmarshaller;

    public MapperFile(Unmarshaller jaxbUnmarshaller) {
        this.jaxbUnmarshaller = jaxbUnmarshaller;
    }

    /**
     * отображает файл xml в java Object
     * @param file file
     * @return EntityDto
     */
    @SneakyThrows
    @Override
    public Entry fileToDto(File file) {
        try (var fs = new FileInputStream(file);
             var bs = new BufferedInputStream(fs)) {
            return (Entry) jaxbUnmarshaller.unmarshal(bs);
        }
    }

    @Override
    public Mono<Entry> fileToMonoDto(File file) {
        return Mono.just(fileToDto(file));
    }


}
