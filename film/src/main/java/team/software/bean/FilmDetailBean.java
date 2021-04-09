package team.software.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FilmDetailBean
 * @author: dengshikai
 * @Data: 2021-04-06-10:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDetailBean {
    /**
     * 自增主键
     */
    private String id;

    /**
     * 电影译名
     */
    private String translated_term;

    /**
     * 电影名
     */
    private String name;

    /**
     * 电影封面
     */
    private String cover;

    /**
     * 年代
     */
    private String decade;

    /**
     * 语言
     */
    private String language;

    /**
     * 字幕
     */
    private String subtitle;

    /**
     * 电影上映日期
     */
    private String release_time;

    /**
     * 豆瓣评分
     */
    private String douban;

    /**
     * 片长
     */
    private String length;

    /**
     * 简介
     */
    private String info;

    /**
     * 电影资源下载链接
     */
    private String link;

    /**
     * 上传日期
     */
    private String upload_time;

    /**
     * 热度
     */
    private Integer hot;

    /**
     * 收藏量
     */
    private Integer collect;

    /**
     * 地区
     */
    private String areas;
    /**
     * 目录
     */
    private String category;

    /**
     * 标签
     */
    private String tags;

    /**
     * 编剧
     */
    private List<StarBean> scriptwriterList = new ArrayList<>();

    /**
     * 导演
     */
    private List<StarBean> directorList = new ArrayList<>();

    /**
     * 电影对应多个演员
     */
    private List<StarBean> starList = new ArrayList<>();

    /**
     * 奖项具体内容
     */
    private List<AwardBean> awardContentList = new ArrayList<>();

}
