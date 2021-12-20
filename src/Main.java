import common.util.TextFileReader;
import repository.JdbcRepository;

import java.util.List;

import static common.constant.FileReaderConstants.DIR_PATH;

public class Main {

    public static void main(String[] args) {
        JdbcRepository jdbcRepository = new JdbcRepository();
        TextFileReader textFileReader = new TextFileReader();

        List<String> queryList = textFileReader.getQueryList(DIR_PATH);
        for (String query : queryList) {
            if (textFileReader.isSelectQuery(query)) {
                jdbcRepository.executeQuery(query).print();
            } else if (textFileReader.isUpdateQuery(query)) {
                jdbcRepository.executeUpdate(query).print();
            }
        }
    }
}
