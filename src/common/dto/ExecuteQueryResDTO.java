package common.dto;

import java.util.List;
import java.util.Map;

public class ExecuteQueryResDTO {

    private String query;
    private List<String> columns;
    private List<Map<String, String>> data;
    private Map<String, Integer> maxLength;

    public ExecuteQueryResDTO(List<String> columns, Map<String, Integer> maxLength) {
        this.columns = columns;
        this.maxLength = maxLength;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getColumns() {
        return columns;
    }


    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public Map<String, Integer> getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Map<String, Integer> maxLength) {
        this.maxLength = maxLength;
    }

    public void print() {
        // Query
        System.out.println("[Query :: " + query + "]");

        setWidth("-");
        System.out.println();

        // Table
        int size = columns.size();
        System.out.print("|");
        for (String column : columns) {
            String space = "";
            Integer maxLength = getMaxLength().get(column);
            if (maxLength > column.length()) {
                space = this.addSpace(column, maxLength);
            }
            System.out.printf("  %s%s  |", column, space);
        }
        System.out.println();

        setWidth("=");

        for (int i = 0; i < data.size(); i++) {
            if (i % size == 0) {
                System.out.println();
                System.out.print("|");
            }
            String key = data.get(i).keySet().iterator().next();
            Integer maxLength = getMaxLength().get(key);
            System.out.printf("  %s%s  |", data.get(i).get(key), this.addSpace(data.get(i).get(key), maxLength));
        }

        System.out.println();
        setWidth("-");

    }

    private String addSpace(String columName, Integer maxLength) {
        StringBuilder space = new StringBuilder();
        int sub = maxLength - columName.length();
        for (int i = 0; i < sub; i++) {
            space.append(" ");
        }
        return space.toString();
    }

    private String addLine(Integer maxLength, String ch) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            line.append(ch);
        }
        return line.toString();
    }

    private void setWidth(String ch) {
        System.out.print(ch);
        for (String column : columns) {
            for (int i = 0; i < 5; i++) {
                System.out.print(ch);
            }
            Integer maxLength = getMaxLength().get(column);
            System.out.print(addLine(maxLength, ch));
        }
    }

}