package todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        String pwd = System.getProperty("user.dir");
        Path path = Path.of(pwd, "src/db/test1.sqlite3");
        final String URL = "jdbc:sqlite:" + path.toString();
        final String SQL = "select * from syain;";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(SQL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("id") + " " +
                                    rs.getString("name") + " " +
                                    rs.getString("romaji"));
                }
            }
            ;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("処理が完了しました");
        }
    }
}