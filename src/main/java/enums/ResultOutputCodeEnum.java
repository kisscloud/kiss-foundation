package enums;

import lombok.Getter;

@Getter
public enum ResultOutputCodeEnum {

    Success(200, "成功"),
    SystemError(403, "权限错误"),
    DatabaseError(600, "数据库错误"),
    ValidateError(1000, "数据校验错误");

    private Integer code;

    private String message;

    ResultOutputCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
