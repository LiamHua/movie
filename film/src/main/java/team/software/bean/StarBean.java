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
     * 中文名
     */
    private String cnName;

    /**
     * 英文名
     */
    private String enName;

}
