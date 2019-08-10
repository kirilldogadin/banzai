package ru.mail.kdog.mapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.mail.kdog.dto.Entry;

import java.io.*;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Service
public class FileMapper {

    //File file =
    //Todo дженерифицировать https://www.programcreek.com/java-api-examples/?code=funtl/framework/framework-master/funtl-framework-core/src/main/java/com/funtl/framework/core/mapper/JaxbMapper.java


    //TODO try (InputStream xsdStream = ConfigurationService.class.getClassLoader().getResourceAsStream(CONFIG_XSD_FILE_NAME))
    JAXBContext jaxbContext;
    Unmarshaller jaxbUnmarshaller;

    @PostConstruct
    @SneakyThrows
    //TODO замениь на инжект бинов
    public void init() {
        jaxbContext = JAXBContext.newInstance(Entry.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }

    /**
     * отображает файл xml в java Object
     *
     * @param file new File("Student.xml");
     * @return EntityDto
     * @throws JAXBException
     */

    public Entry fileToPojo(File file) {
        try (var fs = new FileInputStream(file);
             var bs = new BufferedInputStream(fs)) {
            return fileToPojo(bs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @SneakyThrows
    public Entry fileToPojo(BufferedInputStream is) {
        return (Entry) jaxbUnmarshaller.unmarshal(is);
    }

    public Mono<Entry> fileToDto(File file) {
        return Mono.just(fileToPojo(file));
    }
}
