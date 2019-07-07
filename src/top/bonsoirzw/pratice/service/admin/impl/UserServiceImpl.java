package top.bonsoirzw.pratice.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.bonsoirzw.pratice.dao.admin.UserDao;
import top.bonsoirzw.pratice.entity.admin.User;
import top.bonsoirzw.pratice.service.admin.UserService;

/**
 * user用户serviceimpl
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

}
