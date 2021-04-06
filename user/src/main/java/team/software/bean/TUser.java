package team.software.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TUser表实体类
 *
 * @author liam
 * @date 2021/04/06 09:10
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TUser implements Serializable {

    private static final long serialVersionUID = -31837085050102472L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String head;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日 例: 2021-04-01
     */
    private String birthday;

    /**
     * 用户签名
     */
    private String info;

    /**
     * 用户创建时间 例：2021-04-01 17:29:55
     */
    private String createTime;

    /**
     * 最后登录时间 例: 2021-04-01 17:29:55
     */
    private String lastTime;

    /**
     * 用户状态
     */
    private String state;

}
