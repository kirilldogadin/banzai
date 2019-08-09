package ru.mail.kdog.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

    public static Path getOutPath(File subject,File outDit) {
        return Paths.get(subject.getName() + outDit.getName());
    }
}
