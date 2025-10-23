package Database;

import java.sql.*;
import java.sql.Timestamp;

public class DatabaseManager {
    private static final String url = "jdbc:postgresql://localhost:5432/smart_homee";
    private static final String user = "postgres";
    private static final String password = "kometa0707";
    private Timestamp sessionStart;

    public DatabaseManager() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to database successfully");
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
    }

    public void logEvent(String device, String action,double energyusage) throws SQLException {
        String sql = "INSERT INTO Events(device, action,energyusage) VALUES(?, ?,?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, device);
            pstmt.setString(2, action);
            pstmt.setDouble(3, energyusage);
            pstmt.executeUpdate();
            System.out.println("Saved event: " + device + " → " + action);
        } catch (SQLException e) {
            System.out.println(" Error inserting event: " + e.getMessage());
        }
    }
    public void logEvent(String device, String action) throws SQLException {
        logEvent(device, action, 0.0);
    }
    public void saveEnergyUsage(String deviceName, double energyUsage) {
        String sql = "INSERT INTO events(device,action,energyusage) VALUES(?, ?,?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, deviceName);
            pstmt.setString(2, "energyUsage recorded");
            pstmt.setDouble(3, energyUsage);
            pstmt.executeUpdate();

            System.out.println("Saved energy usage for " + deviceName + ": " + energyUsage + " kWh");
        } catch (SQLException e) {
            System.out.println("Error saving energy usage: " + e.getMessage());
        }
    }

    public void setSessionStart(Timestamp sessionStart) {
        this.sessionStart = sessionStart;
    }
    public void showNewEvents() {
        String sql = "SELECT * FROM events WHERE timestamp >= ? ORDER BY timestamp DESC";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, sessionStart);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Events added in this session:");
            while (rs.next()) {
                System.out.printf("[%s] %s → %s%n",
                        rs.getTimestamp("timestamp"),
                        rs.getString("device"),
                        rs.getString("action"),
                        rs.getDouble("energyusage"));
            }
        } catch (SQLException e) {
            System.out.println("Error reading events: " + e.getMessage());
        }
    }

}
