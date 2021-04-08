package team.software.bean;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
 * @author liam
 * @date 2021/4/6 21:10
 */
public enum UserCode implements IErrorCode {
    // 用户已存在
    EXIST_USERNAME(10002, "该用户已存在"),
    // 用户不存在
    NOT_EXIST_USERNAME(10001, "该用户不存在"),
    // 用户注册成功
    REGISTER_SUCCESS(10003, "用户注册成功"),
    // 用户注册失败
    REGISTER_FAILED(10004, "用户注册失败"),
    // 用户名格式错误
    USERNAME_PATTERN_ERROR(10005, "用户名格式错误"),
    // 密码格式错误
    PASSWORD_PATTERN_ERROR(10006, "密码格式错误"),
    // 密码错误
    PASSWORD_ERROR(10007, "密码错误"),
    // 登陆成功
    LOGIN_SUCCESS(10008, "登陆成功"),
    // 用户状态异常
    USER_STATE_ERROR(10009, "用户状态异常，请联系管理员"),
    // 新密码不能与原密码一致
    SAME_PASSWORD_ERROE(10010, "新密码不能与原密码一致"),
    // 密码修改成功
    UPDATE_PASSWORD_SUCCESS(10011, "密码修改成功"),
    // 密码修改失败
    UPDATE_PASSWORD_FAILED(10012, "密码修改失败"),
    // 获取用户信息成功
    GET_USERINFO_SUCCESS(10013, "获取用户信息成功");

    private final long code;
    private final String msg;

    UserCode(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
