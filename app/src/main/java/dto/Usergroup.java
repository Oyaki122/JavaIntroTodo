package dto;

public class Usergroup {
  private int id;
  private String name;
  private String description;
  private String logo;
  private boolean isPersonal;
  private String created_at;
  private String updated_at;

  public Usergroup(int id, String name, String description, String logo, boolean isPersonal, String created_at,
      String updated_at) {
    this.id = id;

    if (name == null)
      throw new IllegalArgumentException("name is null");
    if (description == null)
      throw new IllegalArgumentException("description is null");
    if (logo == null)
      throw new IllegalArgumentException("logo is null");
    if (created_at == null)
      throw new IllegalArgumentException("created_at is null");
    if (updated_at == null)
      throw new IllegalArgumentException("updated_at is null");

    this.name = name;
    this.description = description;
    this.logo = logo;
    this.isPersonal = isPersonal;
    this.created_at = created_at;
    this.updated_at = updated_at;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getLogo() {
    return logo;
  }

  public boolean getIsPersonal() {
    return isPersonal;
  }

  public String getCreatedAt() {
    return created_at;
  }

  public String getUpdatedAt() {
    return updated_at;
  }

}
