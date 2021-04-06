package team.software.bean;

import java.util.List;

/**
 * @author huao
 * @ClassName FilmBean.java
 * @Description TODO
 * @createTime 2021年04月06日 09:05:00
 */
public class FilmBean {

    /**
     * 主键id
     */
    private String id;

    /**
     * 电影名
     */
    private String name;

    /**
     * 封面
     */
    private String cover;

    /**
     * 导演
     */
    private String directors;

    /**
     * 主演
     */
    private String casts;

    /**
     * 豆瓣评分
     */
    private String douban;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getDouban() {
        return douban;
    }

    public void setDouban(String douban) {
        this.douban = douban;
    }
}
