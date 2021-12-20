package common.dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExecuteQueryResDTO {

    private String query;
    private List<String> columns;
    private List<Map<String, String>> data;
    private Map<String, Integer> maxLength;

    public ExecuteQueryResDTO() {
    }

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

    public void setColumns(List<String> columns) {
        this.columns = columns;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecuteQueryResDTO dto = (ExecuteQueryResDTO) o;
        return Objects.equals(getQuery(), dto.getQuery()) && Objects.equals(getColumns(), dto.getColumns()) && Objects.equals(getData(), dto.getData()) && Objects.equals(getMaxLength(), dto.getMaxLength());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuery(), getColumns(), getData(), getMaxLength());
    }

    public void print() {
        // Query
        System.out.println("[Query :: " + query + "]");

        // Table
        int size = columns.size();
        System.out.print("|");
        for (String columName : columns) {
            String space = "";
            Integer maxLength = getMaxLength().get(columName);
            if (maxLength > columName.length()) {
                space = this.setSpace(columName, maxLength);
            }
            System.out.printf("  %s%s  |", columName, space);
        }
        System.out.println();

        for (int i = 0; i < data.size(); i++) {
            if (i % size == 0) {
                System.out.println();
                System.out.print("|");
            }
            String space = "";
            String key = data.get(i).keySet().iterator().next();
            Integer maxLength = getMaxLength().get(key);
            System.out.printf("  %s%s  |", data.get(i).get(key), this.setSpace(data.get(i).get(key), maxLength));
        }
    }

    private String setSpace(String columName, Integer maxLength) {
        StringBuilder space = new StringBuilder();
        int sub = maxLength - columName.length();
        for (int i = 0; i < sub; i++) {
            space.append(" ");
        }
        return space.toString();
    }

}