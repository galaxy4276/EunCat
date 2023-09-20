package org.euncat.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public final class FileService {
    private final List<File> htmlList = getFiles();

    public List<File> getHtmlList() {
        return htmlList;
    }

    public List<File> getFiles() {
        URL url = ClassLoader.getSystemClassLoader().getResource("public");
        if (url == null) {
            throw new RuntimeException("Can't find Directory");
        }
        try {
            return Arrays.stream(new File(url.toURI()).listFiles()).toList();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
