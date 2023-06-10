package dto;

public class User {
  private int id;
  private String name;
  private String email;
  private String password;
  private String created_at;
  private String updated_at;

  public User(int id, String name, String email, String password, String created_at, String updated_at) {
    this.id = id;

    if (name == null)
      throw new IllegalArgumentException("name is null");
    if (email == null)
      throw new IllegalArgumentException("email is null");
    if (password == null)
      throw new IllegalArgumentException("password is null");
    if (created_at == null)
      throw new IllegalArgumentException("created_at is null");
    if (updated_at == null)
      throw new IllegalArgumentException("upStringd_at is null");

    this.name = name;
    this.email = email;
    this.password = password;
    this.created_at = created_at;
    this.updated_at = updated_at;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getCreatedAt() {
    return created_at;
  }

  public String getUpStringdAt() {
    return updated_at;
  }
}
