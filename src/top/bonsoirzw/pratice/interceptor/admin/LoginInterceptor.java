package top.bonsoirzw.pratice.interceptor.admin;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台登陆拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String requestURI = httpServletRequest.getRequestURI();
        Object admin = httpServletRequest.getSession().getAttribute("admin");
        if(admin == null){
            System.out.println("链接" + requestURI + "进入拦截器");
            //未登录或者登陆失效
            String header = httpServletRequest.getHeader("X-Requested-With");
            if("XMLHttpRequest".equals(header)){
                //表示是ajax请求
                Map<String,String> ret = new HashMap<>();
                ret.put("type","error");
                ret.put("msg","登陆回话超时或还未登录，请重新登陆!");
                httpServletResponse.getWriter().write(JSONObject.fromObject(ret).toString());
                return false;
            }
            //普通链接跳转
            httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/system/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
