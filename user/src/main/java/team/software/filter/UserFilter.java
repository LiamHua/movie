package team.software.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import sun.rmi.runtime.Log;
import team.software.bean.ResultMap;
import team.software.bean.UserCode;
import team.software.util.JwtUtil;
import team.software.util.RedisUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liam
 * @date 2021/4/7 15:26
 */
@Slf4j
@Order(2)
@WebFilter(initParams = {@WebInitParam(name = "nofilter", value = "/user/register,/user/login,/user/checkRepeat", description = "可以直接通过的路径")})
public class UserFilter implements Filter {
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 存储不过滤的路径
     */
    private String[] nofilter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String initNofilter = filterConfig.getInitParameter("nofilter");
        if (initNofilter != null && initNofilter.length() > 0) {
            nofilter = initNofilter.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 请求拦截，校验token
        if (!isNoFilter(request)) {
            String token = request.getHeader("Authorization");
            if (token != null) {
                Claims claims = JwtUtil.parseJWT(token);
                if (claims != null) {
                    log.info(claims.toString());
                    String username = (String) claims.get("username");
                    int id = (int) claims.get("id");
                    // 从Redis中校验token
                    String redisToken = (String) redisUtil.get(username);
                    if (redisToken == null || !redisToken.equals(token)) {
                        response.getWriter().write(JSON.toJSONString(R.restResult("", UserCode.TOKEN_ERROR)));
                        return;
                    }
                    log.info("当前访问的用户为" + username);
                    request.setAttribute("user_id", id);
                    request.setAttribute("username", username);

                    filterChain.doFilter(request, servletResponse);
                } else {
                    response.getWriter().write(JSON.toJSONString(R.restResult("", UserCode.TOKEN_ERROR)));
                    return;
                }
            } else {
                response.getWriter().write(JSON.toJSONString(R.restResult("", UserCode.UNAUTHORIZATION)));
                return;
            }
        } else {
            filterChain.doFilter(request, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    public boolean isNoFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.info("正在访问" + uri);
        for (String s : nofilter) {
            if (uri.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
