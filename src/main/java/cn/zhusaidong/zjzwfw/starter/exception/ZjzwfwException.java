package cn.zhusaidong.zjzwfw.starter.exception;

/**
 * @author zhusaidong
 * @date 2022/9/16
 */
public class ZjzwfwException extends RuntimeException {
    private static final long serialVersionUID = 9221685017025774988L;

    private final String code;

    public ZjzwfwException(String code, String message) {
        super(message);
        this.code = code;
    }
    public ZjzwfwException(String message) {
        super(message);
        this.code = null;
    }

    public String getCode() {
        return code;
    }
}
