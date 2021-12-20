package repository;

import common.dto.ExecuteQueryResDTO;
import common.dto.ExecuteUpdateResDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.constant.JdbcConstants.*;

public class JdbcRepository {

    public ExecuteQueryResDTO executeQuery(String query) {
        ExecuteQueryResDTO dto = null;
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);   //PreparedStatement 생성
            ResultSet rs = pstmt.executeQuery()
        ) {
            dto = this.findColumns(rs);
            dto.setQuery(query);
            this.setData(rs, dto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public ExecuteUpdateResDTO executeUpdate(String query) {
        ExecuteUpdateResDTO dto = new ExecuteUpdateResDTO();
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)   // PreparedStatement 생성
        ) {
            dto.setQuery(query);
            dto.setRowCount(pstmt.executeUpdate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    private ExecuteQueryResDTO findColumns(ResultSet rs) throws SQLException {
        List<String> columns = new ArrayList<>();
        Map<String, Integer> maxLength = new HashMap<>();

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String findColumn = rsmd.getColumnName(i);
                columns.add(findColumn);
                maxLength.put(findColumn, findColumn.length());
            }
        }
        return new ExecuteQueryResDTO(columns, maxLength);
    }

    private void setData(ResultSet rs, ExecuteQueryResDTO dto) throws SQLException {
        List<Map<String, String>> data = new ArrayList<>();
        while (rs.next()) {
            for (String column : dto.getColumns()) {
                String result = rs.getString(column);
                Map<String, String> one = new HashMap<>();
                one.put(column, result);
                data.add(one);
                if (dto.getMaxLength().get(column) < result.length()) {
                    dto.getMaxLength().put(column, result.length());
                }
            }
        }
        dto.setData(data);
    }

    private Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER); // driver 로딩
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD); // Connection 생성
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}