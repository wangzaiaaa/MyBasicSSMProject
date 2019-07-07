package top.bonsoirzw.pratice.service.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import top.bonsoirzw.pratice.entity.admin.User;

/**
 * 用户service
 */
@Service
public interface UserService {
    public User findByUsername(String username);

}
