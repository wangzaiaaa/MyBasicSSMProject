package top.bonsoirzw.pratice.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.bonsoirzw.pratice.dao.admin.MenuDao;
import top.bonsoirzw.pratice.entity.admin.Menu;
import top.bonsoirzw.pratice.service.admin.MenuService;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理实现类
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public int add(Menu menu) {
        return menuDao.add(menu);
    }

    @Override
    public List<Menu> findList(Map<String, Object> queryMap) {
        return menuDao.findList(queryMap);
    }

    @Override
    public List<Menu> findTopList() {
        return menuDao.findTopList();
    }

    @Override
    public int geTotal(Map<String, Object> queryMap) {
        return menuDao.geTotal(queryMap);
    }
}
