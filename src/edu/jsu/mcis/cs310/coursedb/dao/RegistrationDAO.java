package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    private static final String REGISTER_QUERY = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
    private static final String DROP_QUERY = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
    private static final String WITHDRAW_QUERY = "DELETE FROM registration WHERE studentid = ? AND termid = ?";
    private static final String LIST_QUERY = "SELECT * FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";
    
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
                ps = conn.prepareStatement(REGISTER_QUERY);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int rowsAffected = ps.executeUpdate();
                result = rowsAffected > 0;
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
                
                ps = conn.prepareStatement(DROP_QUERY);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int rowsAffected = ps.executeUpdate();
                result = rowsAffected > 0;
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
                
                ps = conn.prepareStatement(WITHDRAW_QUERY);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                int rowsAffected = ps.executeUpdate();
                result = rowsAffected > 0;
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
                
                ps = conn.prepareStatement(LIST_QUERY);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                rs = ps.executeQuery();
                
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
