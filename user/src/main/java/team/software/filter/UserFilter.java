package team.software.filter;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import sun.rmi.runtime.Log;
import team.software.util.JwtUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author liam
 * @date 2021/4/7 15:26
 */
@Slf4j
@Order(2)
@WebFilter(initParams = {@WebInitParam(name = "nofilter", value = "/user/register,/user/login,/user/checkRepeat", description = "可以直接通过的路径")})
public class UserFilter implements Filter {
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
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        // 请求拦截，校验token
        if (!isNoFilter(request)) {
            String token = request.getHeader("Authorization");
            if (token != null) {
                try {
                    Claims claims = JwtUtil.parseJWT(token);
                    if (claims != null) {
                        log.info(claims.toString());
                        log.info("当前访问的用户为" + claims.get("username"));
                        request.setAttribute("user_id", claims.get("id"));
                        request.setAttribute("username", claims.get("username"));

                        filterChain.doFilter(request, servletResponse);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
