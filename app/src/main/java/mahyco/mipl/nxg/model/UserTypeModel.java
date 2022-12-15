package mahyco.mipl.nxg.model;

public class UserTypeModel {

     int RoleId;//": 2,
             String Role;//": "DEVELOPER",

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    boolean IsActive;//": true

    public String toString(){
        return Role;
    }

}
