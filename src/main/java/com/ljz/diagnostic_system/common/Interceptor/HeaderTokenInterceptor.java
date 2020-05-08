package com.ljz.diagnostic_system.common.Interceptor;

import com.alibaba.fastjson.JSON;
import com.ljz.diagnostic_system.common.CommonResult;
import com.ljz.diagnostic_system.common.Utils.JWTUtil;
import com.ljz.diagnostic_system.model.UserToken;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token鉴定
 * @ClassName:  HeaderTokenInterceptor
 */
public class HeaderTokenInterceptor implements HandlerInterceptor {
    private static final Logger LOG = Logger.getLogger(HeaderTokenInterceptor.class);
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object handler) throws Exception {
        httpServletResponse.setCharacterEncoding("UTF-8");//防止CommonResult中的中文乱码
        CommonResult commonResult = null;
        UserToken userToken = new UserToken();
        // 获取我们请求头中的token验证字符
        String headerToken = httpServletRequest.getHeader("token");
        // 检测当前页面,设置当页面不是登录页面时对其进行拦截
        // 具体方法就是检测URL中有没有login字符串
        String requestURI = httpServletRequest.getRequestURI();
        LOG.debug("拦截URL+"+requestURI);

        if (!requestURI.contains("login")&&!requestURI.contains("register")) {
            LOG.debug("拦截请求，headerToken="+headerToken);
            if (headerToken == null) {
                // 如果token不存在的话,返回错误信息。UNAUTHORIZED(401, "暂未登录或token已经过期"),
                commonResult=CommonResult.unauthorized(null);
            }else
            try {
                // 对token进行更新与验证
                userToken = JWTUtil.immediatelyExpired(headerToken);
                String refreshToken = userToken.getToken();
                //String refreshUsername = userToken.getId();
                if (!headerToken.equals(refreshToken)) {
                    LOG.info("token验证通过,并续期了");
                    Cookie cookie = new Cookie("token",refreshToken);
                    cookie.setMaxAge(24 * 60 * 60);
                    cookie.setPath("/diagnostic_system");
                    httpServletResponse.addCookie(cookie);
                    //headerToken = refreshToken;
                }
            } catch (Exception e) {
                LOG.debug("token验证出现异常!");
                // 当token验证出现异常返回错误信息,token不匹配 VALIDATE_FAILED(404, "参数检验失败"),
                commonResult=CommonResult.validateFailed();
            }
        }
        if(commonResult!=null) {//如果有错误信息
            LOG.debug("commonResult.getCode="+commonResult.getCode()+"commonResult.message="+commonResult.getMessage());
            httpServletResponse.getWriter().write(JSON.toJSONString(commonResult));
            return false;
        }else {
            // 将token加入返回页面的header
            //LOG.debug("更新Token："+headerToken);
            //httpServletResponse.setHeader("token", headerToken);
            httpServletRequest.setAttribute("id",userToken.getId());
            return true;
        }
    }



    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
    }
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}

