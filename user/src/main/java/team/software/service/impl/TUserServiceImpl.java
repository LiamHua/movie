package team.software.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import team.software.mapper.TUserMapper;
import team.software.bean.TUser;
import team.software.service.TUserService;
import org.springframework.stereotype.Service;

/**
 * TUser表服务实现类
 *
 * @author liam
 * @date 2021/04/06 09:10
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

}
