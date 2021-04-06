package team.software.bean;

/**
 * @author huao
 * @ClassName CategoryBean.java
 * @Description TODO
 * @createTime 2021年04月02日 17:21:00
 */
public class CategoryBean {

    /**
     * 主键
     */
    private String id;

    /**
     * 分类名
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
