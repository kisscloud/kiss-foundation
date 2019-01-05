package exception;

import lombok.Data;

@Data
public class StatusException extends RuntimeException {

    private Integer code;

    private String message;

    private Object detail;

    public StatusException(Integer code) {
        this.code = code;
    }

    public StatusException(Integer code, Object detail) {
        this.code = code;
        this.detail = detail;
    }

}
