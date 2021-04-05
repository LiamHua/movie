package team.software.bean;

import java.util.List;

/**
 * @author huao
 * @ClassName AwardBean.java
 * @Description TODO
 * @createTime 2021年04月03日 17:08:00
 */
public class AwardBean {

    /**
     * 主键
     */
    private String id;

    /**
     * 电影id
     */
    private String film_id;

    /**
     * 奖项名id
     */
    private String award_id;

    /**
     * 获奖名 例：第76届威尼斯电影节  (2019)
     */
    private String name;

    /**
     * 具体获奖内容,例：银狮奖 最佳导演 罗伊·安德森,最佳视觉效果
     */
    private List<String> award_content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilm_id() {
        return film_id;
    }

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
    }

    public String getAward_id() {
        return award_id;
    }

    public void setAward_id(String award_id) {
        this.award_id = award_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAward_content() {
        return award_content;
    }

    public void setAward_content(List<String> award_content) {
        this.award_content = award_content;
    }
}
