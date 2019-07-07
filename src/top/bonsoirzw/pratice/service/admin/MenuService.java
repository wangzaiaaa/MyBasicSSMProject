package top.bonsoirzw.pratice.service.admin;

import org.springframework.stereotype.Service;
import top.bonsoirzw.pratice.entity.admin.Menu;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理service
 */
@Service
public interface MenuService {
    public int add(Menu menu);
    public List<Menu> findList(Map<String,Object> queryMap);
    public List<Menu> findTopList();
    public int geTotal(Map<String,Object> queryMap);

}
