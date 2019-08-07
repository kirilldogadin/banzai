package ru.mail.kdog.mapper;

import org.springframework.stereotype.Service;
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
    public Entry fileToPojoExceptionally(File file) throws JAXBException {
        //TODO зачем каждый раз инициировать?? вынести в бины
        JAXBContext jaxbContext = JAXBContext.newInstance(Entry.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Entry) jaxbUnmarshaller.unmarshal(file);

    }

    public Entry fileToPojo(File file) {
        try {
            return fileToPojoExceptionally(file);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }
}
