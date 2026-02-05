package rubrica;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbHandler {
	private static Connection getConnection() throws Exception {
	    Properties props = new Properties();
	    props.load(new FileInputStream("credenziali_database.properties"));

	    // 1. Read the specific parts from the file
	    String ip = props.getProperty("ip-server-mysql"); // Reads "localhost"
	    String port = props.getProperty("porta");         // Reads "3306"
	    String user = props.getProperty("username");      // Reads "root"
	    String pass = props.getProperty("password");      // Reads the password
	    
	    // 2. Build the JDBC URL manually
	    String dbName = "rubrica_db"; 
	    String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;

	    // 3. Connect
	    return DriverManager.getConnection(url, user, pass);
	}
	
	public static boolean login(String username, String password) {
	    String sql = "SELECT * FROM utente WHERE username = ? AND password = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, username);
	        pstmt.setString(2, password);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            return rs.next(); // Returns true if a user is found
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    public static List<Persona> getAllPersone() {
        List<Persona> list = new ArrayList<>();
        String sql = "SELECT * FROM persona";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Persona(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("telefono"),
                        rs.getInt("eta")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static void insertPersona(Persona p) {
        String sql = "INSERT INTO persona (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getCognome());
            pstmt.setString(3, p.getIndirizzo());
            pstmt.setString(4, p.getTelefono());
            pstmt.setInt(5, p.getEta());
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void updatePersona(Persona p) {
        String sql = "UPDATE persona SET nome=?, cognome=?, indirizzo=?, telefono=?, eta=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getCognome());
            pstmt.setString(3, p.getIndirizzo());
            pstmt.setString(4, p.getTelefono());
            pstmt.setInt(5, p.getEta());
            pstmt.setInt(6, p.getId());
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void deletePersona(int id) {
        String sql = "DELETE FROM persona WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}