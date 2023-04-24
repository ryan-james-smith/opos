/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.proteanit.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author JG uniCenta
 * @author ryan-james-smith refactored Vector to List/ArrayList
 */
public class DbUtils {

    /**
     * @param rs
     * @return TableModel
     */
    public static TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            List<String> columnNames = new ArrayList<>();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.add(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            List<List<Object>> rows = new ArrayList<>();

            while (rs.next()) {
                List<Object> newRow = new ArrayList<>();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.add(rs.getObject(i));
                }

                rows.add(newRow);
            }

            return new DefaultTableModel(rows.stream().map(row -> row.toArray()).toArray(Object[][]::new), columnNames.toArray());
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}