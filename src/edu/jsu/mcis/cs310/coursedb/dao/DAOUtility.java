package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
            
            if (rs != null) {
                
                // Metadata of the ResultSet
                ResultSetMetaData rsmd = rs.getMetaData();
                
                // Number of the columns in the ResultSet
                int columnCount = rsmd.getColumnCount();
                
                // Iterate each row in ResultSet
                while (rs.next()) {
                    JsonObject sectionData = new JsonObject(); 

                    // Iterate through each column in the row
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = rsmd.getColumnName(i);
                        Object columnValue = rs.getObject(i);

                        // Add column name and value to JsonObject
                        sectionData.put(columnName, columnValue != null ? columnValue.toString() : null);
                    }
                    
                    // Add the JSON object representing the row to the JSON array
                    records.add(sectionData);                                                         
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
