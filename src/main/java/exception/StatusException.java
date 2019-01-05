package exception;

public class StatusException extends RuntimeException {

    private Integer code;

    private String message;

    private Object detail;

    public StatusException(Integer code) {
        this.code = code;
    }

    public StatusException(Integer code, String message, Object detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public StatusException(Integer code, Object detail) {
        this.code = code;
        this.detail = detail;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getDetail() {
        return detail;
    }
}
