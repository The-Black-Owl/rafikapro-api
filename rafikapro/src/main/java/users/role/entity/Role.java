package users.role.entity;

import users.role.reference.RoleTypes;

public class Role {
    private  Long id;
    private RoleTypes roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName.toString();
    }

    public void setRoleName(String roleName) {
        switch (roleName.toUpperCase()){
            case "ADMIN":
                this.roleName=RoleTypes.ADMIN;
                break;
            case "VENDOR":
                this.roleName=RoleTypes.VENDOR;
                break;
            case "ORGANIZER":
                this.roleName=RoleTypes.ORGANIZER;
                break;
            case "ATTENDEE":
                this.roleName=RoleTypes.ATTENDEE;
                break;
            default:
                throw new RuntimeException("No valid role was set.");
        }
    }
}
