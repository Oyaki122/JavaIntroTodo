package dao;

import dto.Task;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRepository {
  final String TABLE = "tasks";
  private Connection conn;

  public TaskRepository(Connection conn) {
    this.conn = conn;
  }

  public ArrayList<Task> getTasksByTasksetId(int tasksetId) {
    ArrayList<Task> tasks = new ArrayList<Task>();

    String sql = """
        select * from task
        where taskset_id=
        """;
    sql += tasksetId + ";";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Task task = new Task(
              rs.getInt("id"),
              rs.getString("title"),
              rs.getString("description"),
              rs.getString("created_at"),
              rs.getString("updated_at"),
              rs.getString("due_date"),
              rs.getInt("priority"));
          tasks.add(task);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return tasks;
  }

}
