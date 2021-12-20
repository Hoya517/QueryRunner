package common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static common.constant.FileReaderConstants.EXTENSION_TXT;
import static common.constant.JdbcConstants.*;
import static common.constant.JdbcConstants.DML_DELETE;

public class TextFileReader {

    public boolean isSelectQuery(String query) {
        String queryFirst= query.substring(0, query.indexOf(" ")).toUpperCase();
        return queryFirst.equals(DML_SELECT) || queryFirst.equals("DESC");
    }

    public boolean isUpdateQuery(String query) {
        String queryFirst = query.substring(0, query.indexOf(" ")).toUpperCase();
        return queryFirst.equals(DML_INSERT) || queryFirst.equals(DML_UPDATE) || queryFirst.equals(DML_DELETE);
    }

    public List<String> getQueryList(String dirPath) {
        List<String> queryList = new ArrayList<>();

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (extractExtension(file).equals(EXTENSION_TXT)) {
                String query = getQuery(file.toPath());
                queryList.add(query);
            }
        }
        return queryList;
    }

    private String getQuery(Path path) {
        StringBuilder query = new StringBuilder();
        String line;
        try {
            BufferedReader reader = Files.newBufferedReader(path);
            while ((line = reader.readLine()) != null) {
                query.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return query.toString();
    }

    private String extractExtension(File file) {
        String extension = null;

        String fileName = file.getName();
        int idx = fileName.lastIndexOf(".");
        if (idx > 0) {
             extension = fileName.substring(idx + 1);
        }

        return extension;
    }

}
