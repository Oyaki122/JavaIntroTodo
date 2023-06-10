package dto;

public class Task {
  private int id;
  private String title;
  private String description;
  private String created_at;
  private String updated_at;
  private String due_date;
  private int priority;

  public Task(int id, String title, String description, String created_at, String updated_at, String due_date,
      int priority) {
    this.id = id;

    if (title == null)
      throw new IllegalArgumentException("title is null");
    if (description == null)
      throw new IllegalArgumentException("description is null");
    if (created_at == null)
      throw new IllegalArgumentException("created_at is null");
    if (updated_at == null)
      throw new IllegalArgumentException("updated_at is null");
    if (due_date == null)
      throw new IllegalArgumentException("due_date is null");

    this.title = title;
    this.description = description;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.due_date = due_date;
    this.priority = priority;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getCreatedAt() {
    return created_at;
  }

  public String getUpdatedAt() {
    return updated_at;
  }

  public String getDueString() {
    return due_date;
  }

  public int getPriority() {
    return priority;
  }

}
