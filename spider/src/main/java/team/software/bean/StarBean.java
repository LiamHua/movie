package team.software.bean;

/**
 * @author huao
 * @ClassName StarBean.java
 * @Description 演员bean
 * @createTime 2021年04月02日 16:58:00
 */
public class StarBean {

    /**
     * 主键id
     */
    private String id;

    /**
     * 豆瓣id(后续可能要添加功能)
     */
    private String douban_id;

    /**
     * 中文名
     */
    private String CN_name;

    /**
     * 英文名
     */
    private String EN_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCN_name() {
        return CN_name;
    }

    public void setCN_name(String CN_name) {
        this.CN_name = CN_name;
    }

    public String getEN_name() {
        return EN_name;
    }

    public void setEN_name(String EN_name) {
        this.EN_name = EN_name;
    }

    public String getDouban_id() {
        return douban_id;
    }

    public void setDouban_id(String douban_id) {
        this.douban_id = douban_id;
    }
}
