import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UpdateDoctor {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/medical_prescription_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "Chirag,.12";
        
        String updateSQL = "UPDATE doctors SET " +
                          "name = 'Dr. Ishan Mota', " +
                          "specialization = 'Conservative Dentistry and Endodontics', " +
                          "qualification = 'BDS, MDS - Conservative Dentistry and Endodontics', " +
                          "email = 'info@identistindia.com', " +
                          "phone = '+91 95522 63314', " +
                          "address = 'India' " +
                          "WHERE id = 1";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            
            int rowsAffected = stmt.executeUpdate(updateSQL);
            System.out.println("Doctor information updated successfully!");
            System.out.println("Rows affected: " + rowsAffected);
            
        } catch (Exception e) {
            System.err.println("Error updating doctor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
