package team.software.bean;

/**
 * @author huao
 * @ClassName ResultMap.java
 * @Description TODO
 * @createTime 2021年04月02日 11:12:00
 */
public class ResultMap {

    /**
     * 返回的状态码
     * code: 200 请求成功
     * code: 500 参数错误
     * code: 501 未登录
     */
    private String code;

    /**
     * 返回的消息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
