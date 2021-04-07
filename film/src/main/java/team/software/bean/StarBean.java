package team.software.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: StarBean
 * @author: dengshikai
 * @Data: 2021-04-06-10:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * 演员对应多个电影
     */
    private List<FilmDetailBean> filmList = new ArrayList<>();

}
