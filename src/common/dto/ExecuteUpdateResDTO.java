package common.dto;

public class ExecuteUpdateResDTO {

    private String query;
    private int rowCount;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public String toString() {
        return "ExecuteUpdateResDTO{" +
                "query='" + query + '\'' +
                ", rowCount=" + rowCount +
                '}';
    }

    public void print() {
        // Query
        System.out.println("[Query :: " + query + "]");

        // rowCoumt
        System.out.println("rowCount => " + rowCount);
    }
}
