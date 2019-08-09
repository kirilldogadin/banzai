package ru.mail.kdog.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.mail.kdog.dto.Entry;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Service
public class FileMapper {

    //File file =
    //Todo дженерифицировать

    /**
     * отображает файл xml в java Object
     *
     * @param file new File("Student.xml");
     * @return EntityDto
     * @throws JAXBException
     */

    @SneakyThrows(JAXBException.class)
    public Entry fileToPojo(File file) {
        //TODO зачем каждый раз инициировать?? вынести в бины
        JAXBContext jaxbContext = JAXBContext.newInstance(Entry.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Entry) jaxbUnmarshaller.unmarshal(file);

    }

    //TODO по логике здесь должен быть Mono
    public Mono<Entry> fileToDto(File file) {
        return Mono.just(fileToPojo(file));
    }
}
