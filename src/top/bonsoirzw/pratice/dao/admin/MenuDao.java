package top.bonsoirzw.pratice.dao.admin;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import top.bonsoirzw.pratice.entity.admin.Menu;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理dao
 */
@Repository
public interface MenuDao {
    public int add(Menu menu);
    public List<Menu> findList(Map<String,Object> queryMap);
    public List<Menu> findTopList();
    public int geTotal(Map<String,Object> queryMap);
}
