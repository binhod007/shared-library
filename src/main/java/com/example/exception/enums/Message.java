package com.example.exception.enums;

import com.example.exception.base.SystemCodeAware;

public enum Message implements SystemCodeAware {

    PASSWORD_REQUIRED("E001", "Password is required"),
    PASSWORD_TOO_SHORT("E002", "Password must be at least 8 characters"),
    INVALID_EMAIL("E003", "Invalid email format"),
    NAME_REQUIRED("E004", "Name is required"),
    NAME_TOO_LONG("E005", "Name cannot be more than 100 characters"),
    INTERNAL_ERROR("E006", "Internal server error"),
    USER_NOT_FOUND("E007", "User not found"),
    VALIDATION_ERROR("E008", "Validation error"),
    EMAIL_REQUIRED("E009", "Email is required"),
    EMAIL_ALREADY_EXISTS("E010", "Email already exists"),
    NO_USERS_FOUND("E011", "No users found in the system"),
    INVALID_EMAIL_OR_PASSWORD("E012", "Invalid email or password"),
    ROLE_NOT_FOUND("E013", "Role not found"),
    ACCESS_DENIED("E014", "You do not have permission to access this resource"),
    TOKEN_MISSING_OR_INVALID("E015", "Authentication token is missing or invalid"),
    USER_ALREADY_INACTIVE("E016", "User account is already inactive"),
    USER_ALREADY_ACTIVE("E017", "User account is already active"),
    USER_INACTIVE("E018", "User account is inactive"),
    INVALID_OLD_PASSWORD("E019", "Old password is incorrect"),
    ROLE_ALREADY_ASSIGNED("E020", "Role is already assigned"),
    CANNOT_DEACTIVATE_ADMIN("E021", "Can't deactivate admin"),
    CANNOT_DELETE_ADMIN("E022", "Cannot delete another admin account"),
    UNAUTHORIZED_ACCESS("E023", "You are not authorized to access this resource"),
    ACCOUNT_DELETED_SUCCESS("E024", "Your account has been deleted successfully"),
    USER_DELETED_SUCCESS("E025", "Your account has been deleted successfully"),
    NO_ACTIVITY_LOGS_FOUND("E026", "No activity logs found for the user");








    private final String code;
    private final String description;

    Message(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return description;
    }
}
