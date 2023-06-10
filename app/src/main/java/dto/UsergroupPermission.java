package dto;

class UsergroupPermission {
  private int usergroup_id;
  private int user_id;
  private int permission;
  private String created_at;
  private String updated_at;

  public UsergroupPermission(int usergroup_id, int user_id, int permission, String created_at, String updated_at) {
    if (created_at == null)
      throw new IllegalArgumentException("created_at is null");
    if (updated_at == null)
      throw new IllegalArgumentException("updated_at is null");

    this.usergroup_id = usergroup_id;
    this.user_id = user_id;
    this.permission = permission;
    this.created_at = created_at;
    this.updated_at = updated_at;
  }

  public int getUsergroupId() {
    return usergroup_id;
  }

  public int getUserId() {
    return user_id;
  }

  public int getPermission() {
    return permission;
  }

  public String getCreatedAt() {
    return created_at;
  }

  public String getUpdatedAt() {
    return updated_at;
  }
}