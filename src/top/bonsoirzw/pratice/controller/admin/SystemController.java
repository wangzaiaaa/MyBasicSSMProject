package top.bonsoirzw.pratice.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.bonsoirzw.pratice.entity.admin.User;
import top.bonsoirzw.pratice.service.admin.UserService;
import top.bonsoirzw.pratice.util.CpachaUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统操作控制器
 */
@Controller
@RequestMapping("/system")
public class SystemController {
//    @RequestMapping(value = "/index",method = RequestMethod.GET)
//    public String index(){
//        return "system/index";  //  /WEB_INF/views + index + .jsp
//    }
   @Autowired
   private UserService userService;


    /**
     * 登陆后的主页
     * @param view
     * @return
     */
   @RequestMapping(value ="/index",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView view){
        view.setViewName("system/index");

        return view;
    }

    /**
     * 登陆后的欢迎页
     * @param view
     * @return
     */
    @RequestMapping(value ="/welcome",method = RequestMethod.GET)
    public ModelAndView welcome(ModelAndView view){
        view.setViewName("system/welcome");

        return view;
    }



    //登陆页面
    @RequestMapping(value = "/login",method = RequestMethod.GET)

    public ModelAndView login(ModelAndView model){
        model.setViewName("system/login");
        return model;
    }

    /**
     * 登陆表单控制器
     * @param user
     * @param cpacha
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> loginAct(User user,String cpacha,HttpServletRequest request){
        Map<String,String> ret = new HashMap<>();
        if(user==null){
            ret.put("type","error");
            ret.put("msg","请填写用户信息！");
            return ret;
        }
        if(StringUtils.isEmpty(cpacha)){
            ret.put("type","error");
            ret.put("msg","请填写验证码！");
            return ret;
        }
        if(StringUtils.isEmpty(user.getUsername())){
            ret.put("type","error");
            ret.put("msg","请填写用户名！");
            return ret;
        }
        if(StringUtils.isEmpty(user.getPassword())){
            ret.put("type","error");
            ret.put("msg","请填写密码！");
            return ret;
        }
        Object loginCpacha = request.getSession().getAttribute("loginCpacha");
        if(loginCpacha == null){
            ret.put("type","error");
            ret.put("msg","会话超时，请刷新页面！");
            return ret;
        }
        if(!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())){
            ret.put("type","error");
            ret.put("msg","验证码错误！");
            return ret;
        }
        User findByUsername = userService.findByUsername(user.getUsername());
        if(findByUsername == null){
            ret.put("type","error");
            ret.put("msg","该用户名不存在");
            return ret;
        }
        if(!user.getPassword().equals(findByUsername.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码错误");
            return ret;
        }
        request.getSession().setAttribute("admin",findByUsername);
        ret.put("type","success");
        ret.put("msg","登陆成功！");
        return ret;
    }

    /**
     * 所有的验证码都采用此方法
     * @param vcodeLen
     * @param width
     * @param height
     * @param cpachaType 用来区分验证码类型
     * @param request
     * @param response
     */
    @RequestMapping(value = "/get_cpacha",method = RequestMethod.GET)
    public void generateCpacha(
            @RequestParam(name = "vl",defaultValue = "4",required = false) Integer vcodeLen,
            @RequestParam(name = "w",defaultValue = "100",required = false) Integer width,
            @RequestParam(name = "h",defaultValue = "30",required = false) Integer height,
            @RequestParam(name = "type",defaultValue = "loginCpacha",required = true) String cpachaType,
            HttpServletRequest request,
            HttpServletResponse response){
        CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen,width,height);
        String generatorVcode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute(cpachaType,generatorVcode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVcode,true);
        try{
            ImageIO.write(generatorRotateVCodeImage,"gif",response.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
