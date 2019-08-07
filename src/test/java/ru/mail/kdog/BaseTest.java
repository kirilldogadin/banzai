package ru.mail.kdog;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mail.kdog.config.*;


public class BaseTest {

    public final String BASE_PATH_URI = ".\\src\\test\\resources\\app\\";
    public final String IN_URI = BASE_PATH_URI + "in";
    public final String OUT_SUCCESS_URI = BASE_PATH_URI + "out\\success";
    public final String OUT_WRONG_URI = BASE_PATH_URI + "out\\wrong";

    public final String BASE_FILE_URI = IN_URI + "\\Entry1.xml";

    @Autowired
    ConfigProperties propertyHolder;
}
