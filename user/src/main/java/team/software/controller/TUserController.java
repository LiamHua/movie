package team.software.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import team.software.bean.UserCode;
import team.software.bean.TUser;
import team.software.service.TUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.api.R;

import org.springframework.web.bind.annotation.*;
import team.software.util.BaseUtil;
import team.software.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TUser表服务接口层
 *
 * @author liam
 * @date 2021/04/06 09:10
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class TUserController {

    /**
     * 服务对象
     */
    @Autowired
    @Qualifier("TUserServiceImpl")
    private TUserService tUserServiceImpl;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param tUser 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R<IPage<TUser>> selectAll(Page<TUser> page, TUser tUser) {
        return R.ok(tUserServiceImpl.page(page, new QueryWrapper<>(tUser)));
    }

    /**
     * 用户登陆
     *
     * @param tUser 用户名及密码
     * @return 单条数据
     */
    @RequestMapping("/login")
    public R<HashMap<String, Object>> selectOne(@RequestParam Map<String, String> tUser) {
        log.info("用户正在登陆" + tUser.toString());
        String username = tUser.get("username").trim();
        String password = tUser.get("password").trim();
        // 用户名格式错误
        if (username.length() == 0 || username.length() > 20) {
            log.info("用户名格式错误!");
            return R.restResult(null,UserCode.USERNAME_PATTERN_ERROR);
        }
        // 密码格式错误，加密后密码长度为32位
        if (password.length() != 32) {
            log.info("密码格式错误!");
            return R.restResult(null, UserCode.PASSWORD_PATTERN_ERROR);
        }
        TUser user = tUserServiceImpl.getOne(Wrappers.<TUser>lambdaQuery().eq(TUser::getUsername, username));
        if (user != null) {
            if (!user.getPassword().equals(password)) {
                // 密码错误
                log.info("用户密码错误!");
                return R.failed(UserCode.PASSWORD_ERROR);
            } else if (user.getState() != 1) {
                log.info("用户状态异常！");
                return R.failed(UserCode.USER_STATE_ERROR);
            } else {
                // 登陆成功
                log.info(username + "用户登陆成功！");
                HashMap<String, Object>  userSubject = new HashMap<>();
                userSubject.put("id", user.getId());
                userSubject.put("username", user.getUsername());
                String token = JwtUtil.createJWT(userSubject);
                HashMap<String, Object> data = new HashMap<>(16);
                data.put("token", token);
                return R.restResult(data,UserCode.LOGIN_SUCCESS);
            }
        } else {
            // 用户不存在
            log.info(username + "用户不存在！");
            return R.restResult(null,UserCode.NOT_EXIST_USERNAME);
        }
    }

    /**
     * 用户注册
     *
     * @param tUser 用户名与密码
     * @return 注册结果
     */
    @PostMapping("/register")
    public R<Map<String, String>> insert(@RequestParam Map<String, String> tUser) {
        log.info("用户注册中" + tUser.toString());
        String username = tUser.get("username");
        String password = tUser.get("password");
        // 用户名格式错误
        if (username.length() == 0 || username.length() > 20) {
            log.info("用户名格式错误");
            return R.restResult(null,UserCode.USERNAME_PATTERN_ERROR);
        }
        // 密码格式错误，加密后密码长度为32位
        if (password.length() != 32) {
            log.info("密码格式错误");
            return R.restResult(null, UserCode.PASSWORD_PATTERN_ERROR);
        }
        // 用户已存在
        if (tUserServiceImpl.getOne(Wrappers.<TUser>lambdaQuery().eq(TUser::getUsername,username)) != null) {
            log.info(username + "用户已存在");
            return R.failed(UserCode.EXIST_USERNAME);
        }

        TUser user = new TUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateTime(BaseUtil.getNowTime());
        user.setState(1);

        boolean flag = tUserServiceImpl.save(user);
        if (flag) {
            log.info(username + "用户注册成功！");
            return R.restResult(null, UserCode.REGISTER_SUCCESS);
        } else {
            log.info(username + "用户注册失败");
            return R.restResult(null, UserCode.REGISTER_FAILED);
        }
    }

    /**
     * 修改密码
     *
     * @param password 新密码
     * @return 修改结果
     */
    @PutMapping("/updatePassword")
    public R<Boolean> update(@RequestParam String password, HttpServletRequest request) {
        String username = (String)request.getAttribute("username");
        password = password.trim();
        log.info(username + "正在修改密码...");
        if (password.length() != 32) {
            log.info("密码格式错误");
            return R.restResult(null, UserCode.PASSWORD_PATTERN_ERROR);
        }
        TUser user = tUserServiceImpl.getOne(Wrappers.<TUser>lambdaQuery().eq(TUser::getUsername, username));
        if (user.getPassword().equals(password)) {
            log.info("密码修改失败，新密码不能与原密码一致！");
            return R.failed(UserCode.SAME_PASSWORD_ERROE);
        }

        boolean flag = tUserServiceImpl.update(Wrappers.<TUser>lambdaUpdate().set(TUser::getPassword, password));
        if (flag) {
            log.info(username + "修改密码成功！");
            return R.restResult(null, UserCode.UPDATE_PASSWORD_SUCCESS);
        } else {
            log.info(username + "密码修改失败！");
            return R.restResult(null, UserCode.UPDATE_PASSWORD_FAILED);
        }
    }

    /**
     * 获取用户信息
     * @param
     * @return 删除结果
     */
    @RequestMapping("/info")
    public R<HashMap<String, Map>> delete(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");
        String username = (String) request.getAttribute("username");
        log.info(username + "正在获取用户信息");
        TUser user = tUserServiceImpl.getOne(Wrappers.<TUser>lambdaQuery().eq(TUser::getId, userId));
        log.info(username + "获取用户信息成功！");
        HashMap<String, Object>  userInfo = new HashMap<>(16);
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("head", user.getHead());
        userInfo.put("sex", user.getSex());
        userInfo.put("birthday", user.getBirthday());
        userInfo.put("info", user.getInfo());
        userInfo.put("last_time", user.getLastTime());
        HashMap<String, Map> data = new HashMap<>();
        data.put("userInfo", userInfo);
        return R.restResult(data, UserCode.GET_USERINFO_SUCCESS);
    }

    /**
     * 检测用户是否已存在
     * @param username
     * @return
     */
    @RequestMapping("checkRepeat/{username}")
    public R<Map<String,String>> checkRepeat(@PathVariable String username) {
        log.info(username + " 正在注册，检测重名中...");
        if (tUserServiceImpl.getOne(Wrappers.<TUser>lambdaQuery().eq(TUser::getUsername,username)) == null) {
            return R.restResult(null,UserCode.NOT_EXIST_USERNAME);
        } else {
            log.info(username + "用户已存在");
            return R.failed(UserCode.EXIST_USERNAME);
        }
    }
}
