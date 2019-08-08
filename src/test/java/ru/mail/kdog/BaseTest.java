package ru.mail.kdog;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mail.kdog.config.*;


public class BaseTest {

    public final String BASE_PATH_URI = ".\\src\\test\\resources\\app\\";
    public final String FILE1_NAME = "\\Entry1.xml";
    public final String FILE2_NAME = "\\Entry2.xml";
    public final String IN_URI = BASE_PATH_URI + "in";

    public final String OUT_SUCCESS_URI = BASE_PATH_URI + "out\\success";
    public final String OUT_WRONG_URI = BASE_PATH_URI + "out\\wrong";

    /**
     * исходный path
     */
    public final String FILE1_URI = IN_URI + FILE1_NAME;
    public final String FILE2_URI = IN_URI + FILE2_NAME;

    public final String FILE1_URI_COPIED = IN_URI + "\\Entry1ForSuccessMoving.xml";
    public final String FILE2_URI_COPIED = IN_URI + "\\Entry2ForWrongMoving.xml";

    public String FILE1_OUT_URI_SUCCESS = OUT_SUCCESS_URI + "\\Entry1ForSuccessMoving.xml";
    public String FILE2_OUT_URI_SUCCESS = OUT_SUCCESS_URI + "\\Entry2ForSuccessMoving.xml";

    public String FILE1_OUT_URI_WRONG = OUT_WRONG_URI + "\\Entry1ForWrongMoving.xml";
    public String FILE2_OUT_URI_WRONG = OUT_WRONG_URI + "\\Entry2ForWrongMoving.xml";


    @Autowired
    ConfigProperties propertyHolder;
}
