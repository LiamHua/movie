package team.software.bean;

/**
 * @author huao
 * @ClassName TagBean.java
 * @Description 标签bean
 * @createTime 2021年04月02日 17:22:00
 */
public class TagBean {

    /**
     * 主键
     */
    private String id;

    /**
     * 标签名
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
