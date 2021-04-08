package team.software.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: AwardBean
 * @author: dengshikai
 * @Data: 2021-04-08-16:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardBean {
    /**
     * 电影id
     */
    private String filmId;
    /**
     * 奖名id
     */
    private String awardId;
    /**
     * 奖名
     */
    private String awardName;

    private String awardContent;


    /**
     * 奖项具体内容
     */
    private List<String> awardContentList = new ArrayList<>();

}
