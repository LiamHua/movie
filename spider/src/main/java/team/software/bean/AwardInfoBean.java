package team.software.bean;

/**
 * @author huao
 * @ClassName AwardInfoBean.java
 * @Description TODO
 * @createTime 2021年04月04日 15:33:00
 */
public class AwardInfoBean {

    private String id;

    private String film_id;

    private String award_id;

    private String award_content;

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

    public String getAward_content() {
        return award_content;
    }

    public void setAward_content(String award_content) {
        this.award_content = award_content;
    }
}
