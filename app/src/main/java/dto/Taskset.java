package dto;

public class Taskset {
  private int id;
  private int usergroup_id;
  private String name;
  private String description;
  private String logo;

  public Taskset(int id, int usergroup_id, String name, String description, String logo) {
    this.id = id;

    if (name == null)
      throw new IllegalArgumentException("name is null");
    if (description == null)
      throw new IllegalArgumentException("description is null");
    if (logo == null)
      throw new IllegalArgumentException("logo is null");

    this.usergroup_id = usergroup_id;
    this.name = name;
    this.description = description;
    this.logo = logo;
  }

  public int getId() {
    return id;
  }

  public int getUsergroupId() {
    return usergroup_id;
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
}
