package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    //SQL query string for creating, deleting, deleting all, and listing registration 
    private final static String QUERY_CREATE = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";     
    private final static String QUERY_DELETE = "DELETE FROM registration WHERE (studentid = ?) AND (termid) = ? AND (crn = ?)";
    private final static String QUERY_DELETE_TERM = "DELETE FROM registration WHERE (studentid = ?) AND (termid = ?)";
    private final static String QUERY_LIST = "SELECT * FROM registration WHERE (studentid = ?) AND (termid = ?)";
    
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // Prepare the SQL statement for creating a new registration
                ps = conn.prepareStatement(QUERY_CREATE);
                
                // Set the parameters for the SQL statement
                ps.setInt(1, studentid);    
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                //Execute the SQL statement
                result = ps.executeUpdate() > 0;
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // Prepare the SQL statement for deleting a registration
                ps = conn.prepareStatement(QUERY_DELETE);
                
                //Parameters for the SQL statement
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                // Execute the SQL statement
                result = ps.executeUpdate() > 0;

            }
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // Preparing the SQL statement for deleting all registrations for a specific term
                ps = conn.prepareStatement(QUERY_DELETE_TERM);
                
                // Setting parameters for the SQL statement
                ps.setInt(1, studentid);
                ps.setInt(2, termid);

                // Execute the SQL statement
                result = ps.executeUpdate() > 0;
     
            }
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // Preparing the SQL statement for listing registrations for a specific student and term
                ps = conn.prepareStatement(QUERY_LIST);
                
                // Parameters for the SQL statement
                ps.setInt(1, studentid);
                ps.setInt(2, termid);

                // Executinng the SQL statement
                rs = ps.executeQuery();
                
                //Converting the result set to a JSON string using DAOUtility method
                result = DAOUtility.getResultSetAsJson(rs);        
            }
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        
        return result;
        
    }
    
}
