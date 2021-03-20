import java.sql.*;

public class DB {
    private final static String url = "jdbc:mysql://localhost:3306/mydb";
    private final static String user = "root";
    private final static String password = "root";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                System.out.println("id = " + rs.getInt("id"));
                System.out.println("full name = " + rs.getString("full_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
