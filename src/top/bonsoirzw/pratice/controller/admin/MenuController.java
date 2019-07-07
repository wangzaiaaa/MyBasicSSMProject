package top.bonsoirzw.pratice.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.bonsoirzw.pratice.entity.admin.Menu;
import top.bonsoirzw.pratice.page.admin.Page;
import top.bonsoirzw.pratice.service.admin.MenuService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理控制器
 */
@RequestMapping("/admin/menu")
@Controller
public class MenuController {
    /**
     * 菜单管理列表页
     * @param model
     * @return
     */
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.addObject("topList",menuService.findTopList());
        model.setViewName("menu/list");
        return model;
    }

    /**
     * 获取菜单列表
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMenuList
            (Page page,
             @RequestParam(name = "name",required = false,defaultValue = "") String name
            ){
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap= new HashMap<>();
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        queryMap.put("name",name);
        List<Menu> findList = menuService.findList(queryMap);
        ret.put("rows",findList);
        ret.put("total",menuService.geTotal(queryMap));
        return ret;
    }
    @RequestMapping(value = "/get_icons",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getIconList(HttpServletRequest request){
        Map<String,Object> ret = new HashMap<>();
        String realPath = request.getServletContext().getRealPath("/");
        File file = new File(realPath + "\\resources\\admin\\eazyui\\css\\icons");
        List<String> icons = new ArrayList<>();
        if(!file.exists()){
            ret.put("type","error");
            ret.put("msg","文件目录不存在");
            return ret;
        }
        File [] files = file.listFiles();
        for(File f:files){
            if(f != null && f.getName().contains("png")){
                icons.add("icons-" + f.getName().substring(0,f.getName().indexOf(".")));
            }
        }
        ret.put("type","success");
        ret.put("content",icons);
        return ret;

    }


    /**
     * 菜单添加
     * @param menu
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Menu menu){
        Map<String,String> ret = new HashMap<>();
       if(menu == null){
           ret.put("type","error");
           ret.put("msg","请填写正确的菜单信息！");
           return ret;
       }
       if(StringUtils.isEmpty(menu.getName())){
           ret.put("type","error");
           ret.put("msg","请填写正确的菜单名称！");
           return ret;
       }

        if(StringUtils.isEmpty(menu.getIcon())){
            ret.put("type","error");
            ret.put("msg","请填写正确的菜单图标！");
            return ret;
        }
        if(menu.getParentId() == null){
            menu.setParentId(-1L);
        }
        if(menuService.add(menu) < 1){
            ret.put("type","error");
            ret.put("msg","添加失败，请联系管理员");
        }
        ret.put("type","success");
        ret.put("msg","添加成功！");
        return ret;
    }
}
