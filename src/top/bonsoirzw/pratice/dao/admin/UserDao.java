package top.bonsoirzw.pratice.dao.admin;

import org.springframework.stereotype.Repository;
import top.bonsoirzw.pratice.entity.admin.User;

/**
 * user用户dao
 */
@Repository
public interface UserDao {
    public User findByUsername(String username);
}
